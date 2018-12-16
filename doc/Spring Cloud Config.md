回顾上次课程内容：

如何构建一个微服务及服务注册**(有问题？)**

客户端如何调用服务端的API(写死，负载均衡[LoadBalanceClient, @LoadBlanced])

#### Feign 客户端的调用框架







------------------------------------------------------------------------------------------------------------------------------------------------------

git / svn 

git配置: 

account:    271314998@qq.com

password: zxvnmqt@.123



### **Spring Cloud Config**

#### 1、服务端配置

##### 1.1 引入依赖

```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
    <exclusions>
        <exclusion>
            <artifactId>commons-logging</artifactId>
            <groupId>commons-logging</groupId>
        </exclusion>
    </exclusions>
</dependency>
```

##### 1.2 配置项

```
spring:
  cloud:
    zookeeper:
      connect-string: 192.168.3.201:2181
    config:
      server:
        git:
          uri: https://gitee.com/gerry123/config-repos
          username: 271314998@qq.com
          password: zxvnmqt@.123
```

##### 1.3 启动类配置

```
@EnableConfigServer 启用服务配置
```

**测试说明:** 

/{label}/{name}-{profiles}.yml

/{name}-{profiles}.yml / dev/prod/test

name git的文件名称不包括后缀

profiles 环境

label 分支(branch)

#### 2、客户配置

##### 2.1 引入依赖

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-client</artifactId>
</dependency>
```

##### 2.2 配置项

```
spring:
  application:
    name: bookProvider
  cloud:
    config:
      discovery:
        enabled: true
        service-id: config # 配置服务项目名称
```

##### 2.3 高可用测试说明



### Spring Cloud Bus 手动实现刷配置(不重启项目情况下)

##### 安装RabbitMQ

1、先在www.erlang.org/downloads下载erlang的源码
2、http://www.rabbitmq.com/download.html下载rabbitMQ
3、安装依赖 
   yum install ncurses-devel openssl

4、解压erlang的源码 

 tar xf otp_src_20.1.tar.gz

 cd otp_src_20.1

 ./configure --prefix=/usr/local/erlang210 --without-javac

 make -j 4
 make install

erlang /usr/local/erlang210/bin

5、安装python
​	yum install python -y
​	安装simplejson
​	yum install xmlto -y
​       a、yum install python-simplejson -y（如果安装失败请使用b）

​       b、进入simplejson文件夹，运行命令：python setup.py 或者先进如python编程模式，输入 setup.py install 

6、安装rabbitMQ 
​     xz -d rabbitmq-server-generic-unix-3.7.7.tar.xz
​    tar xf rabbitmq-server-generic-unix-3.7.7.tar
​    mv rabbitmq-server-3.7.7 /usr/local/rabbitmq
​    vim /etc/profile 配置环境变量:

​       到文件的末尾添加:

​	export PATH=$PATH:/usr/local/erlang210/bin

​	export PATH=$PATH:/usr/local/rabbitmq/sbin

​      使用配置马上生效: source /etc/profile

​    查看已经开放的端口：
​    firewall-cmd --list-ports

​    开启端口
​    firewall-cmd --zone=public --add-port=5672/tcp --permanent

​    firewall-cmd --zone=public --add-port=15672/tcp --permanent

7、启用RabbitMQWeb管理插件
​     rabbitmq-plugins enable rabbitmq_management 

8、创建用户
​    【用户】 ./rabbitmqctl add_user rabbit rabbit

​    【操作授权】该命令使用户test具有/vhost1这个/中所有资源的配置、写、读权限以便管理其中的资源
​      ./rabbitmqctl set_permissions -p / test ".*" ".*" ".*"

​     【角色授权】

​       ./rabbitmqctl set_user_tags test administrator

9、启动rabbitmq   

​      后台启动 ./rabbitmq-server -detached      

​	

10、SpringBoot加入RabbitMQ配置
   a> 添加依赖amqp并配置
​	#rabbitmq
​	spring.rabbitmq.host=192.168.3.101
​	spring.rabbitmq.port=5672
​	spring.rabbitmq.username=guest
​	spring.rabbitmq.password=guest
​	

##### 配置端引入依赖

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

##### 配置端配置项

```
rabbitmq:
    host: 192.168.3.201
    port: 5672
    username: test
    password: test
