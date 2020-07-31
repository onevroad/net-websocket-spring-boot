package org.net.websocket.core.server;


import lombok.extern.slf4j.Slf4j;
import org.net.websocket.core.client.WebSocketClient;
import org.net.websocket.core.client.WebSocketClientGroup;
import org.net.websocket.core.client.WebSocketClientMap;
import org.net.websocket.core.client.WebSocketClientService;

@Slf4j
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
