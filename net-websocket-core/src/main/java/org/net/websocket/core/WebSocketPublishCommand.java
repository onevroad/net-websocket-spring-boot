package org.net.websocket.core;

import io.netty.channel.ChannelId;
import java.util.Iterator;
import java.util.Map.Entry;

public class WebSocketPublishCommand implements Runnable {

    private String topic;
    private String message;

    public WebSocketPublishCommand(String topic, String message) {
        this.topic = topic;
        this.message = message;
    }

    @Override
    public void run() {
        WebSocketClientGroup group = WebSocketClientService.getClientGroup();
        if (group.containsKey(topic)) {
            WebSocketClientMap clients = group.get(topic);
            Iterator<Entry<ChannelId, WebSocketClient>> iterator = clients.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<ChannelId, WebSocketClient> entry = iterator.next();
                WebSocketClient client = entry.getValue();
                client.send(topic, message);
            }
        }
    }
}
