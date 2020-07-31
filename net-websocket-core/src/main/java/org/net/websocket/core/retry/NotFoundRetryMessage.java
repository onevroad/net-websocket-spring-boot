package org.net.websocket.core.retry;

import lombok.Getter;

@Getter
public class NotFoundRetryMessage {

    private String topic;

    private String message;

    private int retryTime = 0;

    private long lastSendTime = System.currentTimeMillis();

    public NotFoundRetryMessage(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    public void retry() {
        retryTime++;
        lastSendTime = System.currentTimeMillis();
    }
}
