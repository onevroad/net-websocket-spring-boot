package org.net.websocket.core.retry;

import lombok.Getter;

@Getter
public class NotFoundRetryMessage {

    private String topic;

    private String scope;

    private String message;

    private int retryTime = 0;

    private long lastSendTime = System.currentTimeMillis();

    public NotFoundRetryMessage(String topic, String scope, String message) {
        this.topic = topic;
        this.scope = scope;
        this.message = message;
    }

    public void retry() {
        retryTime++;
        lastSendTime = System.currentTimeMillis();
    }
}
