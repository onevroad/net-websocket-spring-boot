package org.net.websocket.core.server;


import lombok.extern.slf4j.Slf4j;
import org.net.websocket.core.client.WebSocketClient;
import org.net.websocket.core.client.WebSocketClientService;

@Slf4j
public class WebSocketSubscribeCommand implements Runnable {

    private WebSocketRequest request;

    public WebSocketSubscribeCommand(WebSocketRequest request) {
        this.request = request;
    }

    @Override
    public void run() {
        for (int i = 0; i < request.getTopic().length; i++) {
            String topic = request.getTopic()[i];
            WebSocketClient client = WebSocketClientService.subscribe(request.getContext(), topic);
            WebSocketServerService.onSubscribe(client, topic, request.getData());
        }
    }
}
