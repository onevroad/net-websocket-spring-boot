package org.net.websocket.core.server;

import org.net.websocket.core.client.WebSocketClientService;
import org.net.websocket.core.retry.NotFoundRetryMessage;
import org.net.websocket.core.retry.WebSocketRetryService;

public class WebSocketPublishCommand implements Runnable {

    private String topic;
    private String message;

    public WebSocketPublishCommand(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    @Override
    public void run() {
        if (!WebSocketClientService.publishSync(topic, message)) {
            NotFoundRetryMessage notFoundRetryMessage = new NotFoundRetryMessage(topic, message);
            WebSocketRetryService.retry(notFoundRetryMessage);
        }
    }
}
