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
        for (int i = 0; i < request.getTopic().length; i++) {
            String topic = request.getTopic()[i];
            String scope = null;
            if (request.getScope() != null && request.getScope().length > i) {
                scope = request.getScope()[i];
            }
            WebSocketClientGroup group = WebSocketClientService.getClientGroup();
            if (group.containsKey(topic)) {
                WebSocketClientMap map = group.get(topic);
                if (map.containsKey(request.getContext().channel().id())) {
                    WebSocketClient client = map.get(request.getContext().channel().id());
                    WebSocketServerService.onMessage(client, topic, scope, request.getData());
                }
            }
        }
    }
}
