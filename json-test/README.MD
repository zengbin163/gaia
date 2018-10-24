##fastjson这么快老外为啥还是热衷 jackson? 
https://www.zhihu.com/question/44199956

作者：甘明
链接：https://www.zhihu.com/question/44199956/answer/112224034
来源：知乎
著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。

哈哈哈，我来回答这个问题！因为我实在是对这两个库太熟悉了。  
1、你写个bean，然后属性里分别有包含_（下划线开头、#开头）之类的属性，序列化为json时，出现属性丢失，那么自然你也无法反序列化回来，据说最新的1.2.14已经改正了这个bug（我没测试不做评价）；  
2、翻阅fastjson的源码，你会发现有很多写死的代码，比如：针对spring之类的框架的各种处理，都是用classload判断是否存在这种类名fastjson/SerializeConfig.java at master · alibaba/fastjson · GitHub；那么这是什么意思呢？意思就是如果你用spring的那种思想，自己写了个类似的功能，因为你这个项目里没有spring的那个类，那么用起来就有一堆bug；当然不仅限于这些，还有很多，比如ASM字节码织入部分fastjson/ASMSerializerFactory.java at master · alibaba/fastjson · GitHub（温少的ASM方面水平是个半吊子），看源码的话，能发现的缺点数不胜数。  
3、其解析json主要是用的String类substring这个方法fastjson/JSONScanner.java at master · alibaba/fastjson · GitHub，所以解析起来非常“快”，因为申请内存次数很少。但是因为jdk1.7之前substring的实现并没有new一个新对象，在使用的时候，如果解析的json非常多，稍不注意就会出现内存泄漏（比如一个40K的json，你在对象里引用了里边的一个key，即使这个key只有2字节，也会导致这40K的json无法被垃圾回收器回收），这也是“快”带来的负面效果。而且这还不算，在jdk1.7以上版本对string的substring方法做了改写，改成了重新new一个string的方式，于是这个“快”的优势也不存在了。  
总结：fastjson就是一个代码质量较差的国产类库，用很多投机取巧的的做法去实现所谓的“快”，而失去了原本应该兼容的java特性，对json标准遵循也不严格，自然很难在国际上流行。  
感谢htyleo更正，我改一下。  
------------我是分割线-------------  
fastjson作者在看到本文后，今天告诉我已经改正了问题1，给温高铁先生点赞。json从发明到现在非常流行，并不是因为json快的原因（比json快且小巧的格式和类库一大把），而是因为json和web结合的时候更易于使用，对开发人员易于理解。很多人拿fastjson和jackson比，就像拿非智能机和iphone比待机时间，其功能性不一样，jackson的很多功能fastjson并没有实现，所以这种对比也不客观。fastjson之所以没在国际上流行起来，最主要的原因应该是开发者的思路全放到“快”上去了，而偏离了“标准”及功能性，质量也不够好，有点“舍本逐末”的味道。当然在目前的环境下，国产软件能踏实的心态做好开源的不多，fastjson团队能这么快的反馈并修正问题，这种精神还是值得称赞的。希望国内的技术从业者能更重视“技术的原始需求”。  
编辑于 2016-07-28



#jackson部分

注：jackson自己注册了默认的日期格式！DateFormat：StdDateFormat.instance，而StdDateFormat.instance的DEFAULT_LOCALE = Locale.US。但是，建议自定义日期格式 - 建议手动设置时区和locale，否则容易出问题！

注：除了一次性反序列化所有内容之外，还可以渐次反序列化指定的内容 - 通过readTree来生成JsonNode对象，然后就可以使用jsonNode.at(tree_path)来获取需要的内容！这里的tree_path是路径，如"/address/city"。

注：get(index)只适合array node。其他情况请使用get(field_str)获取。

注：