management:
  endpoints:
    web:
      exposure:
        include: '*' # 开启所有配置url
```
在controller上启用读取刷新数据注解@RefreshScope

### Spring Cloud Bus 实现自动刷配置(不重启项目情况下)+WebHooks

加入依赖

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-config-monitor</artifactId>
</dependency>
```

想办法把自己机器变为服务器netaap

在gitee里面配置webhook（bug属性不了分配文件）

通过/monitor 接口回调目标服务对应的服务



### Spring Cloud Config的加密应用

安装jcehttps://www.oracle.com/technetwork/java/javase/downloads/jce8-download-2133166.html

​	a、把jce中的2个策略文件覆盖到jdk/jre/lib/security目录下

​	b、设置当前用户对文件的完全操作权限

##### 对称加密（神坑）

```
bootstrap.yml
encrypt:
  key: zf_fkewkrKD3441
```

执行加密: http://localhost:8080/encrypt -d 需要加密的字符串 (POST)

执行解密:  http://localhost:8080/decrypt -d 加密后的字符串

##### 非对称加密

https://www.cnblogs.com/mujian/p/7665952.html

keytool

https://www.cnblogs.com/xdp-gacl/p/3750965.html

### Spring Cloud Zuul（服务网关）

引入依赖

org.springframework.cloud

spring-cloud-starter-netflix-zuul



配置项

```
spring:
  cloud:
    zookeeper:
      connect-string: 192.168.3.201:2181
    config:
      discovery:
        enabled: true
        service-id: config
  application:
    name: api-gateway
```



启用@EnableZuulProxy

启动服务并测试： http://localhost:9000/服务名/访问的地址



自定义路由:

```
zuul:
  routes:
    users:
      path: /myusers/** 
      serviceId: users_service 服务名称
    ---------------------
    服务名称: /myusers/** 
  ingore-patterns:
  	- /禁止的地址1
  	- /禁止的地址2
  sensitiveHeaders: 
      
```

![1540898673557](C:\Users\GERRY\AppData\Roaming\Typora\typora-user-images\1540898673557.png)



##### 自定义过滤器:需要继承ZuulFilter

pre

post

##### 限流:

![1540899511987](C:\Users\GERRY\AppData\Roaming\Typora\typora-user-images\1540899511987.png)





guava=>RateLimiter

##### zuul权限校验(放到项目中去讲)



##### zuul跨域处理(放到项目中去讲)

![1540900724294](C:\Users\GERRY\AppData\Roaming\Typora\typora-user-images\1540900724294.png)



### Hystrix（断路器）

#### restTemplate+hystrix

引入依赖:

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

配置项：

```properties
# 超时时间
hystrix.command.default.exectuion.isolation.thread.timeoutInMilliseconds=2000
#设置线程池最小线程数
hystrix.threadpool.default.coreSize=5
#设置对大线程池数量
hystrix.threadpool.default.maximumSize=10
# 让最大线程数生效
hystrix.threadpool.default.allowMaximumSizeToDivergeFromCoreSize=true
#设置队列长度
hystrix.threadpool.default.maxQueueSize=10
# 设置失败比例阀值
hystrix.command.default.circuitBreaker.errorThresholdPercentage=10
# 设置休眠时间（根据业务来设置）
hystrix.command.default.circuitBreaker.sleepWindowInMilliseconds=10000

---------------------------------------------------------------------
# 定制化设置
hystrix.threadpool.userDao.coreSize=10
1、必须在类上面配置
@DefaultProperties(groupKey="userDao", threadPoolKey="userDao",
commandProperties={@HystrixProperty(name="exectuion.isolation.thread.timeoutInMilliseconds", value="1000")},
threadPoolProperties={@HystrixProperty(name="coreSize", value="10"),
@HystrixProperty(name="maxQueueSize", value="1000")
})

2、方法上面添加@HystrixCommand注解
    fallbackMethod="getUserByTokenFb" #方法名称必须以方法名称打头，参数必须一致
    有异常抛出才加=>ignoreExceptions="忽略异常错误"
3、方法比较多的话，可以使用@DefaultProperties
```

