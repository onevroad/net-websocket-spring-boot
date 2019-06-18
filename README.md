# net-websocket-spring-boot-starter

## 设计目的
让大家能更方便的使用websocket

## 通信数据格式
```json
{
  "event": "事件类型",//目前支持：subscribe(订阅), message(通信), cancel(取消订阅), heartbeat(心跳，由系统自动发送)
  "topic": ["主题信息"],
  "data": "自定义数据"
}
```

## 使用方式
1. 添加依赖
```
<dependency>
    <groupId>org.net.websocket</groupId>
    <artifactId>net-websocket-spring-boot-starter</artifactId>
    <version>1.0.0</version>
</dependency>
```

2. 自己实现WebSocketEventHandler或WebSocketCustomizeEventHandler接口，并添加注解和主题信息@MessageListener("test")，WebSocketCustomizeEventHandler主题信息在equalsTopic方法中配置
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

3. 配置扫描路径
```java
@EnableAutoConfiguration
@WebSocketScan(basePackages = {"org.net.websocket.samples.handler"})
public class WebSocketApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebSocketApplication.class).run(args);
    }
}
```

4. 发送消息使用WebSocketMessagePublisher的publish，由两个参数：topic: 主题信息; message: 消息内容
```java
WebSocketMessagePublisher.publish("test", "推送消息");
```
