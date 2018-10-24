# solr test

一定要先看lucene！

额，其实了解索引相关的概念就行：Directory、Document、Field、IndexReader、IndexWriter。

其中Field有多种实现，主要区别：indexed、stored、tokenized。

## 关于Solr

使用Lucene作为技术基础，但提供更为丰富的功能。是一个服务！

Solr包含两部分：Server + SolrCore。

其中Server部分对外提供REST API接口，SolrCore部分提供索引+存储功能。

但是，Solr要求先定义Field、FieldType，还必须有一个uniqueKey，否则就不能使用。

## 数据

使用lucene-test下面的products.sql。