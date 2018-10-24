# reactor test

## what is reactor

`Pivotal` 提供的，异步非阻塞响应式框架，实现了`reactive-streams`。

## 怎么入门

[Notes on Reactive Programming Part I: The Reactive Landscape](https://spring.io/blog/2016/06/07/notes-on-reactive-programming-part-i-the-reactive-landscape)

[Notes on Reactive Programming Part II: Writing Some Code](https://spring.io/blog/2016/06/13/notes-on-reactive-programming-part-ii-writing-some-code)

先阅读上面两篇文章，再看看`reactive streams`提供的接口 - 作用类似Java Web的Servlet/Filter/Listener！

## 关键概念

```java
class Flux<T> implements Publisher{}; //针对0或n个元素
class Mono<T> implements Publisher{}; //针对0或1个元素
```


