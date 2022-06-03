# mq-learn

### kafka
```shell script
auto.leader.rebalance.enable: boolean, 是否对broker 集群进行rebalance 操作. 比如当前有三个broker.
broker-1, broker-2, broker-3. 如果broker-3宕机了. 那么broker-3中的partation 会被broker-1, broker-2
接替. 如果开启了上述的配置. 当broker-3 启动之后会将之前broker-3 管理的partation 重新接管过来.
```
注: 判断是否重新选举成为leader. 是通过优先副本来判断的.

```shell script
leader.imbalance.per.broker.percentage: int. 这个值是每个broker 允许的不平衡的百分比,当超过这个限制的
时候会进行判断是否rebalance操作.
```
注: 这个均衡的百分比如何判定.

```shell script
compression.type: 'gzip', 'snappy', 'lz4', 'zstd'. 设置压缩算法类型. 还新增了一个'uncompressed' 非压缩
的类型, 用于处理没有压缩策略的类型. 不使用压缩.
```

```shell script
log.flush.interval.messages: int. 在消息flush 到磁盘之前, partation 上累积的消息数.
```

```shell script
log.retention.hours: 168[7天]. 日志保留的时长[hours].
```


### RocketMq


### pulsar 

pulsar 通过springboot 接入
1. 引入依赖
```xml
<dependency>
  <groupId>io.github.majusko</groupId>
  <artifactId>pulsar-java-spring-boot-starter</artifactId>
  <version>1.1.2</version>
</dependency>
```

注意: 这里的版本如果是1.1.2, 在消费端会报错. 
版本修改为 1.0.1, 可以正常消费.

2. 定义pulsar的生产者
参考: org.nirvana.pulsar.config.PulsarConfig

3. 配置文件中配置pulsar 的brokers的地址
```properties
pulsar.service-url=pulsar://localhost:6650
```

备注(pulsar 默认的配置, 参考: https://github.com/majusko/pulsar-java-spring-boot-starter): 
````properties
#PulsarClient
pulsar.service-url=pulsar://localhost:6650
pulsar.io-threads=10
pulsar.listener-threads=10
pulsar.enable-tcp-no-delay=false
pulsar.keep-alive-interval-sec=20
pulsar.connection-timeout-sec=10
pulsar.operation-timeout-sec=15
pulsar.starting-backoff-interval-ms=100
pulsar.max-backoff-interval-sec=10
pulsar.consumer-name-delimiter=
pulsar.namespace=default
pulsar.tenant=public
pulsar.auto-start=true
pulsar.allow-interceptor=false

#Consumer
pulsar.consumer.default.dead-letter-policy-max-redeliver-count=-1
pulsar.consumer.default.ack-timeout-ms=3000
````