启动用断路器@EnableCircuitBreaker

超时时间测试：



##### feign+hystrix

启用feign的hystrix

```yml
feign:
  hystrix:
    enabled: true
```

启动类加入

@EnableCircuitBreaker注解



依赖隔离:

线程池隔离

hystrix实现自动隔离



#### 服务熔断

![1541071695317](C:\Users\GERRY\AppData\Roaming\Typora\typora-user-images\1541071695317.png)





##### 如何使用熔断器(Circuit Breaker)

由于Hystrix是一个容错框架，因此我们在使用的时候，要达到熔断的目的只需配置一些参数就可以了。但我们要达到真正的效果，就必须要了解这些参数。Circuit Breaker一共包括如下6个参数。 
**1、circuitBreaker.enabled** 
是否启用熔断器，默认是TURE。 
**2、circuitBreaker.forceOpen** 
熔断器强制打开，始终保持打开状态。默认值FLASE。 
**3、circuitBreaker.forceClosed** 
熔断器强制关闭，始终保持关闭状态。默认值FLASE。 
**4、circuitBreaker.errorThresholdPercentage** 
设定错误百分比，默认值50%，例如一段时间（10s）内有100个请求，其中有55个超时或者异常返回了，那么这段时间内的错误百分比是55%，大于了默认值50%，这种情况下触发熔断器-打开。 
**5、circuitBreaker.requestVolumeThreshold** 
默认值20.意思是至少有20个请求才进行errorThresholdPercentage错误百分比计算。比如一段时间（10s）内有19个请求全部失败了。错误百分比是100%，但熔断器不会打开，因为requestVolumeThreshold的值是20. 

**6、circuitBreaker.sleepWindowInMilliseconds** 
半开试探休眠时间，默认值5000ms。当熔断器开启一段时间之后比如5000ms，会尝试放过去一部分流量进行试探，确定依赖服务是否恢复。



测试熔断 通过服务调用奇偶判断

**可视化组件**（dashboard）

引入依赖

spring-cloud-starter-netflix-hystrix-dashboard



启动类上标注启用

@EnableHystrixDashBoard

解决/actuator/hystrix.stream路径不存在问题

```java
@Bean
public ServletRegistrationBean getServlet(){
    HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();
    ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);
    registrationBean.setLoadOnStartup(1);
    registrationBean.addUrlMappings("/actuator/hystrix.stream");
    registrationBean.setName("HystrixMetricsStreamServlet");
    return registrationBean;
}
```



### Spring Cloud Sleuth服务跟踪

```xml
<dependency> 
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-sleuth</artifactId>
</dependency>
```

a -> b -> c -> d(扣库存) => 消耗的时间

监控

1. zipkin（https://zipkin.io/）

- 监控服务构建: (普通的springBoot项目)

```xml
<!--引入的zipkinServer依赖-->
<dependency>
    <groupId>io.zipkin.java</groupId>
    <artifactId>zipkin-server</artifactId>
    <version>2.9.4</version>
</dependency>
<dependency>
    <groupId>io.zipkin.java</groupId>
    <artifactId>zipkin-autoconfigure-ui</artifactId>
    <version>2.9.4</version>
</dependency>
```

配置内容：解决zipkin服务后台报错

```properties
management.metrics.web.server.auto-time-requests=false
```

- 启动类上加入@EnableZipkinServer注解启用zipkin服务

