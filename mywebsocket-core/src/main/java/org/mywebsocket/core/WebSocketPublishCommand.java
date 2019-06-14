package org.mywebsocket.core;

public class WebSocketPublishCommand implements Runnable {

    private String topic;
    private String message;

    public WebSocketPublishCommand(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    @Override
    public void run() {
        WebSocketClientGroup group = WebSocketService.getClientGroup();
        if (group.containsKey(topic)) {
            WebSocketClientMap map = group.get(topic);
            for (WebSocketClient client : map.values()) {
                client.send(topic, message);
            }
        }
    }
}
