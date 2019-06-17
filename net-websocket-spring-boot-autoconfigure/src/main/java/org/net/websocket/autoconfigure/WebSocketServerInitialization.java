package org.net.websocket.autoconfigure;

import org.net.websocket.core.WebSocketServerService;


public class WebSocketServerInitialization {

    private WebsocketProperties properties;

    public WebSocketServerInitialization(WebsocketProperties properties) {
        this.properties = properties;
    }

    public void init() {
        WebSocketServerService.start(properties.getPort(), properties.getBossGroupThreads(), properties.getWorkerGroupThreads(), properties.getEndPoint());
    }

}