2. 在需要监控链路的服务里面加入下面依赖

   a、加入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
```

​	b、配置项

```properties
spring:
  zipkin:
    base-url: http://localhost:7777 # zipkin服务地址
    sleuth:
      sampler:
        probability: 1 # 抽样百分比
    sender:
      type: web # 链路类型（kafka,rabbitmq）
```







docker 安装zipkin**

```properties
docker run -d -p 9411:9411 openzipkin/zipkin
```

下载速度慢: 换docker镜像

 vim /etc/docker/daemon.json

内容: {
  		"registry-mirrors": ["https://9cpn8tt6.mirror.aliyuncs.com"]
​	}

### Spring Cloud Stream



安装jdk1.8 版本匹配 zookeeper3.5.xx



安装zookeeper



下载kafka
http://mirrors.hust.edu.cn/apache/kafka/2.0.0/kafka_2.11-2.0.0.tgz

kafka最为重要三个配置依次为：broker.id、log.dir、zookeeper.connect，kafka server端config/server.properties参数



advertised.listeners=PLAINTEXT://192.168.3.201:9092 # 公布访问地址和端口

启动kafka
bin/kafka-server-start.sh ../config/server.properties
检测是否启动
netstat -tunlp | egrep "(2181|9092)"

测试发送信息和消费消息

创建主题

`./kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test`

生产者
`./kafka-console-producer.sh --broker-list localhost:9092 --topic test`
消费者
`./kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning`

#### kafka

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-kafka</artifactId>
</dependency>
```

##### 简单API的应用

**生成者**

```java
public static void main(String[] args) throws ExecutionException, InterruptedException {

        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "192.168.3.201:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
 
        KafkaProducer kafkaProducer = new KafkaProducer(properties);
        String topic = "message";
        Integer partition = 0;
        long timeMillis = System.currentTimeMillis();
        String key = "key-message";
        String value = "value-message";
        ProducerRecord record = new ProducerRecord(topic, partition, key, value);
        kafkaProducer.send(record);
        kafkaProducer.close();
    }
```

**消费者**

```java
public static void main(String[] args) throws ExecutionException, InterruptedException {

    Properties props = new Properties();
    props.put("bootstrap.servers", "192.168.3.201:9092");
    props.put("group.id", "message");
    props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
    consumer.subscribe(Arrays.asList("message"));
    while (true) {
        ConsumerRecords<String, String> records = consumer.poll(100);
        for (ConsumerRecord<String, String> record : records)
            System.out.printf("--------offset = %d, key = %s, value = %s\n", record.offset(), record.key(), record.value());
    }
}
```

##### spring kafka（一个项目中操作）

依赖

```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-kafka</artifactId>
</dependency>
```

生成者

```properties
spring.kafka.bootstrap-servers=192.168.3.201:9092
spring.kafka.producer.key-serializer=\
  org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=\
  org.apache.kafka.common.serialization.StringSerializer
kafka.topic=spring-kafka # 定义kafka主题
```

代码

```java
@RestController
public class KafkaProducerController {
    public final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic;

    public KafkaProducerController(KafkaTemplate<String, String> kafkaTemplate,
                                   @Value("${kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @PostMapping("message/send")
    public boolean sendMessage(@RequestParam String message) {
        kafkaTemplate.send(topic,message);

        return true;
    }
}
```

消费者

```properties
# 消费者配置
spring.kafka.consumer.group-id=gerry-1
spring.kafka.consumer.key-deserializer=\
  org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=\
  org.apache.kafka.common.serialization.StringDeserializer
```

代码

```java
@Component
public class KafkaConsumerListener {
    @KafkaListener(topics = "${kafka.topic}")
    public void onMessage(String message) {
        System.out.println("kafka 消费者监听，接收到消息:" + message);
    }
}
```

#####Spring Cloud Stream

官方定义三个接口

Source=> 发送者 Producer、Publisher

Sink=> 接收器 Consumer、Subscriber

