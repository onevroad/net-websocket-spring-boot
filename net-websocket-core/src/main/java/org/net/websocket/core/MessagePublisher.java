package org.net.websocket.core;


public class MessagePublisher {

    public static void publish(String topic, String message) {
        WebSocketClientService.publish(topic, message);
    }
}
