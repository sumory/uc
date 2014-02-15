# UC



**UC** 是一个用于处理分布式系统中ID生成，唯一性字段值管理的一个通用模块.

## 介绍

 - UC使用 **Java** 开发，基于`Zookeeper`,`Thrift`,`Memcached`.
 - 其中config,id,passport,service包中代码为通用代码，init，main包是以现实项目为例做的Demo.


 ## TODO

 - 以HTTP或是其他RPC接口暴露功能，提供除Zookeeper/Thrift搭配外的其他选择
 - 将数据初始化管理和数据恢复独立出来，减少耦合