Processor: 上流而言Sink、下流而言Souce

##### Spring Cloud Stream Binder: Kafka

######发送信息

​        引入依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-stream-binder-kafka</artifactId>
</dependency>
```

​	编写生成者

```java
@Component
@EnableBinding(Source.class)
public class MessageProducerBean {
    @Autowired
    @Qualifier(Source.OUTPUT)
    private MessageChannel messageChannel;

    @Autowired
    private Source source;

    /**
     * 发送信息
     * @param message
     */
    public void send(String message) {
        // 通过消息管道发送消息
        // messageChannel.send(MessageBuilder.withPayload(message).build());
        source.output().send(MessageBuilder.withPayload(message).build());
    }
}
```

​	配置项

```properties
spring.kafka.bootstrap-servers=192.168.3.201:9092
kafka.topic=spring-kafka
# 由于内置了key-value的序列化器
# 消费者配置
spring.kafka.consumer.group-id=gerry-1
spring.kafka.consumer.key-deserializer=\
  org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer=\
  org.apache.kafka.common.serialization.StringDeserializer

# 配置消息去向
# spring.cloud.stream.bindings.<channel-name>.destination = 
spring.cloud.stream.bindings.output.destination= ${kafka.topic}
```

----

- 自定义Source

```java
public interface MessageSource {
    String NAME = "gerry";

    @Output(NAME)
    MessageChannel output();
}
```

```java
@Component
@EnableBinding({Source.class, MessageSource.class})
public class MessageProducerBean {

    @Autowired
    @Qualifier(MessageSource.NAME)
    private MessageChannel gerryMessageChannel;

    /**
     * 发送信息
     * @param message
     */
    public void sendToGerry(String message) {
        // 通过消息管道发送消息
        // messageChannel.send(MessageBuilder.withPayload(message).build());
        gerryMessageChannel.send(MessageBuilder.withPayload(message).build());
    }
}
```

```java
@RestController
public class KafkaProducerController {
    public final KafkaTemplate<String, String> kafkaTemplate;
    public final MessageProducerBean messageProducerBean;
    private final String topic;

    public KafkaProducerController(KafkaTemplate<String, String> kafkaTemplate,
                                   MessageProducerBean messageProducerBean,
                                   @Value("${kafka.topic}") String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.messageProducerBean = messageProducerBean;
        this.topic = topic;
    }

    @GetMapping("message/send/to/gerry")
    public boolean sendToGerry(@RequestParam String message) {
        messageProducerBean.sendToGerry(message);

        return true;
    }
}
```

###### 订阅消息（三种方式）

```java
@Component
@EnableBinding(value={Sink.class})
public class MessageConsumerBean {
    @Autowired
    @Qualifier(Sink.INPUT)
    private SubscribableChannel subscribableChannel;

    //1、 当subscribableChannel注入完成后完成回调
    @PostConstruct
    public void init() {
        subscribableChannel.subscribe(message->{
            System.out.println(message.getPayload());
        });
    }
	// 2、@ServiceActivator
    @ServiceActivator(inputChannel=Sink.INPUT)
    public void message(String message) {
        System.out.println("@ServiceActivator:"+message);
    }
	//3、@StreamListener
    @StreamListener(Sink.INPUT)
    public void onMessage(String message) {
        System.out.println("@StreamListener:"+message);
    }
}
```

##### Spring Cloud Stream Binder: RabbitMQ

kafka项目直接改造为rabbitmq

依赖项

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-stream-binder-rabbit</artifactId>
</dependency>
```

配置项

```properties
kafka.topic=spring-kafka
# 配置消息去向
spring.cloud.stream.bindings.output.destination= ${kafka.topic}
spring.cloud.stream.bindings.input.destination= ${kafka.topic}
spring.rabbitmq.host=192.168.3.201
spring.rabbitmq.port=5672
spring.rabbitmq.username=test
spring.rabbitmq.password=test
```





