package org.mywebsocket.core;


public class WebSocketMessageCommand implements Runnable {

    private WebSocketRequest request;

    public WebSocketMessageCommand(WebSocketRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        for (String topic : request.getTopic()) {
            //TODO 执行通信操作
        }
    }
}
