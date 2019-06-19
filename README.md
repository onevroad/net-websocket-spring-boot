# net-websocket-spring-boot-starter

## 设计目的
让大家能更方便的使用websocket

## 请求数据格式
```json
{
  "event": "事件类型",
  "topic": ["主题信息"],
  "data": "自定义数据"
}
```
- 事件类型目前支持：subscribe(订阅), message(通信), cancel(取消订阅), heartbeat(心跳，由系统自动发送)
- 除心跳事件外，topic为必填项，可同时发送多个topic
- 响应数据格式无要求，可自定义返回

## 心跳事件
- 当客户端和服务端在1分钟内无数据交互时，服务端会发送心跳事件，数据格式如下：
```json
{
  "event": "heartbeat",
  "data": "ping"
}
```
- 客户端收到心跳数据时，请发送以下数据进行响应：
```json
{
  "event": "heartbeat",
  "data": "pong"
}
```
- 若服务端发送2次心跳事件仍无响应时，会断开连接

## 快速开始
- 添加依赖
```
<dependency>
    <groupId>org.net.websocket</groupId>
    <artifactId>net-websocket-spring-boot-starter</artifactId>
    <version>.1.0</version>
</dependency>
```

- 定义每个topic的事件处理器，返回值是对客户端的响应数据，返回值为空则不响应

对于确定的topic，可实现WebSocketEventHandler事件处理器，通过注解管理topic
```java
@MessageListener("test")
public class SampleMessageEventHandler implements WebSocketEventHandler<String> {
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
对应动态的topic，可实现WebSocketCustomizeEventHandler事件处理器，通过equalsTopic管理topic
```java
@MessageListener
public class SampleMessageCustomizeEventHandler implements WebSocketCustomizeEventHandler<String> {
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

- 发送消息

使用WebSocketMessagePublisher的publish，有两个参数：topic: 主题信息; message: 消息内容
```java
WebSocketMessagePublisher.publish("topic", "消息内容");
```
