哇哈哈，终于搞明白了asyncio是怎么回事了！！！  
 * 就是先 **注册事件**  ！再 **轮询事件** ，根据不同的事件执行不同的操作！  
 * 执行完操作之后，再 **注册新的事件** ！！！-- 就是告诉对方这个事件的动作我已经搞定了，可以进行下一个动作了！！！！

#

 * 根据 _test.jdk.async.nio.demo.ServerSocketChannelTest_ 可知：
 * ServerSocketChannel也可以注册selector及事件，但只能处理ACCEPT事件（客户端请求时发起的吧？貌似不是）。
 * 一旦有ACCEPT事件，ServerSocketChannel就可以accept()以创建链接了。
 * 如果创建了链接，就返回SocketChannel对象，然后就是SocketChannel之间的操作啦！！！