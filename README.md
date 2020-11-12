# net-websocket-spring-boot-starter [![License](http://img.shields.io/:license-apache-brightgreen.svg)](http://www.apache.org/licenses/LICENSE-2.0.html)

The WebSocket is based on netty. It's easier to use for somebody who want to build a WebSocket in a short time.

Support jdk version 1.8 or 1.8+

[中文文档](https://github.com/onevroad/net-websocket-spring-boot/blob/master/README_cn.md "中文文档")

## Request Data Format
```json
{
  "e": "event type",
  "t": ["topic name"],
  "d": "data"
}
```
- e:event, t:topic, d:data
- support event type：subscribe, message, cancel, heartbeat
- Except for the heartbeat event, the topic is a required param.You can send multiple topics at the same time.
- You can customize the response data format.

## Heartbeat Event
- The server will send a heartbeat event when the client and server have no data interaction within 1 minute. The data format that the server send is like this:
```json
{
  "e": "heartbeat",
  "d": "ping"
}
```
- If the client receive a heartbeat event, please send a data to the server. The data format is like this:
```json
{
  "e": "heartbeat",
  "d": "pong"
}
```
- The connection will be disconnected when the server who sent the heartbeat event twice din't receive any response.

## Quick Start
- add maven dependency
```
<dependency>
    <groupId>org.onevroad</groupId>
    <artifactId>net-websocket-spring-boot-starter</artifactId>
    <version>0.4.1</version>
</dependency>
```

- You have two choices to handle event, add annotation or implement WebSocketEventHandler<Request, Response> or WebSocketCustomizeEventHandler<Request, Response> for every topic that you have.

### Add annotation
Add the annotation on the method to handle event.  
You can define the topic for every annotation.
The priority of topic is: method annotation topic > class annotation topic 
The supporting params is: topic and data. You need add RequestTopic annotation for the topic and add RequestData annotation for the data.
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

### Implement interface
For the definite topics, you can implement WebSocketEventHandler<Request, Response> with the WebsocketListener annotation.
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
For the dynamic topics, you can implement WebSocketCustomizeEventHandler<Request, Response> and override the equalsTopic method.
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

- configure the scan package where your implementers are.
```java
@EnableAutoConfiguration
@WebSocketScan(basePackages = {"org.net.websocket.samples.handler"})
public class WebSocketApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(WebSocketApplication.class).run(args);
    }
}
```

- configure parameter
```yaml
net:
  websocket:
    # Listening port
    port: 80
    # The number of listening threads, default 1 thread
    boss-group-threads: 1
    # The number of working threads, default 0 is the number of CPU cores
    worker-group-threads: 0
    # Request path
    end-point: /ws
```

- send message

You can use the method of the WebSocketMessagePublisher class to send your message.
```java
public class SendMessageHandler {
    public static void send(String topic, String message) {
        WebSocketMessagePublisher.publish(topic, message);
    }
}
```

