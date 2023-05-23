# net-websocket-spring-boot-starter [![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

基于netty实现的websocket，使用更简单

支持jdk版本为1.8或者1.8+

## 请求数据格式
```json
{
  "e": "事件类型",
  "t": ["主题信息"],
  "d": "自定义数据"
}
```
- e:event, t:topic, d:data
- 事件类型目前支持：subscribe(订阅), message(通信), cancel(取消订阅), heartbeat(心跳，由系统自动发送)
- 除心跳事件外，topic为必填项，可同时发送多个topic
- 响应数据格式无要求，可自定义返回

## 心跳事件
- 当客户端和服务端在1分钟内无数据交互时，服务端会发送心跳事件，数据格式如下：
```json
{
  "e": "heartbeat",
  "d": "ping"
}
```
- 客户端收到心跳数据时，请发送以下数据进行响应：
```json
{
  "e": "heartbeat",
  "d": "pong"
}
```
- 若服务端发送2次心跳事件仍无响应时，会断开连接

## 快速开始
- 添加依赖
```
<dependency>
    <groupId>org.onevroad</groupId>
    <artifactId>net-websocket-spring-boot-starter</artifactId>
    <version>0.4.1</version>
</dependency>
```

- 定义每个topic的事件处理器，返回值是对客户端的响应数据，返回值为空则不响应
### 通过注解方式
在类的方法上添加对应的注解，可以代理到对应的事件。  
每个注解可以自定义topic，topic的优先级为method annotation value > class annotation value。  
入参支持topic和data，需要分别加上对应注解RequestTopic和RequestData。
```java
@WebSocketListener("test-annotation")
public class SampleMessageAnnotationEventHandler {

    @OnSubscribe("test-annotation-subscribe")
    public String onSubscribe(@RequestTopic String topic, @RequestData String data) {
        return "subscribe success!";
    }

    @OnMessage
    public String onMessage(@RequestTopic String topic, @RequestData String data) {
        return "message received!";
    }

    @OnCancel("test-annotation-cancel")
    public String onCancel(@RequestTopic String topic, @RequestData String data) {
        return "cancel success!";
    }
}
```
### 通过实现接口方式
对于确定的topic，可实现WebSocketEventHandler<Request, Response>事件处理器，通过注解管理topic
```java
@WebsocketListener("test")
public class SampleMessageEventHandler implements WebSocketEventHandler<String, String> {
    @Override
    public String onSubscribe(String topic, String data) {
        return "subscribe success!";
    }

    @Override
    public String onMessage(String topic, String data) {
        return "message received!";
    }

    @Override
    public String onCancel(String topic, String data) {
        return "cancel success!";
    }
}
```
对应动态的topic，可实现WebSocketCustomizeEventHandler<Request, Response>事件处理器，通过equalsTopic管理topic
```java
@WebsocketListener
public class SampleMessageCustomizeEventHandler implements WebSocketCustomizeEventHandler<String, String> {
    @Override
    public boolean equalsTopic(String topic) {
        return "test2".equals(topic);
    }

    @Override
    public String onSubscribe(String topic, String data) {
        return "subscribe success!";
    }

    @Override
    public String onMessage(String topic, String data) {
        return "message received!";
    }

    @Override
    public String onCancel(String topic, String data) {
        return "cancel success!";
    }
}
```

- 配置扫描路径
```java
@EnableAutoConfiguration
@WebSocketScan(basePackages = {"org.net.websocket.samples.handler"})
public class WebSocketApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebSocketApplication.class).run(args);
    }
}
```

- 配置参数
```yaml
net:
  websocket:
    # 监听端口
    port: 80
    # 监听线程数，默认1个线程
    boss-group-threads: 1
    # 工作线程数，默认0为CPU核心数
    worker-group-threads: 0
    # 请求路径
    end-point: /ws
    # 重试配置
    retry:
      # 是否启用重试机制
      enable: true
      # 最大重试次数，默认3次
      max-retry-time: 3
      # 重试间隔，单位：毫秒，默认1000ms
      retry-interval: 1000
```

- 发送消息

使用WebSocketMessagePublisher的publish，有两个参数：topic: 主题信息; message: 消息内容
```java
public class SendMessageHandler {
    public static void send(String topic, String message) {
        WebSocketMessagePublisher.publish(topic, message);
    }
}
```
## 后续发展
- ~~发送消息支持失败重试~~（0.1.7）
- ~~发送消息支持队列缓存，重连后立即推送（单订阅者）~~（0.1.7）
- ~~发送数据支持多种数据类型~~（0.3.0）
- ~~接收数据支持自定义数据类型~~（0.3.0）
- ~~支持注解方式接入~~（0.4.1）
