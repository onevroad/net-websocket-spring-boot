package org.net.websocket.core.server;

import org.net.websocket.core.client.WebSocketClientService;
import org.net.websocket.core.retry.NotFoundRetryMessage;
import org.net.websocket.core.retry.WebSocketRetryService;

public class WebSocketPublishCommand implements Runnable {

    private String topic;
    private String scope;
    private String message;

    public WebSocketPublishCommand(String topic, String scope, String message) {
        this.topic = topic;
        this.scope = scope;
        this.message = message;
    }

    @Override
    public void run() {
        if (!WebSocketClientService.publishSync(topic, scope, message)) {
            NotFoundRetryMessage notFoundRetryMessage = new NotFoundRetryMessage(topic, scope, message);
            WebSocketRetryService.retry(notFoundRetryMessage);
        }
    }
}
