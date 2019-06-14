package org.mywebsocket.core;


public class WebSocketCancelCommand implements Runnable {

    private WebSocketRequest request;

    public WebSocketCancelCommand(WebSocketRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        for (String topic : request.getTopic()) {
            if ("all".equals(topic)) {
                cancel();
                break;
            } else {
                cancel(topic);
            }
        }
    }

    private void cancel() {
        WebSocketClientGroup group = WebSocketService.getClientGroup();
        for (WebSocketClientMap map : group.values()) {
            if (map.containsKey(request.getContext().channel().id())) {
                WebSocketClient client = map.get(request.getContext().channel().id());
                client.cancel();
            }
        }
    }

    private void cancel(String topic) {
        WebSocketClientGroup group = WebSocketService.getClientGroup();
        if (group.containsKey(topic)) {
            WebSocketClientMap map = group.get(topic);
            if (map.containsKey(request.getContext().channel().id())) {
                WebSocketClient client = map.get(request.getContext().channel().id());
                client.cancel(topic);
            }
        }
    }
}
