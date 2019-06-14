package org.mywebsocket.core;


public class WebSocketSubscribeCommand implements Runnable {

    private WebSocketRequest request;

    public WebSocketSubscribeCommand(WebSocketRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        for (String topic : request.getTopic()) {
            WebSocketClient client = WebSocketService.subscribe(request.getContext(), topic);
            //TODO 执行订阅操作
        }
    }
}
