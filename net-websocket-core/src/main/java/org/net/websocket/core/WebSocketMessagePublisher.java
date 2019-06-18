package org.net.websocket.core;


public class WebSocketMessagePublisher {

    public static void publish(String topic, String message) {
        WebSocketClientService.publish(topic, message);
    }
}
