package org.net.websocket.core.server;


import org.net.websocket.core.client.WebSocketClientService;

public class WebSocketMessagePublisher {

    public static void publish(String topic, String scope, String message) {
        WebSocketClientService.publish(topic, scope, message);
    }
}
