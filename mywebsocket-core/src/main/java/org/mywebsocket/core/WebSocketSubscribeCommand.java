package org.mywebsocket.core;


public class WebSocketSubscribeCommand implements Runnable {

    private WebSocketRequest request;

    public WebSocketSubscribeCommand(WebSocketRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        for (String topic : request.getTopic()) {
            WebSocketClient client = WebSocketClientService.subscribe(request.getContext(), topic);
            WebSocketServerService.onSubscribe(client, topic, request.getData());
        }
    }
}
