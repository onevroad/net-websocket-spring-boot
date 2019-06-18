package org.net.websocket.core;


import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;

@Slf4j
public class WebSocketChannelCheckCommand implements Runnable {
    @Override
    public void run() {
        try {
            WebSocketClientMap clients = WebSocketClientService.getClients();
            Iterator<WebSocketClient> iterator = clients.values().iterator();
            while (iterator.hasNext()) {
                WebSocketClient client = iterator.next();
                if (client.needClose()) {
                    client.close();
                } else if (!client.isActive()) {
                    client.sendHeartbeat();
                }
            }
        } catch (Exception e) {
            log.error("WebSocket channel check error: {}", e.getMessage(), e);
        }
    }
}
