package org.net.websocket.core.channel;


import lombok.extern.slf4j.Slf4j;
import org.net.websocket.core.client.WebSocketClient;
import org.net.websocket.core.client.WebSocketClientMap;
import org.net.websocket.core.client.WebSocketClientService;

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
