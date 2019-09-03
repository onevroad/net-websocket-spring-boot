package org.net.websocket.core;

import lombok.Getter;

@Getter
public class RetryMessage {

    private String topic;

    private String message;

    private WebSocketClient client;

    private int retryTime = 0;

    private long lastSendTime = System.currentTimeMillis();

    public RetryMessage(WebSocketClient client, String topic, String message) {
        this.client = client;
        this.topic = topic;
        this.message = message;
    }

    public void retrySend() {
        retryTime++;
        lastSendTime = System.currentTimeMillis();
        client.send(this);
    }
}
