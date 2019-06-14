package org.mywebsocket.core;

public class WebSocketHeartbeatCommand implements Runnable {

    private WebSocketRequest request;

    public WebSocketHeartbeatCommand(WebSocketRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        if ("pong".equals(request.getData())) {
            receiveHeartbeat();
        }
    }

    private void receiveHeartbeat() {
        WebSocketClientGroup group = WebSocketService.getClientGroup();
        for (WebSocketClientMap map : group.values()) {
            if (map.containsKey(request.getContext().channel().id())) {
                WebSocketClient client = map.get(request.getContext().channel().id());
                client.receiveHeartbeat();
            }
        }
    }
}
