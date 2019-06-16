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
                cancel(request.getData());
                break;
            } else {
                cancel(topic, request.getData());
            }
        }
    }

    private void cancel(String data) {
        WebSocketClientGroup group = WebSocketClientService.getClientGroup();
        for (WebSocketClientMap map : group.values()) {
            if (map.containsKey(request.getContext().channel().id())) {
                WebSocketClient client = map.get(request.getContext().channel().id());
                client.cancel(data);
            }
        }
    }

    private void cancel(String topic, String data) {
        WebSocketClientGroup group = WebSocketClientService.getClientGroup();
        if (group.containsKey(topic)) {
            WebSocketClientMap map = group.get(topic);
            if (map.containsKey(request.getContext().channel().id())) {
                WebSocketClient client = map.get(request.getContext().channel().id());
                client.cancel(topic, data);
            }
        }
    }
}
