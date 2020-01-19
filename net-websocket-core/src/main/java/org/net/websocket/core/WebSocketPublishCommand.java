package org.net.websocket.core;

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
