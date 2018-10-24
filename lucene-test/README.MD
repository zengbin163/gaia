# lucene test

# Lucene入门

首先，lucene仅仅是一个全文检索引擎，不是搜索引擎 -- 因为搜索引擎能够主动采集数据，而Lucene不可以。
也许solr是一个搜索引擎？

>全文检索首先将要查询的目标文档中的词提取出来，组成索引，通过查询索引达到搜索目标文档的目的。这种**先建立索引**，**再对索引进行搜索**的过程就叫全文检索（Full-text Search）。

既然Lucene不能主动采集数据，那就只能由用户自行采集，然后导入lucene！

lucene自己的数据结构为document、field。
你可以将document看成关系型数据库的一条记录，而field则是记录里的列。
所以，document:field = 1:*。

lucene不要求document的field name(s)都一样，可多可少，当然都一样更好。

自己用的话，只需要下载lucene，然后找到其目录下的 analysis/core/queryparser下的jar，放入项目classpath即可。

采集完成后，需要导入lucene -- 将数据转成document即可。

```java
Document doc=new Document();
//Store.YES/NO   是否存储到文档域中。
Field field = new TextField(name, value, store);//多个重载
doc.add(field);
```


具体需要查看api，lucene有提供几个核心类。
IndexWriter、Directory(FSDirectory、RAMDirectory等)。


操作步骤：  
Document -> Analyzer -> IndexWriter(索引写对象) -> Directory(索引目录流对象) -> 索引库

先创建Document，再创建Analyzer和IndexWriter。
通过后两者操作Document。