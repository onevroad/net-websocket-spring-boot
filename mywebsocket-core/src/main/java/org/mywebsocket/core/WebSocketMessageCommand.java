package org.mywebsocket.core;


public class WebSocketMessageCommand implements Runnable {

    private WebSocketRequest request;

    public WebSocketMessageCommand(WebSocketRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        for (String topic : request.getTopic()) {
            WebSocketClientGroup group = WebSocketClientService.getClientGroup();
            if (group.containsKey(topic)) {
                WebSocketClientMap map = group.get(topic);
                if (map.containsKey(request.getContext().channel().id())) {
                    WebSocketClient client = map.get(request.getContext().channel().id());
                    WebSocketServerService.onMessage(client, topic, request.getData());
                }
            }
        }
    }
}
