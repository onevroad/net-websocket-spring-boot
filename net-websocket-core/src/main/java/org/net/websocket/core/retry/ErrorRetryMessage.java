package org.net.websocket.core.retry;

import lombok.Getter;
import org.net.websocket.core.client.WebSocketClient;

@Getter
public class ErrorRetryMessage {

    private String topic;

    private String message;

    private WebSocketClient client;

    private int retryTime = 0;

    private long lastSendTime = System.currentTimeMillis();

    public ErrorRetryMessage(WebSocketClient client, String topic, String message) {
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
