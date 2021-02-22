package org.net.websocket.core.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import java.util.Iterator;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.net.websocket.core.server.WebSocketCommandExecutor;
import org.net.websocket.core.server.WebSocketPublishCommand;

@Slf4j
public class WebSocketClientService {

    private static WebSocketClientMap activeClients = new WebSocketClientMap();
    private static WebSocketClientGroup group = new WebSocketClientGroup();

    public static void active(ChannelHandlerContext context) {
        activeClients.put(context.channel().id(), new WebSocketClient(context.channel()));
    }

    public static void inactive(ChannelHandlerContext context) {
        activeClients.remove(context.channel().id());
    }

    public static WebSocketClient getClient(ChannelHandlerContext context) {
        return activeClients.get(context.channel().id());
    }

    public static WebSocketClientMap getClients() {
        return activeClients;
    }

    public static WebSocketClient subscribe(ChannelHandlerContext context, String topic) {
        if (group.containsKey(topic)) {
            WebSocketClientMap map = group.get(topic);
            return subscribe(context, topic, map);
        } else {
            WebSocketClientMap map = new WebSocketClientMap();
            group.put(topic, map);
            return subscribe(context, topic, map);
        }
    }

    private static WebSocketClient subscribe(ChannelHandlerContext context, String topic, WebSocketClientMap map) {
        if (map.containsKey(context.channel().id())) {
            WebSocketClient client = map.get(context.channel().id());
            client.subscribe(topic);
            return client;
        } else {
            WebSocketClient client = getClient(context);
            client.subscribe(topic);
            map.put(context.channel().id(), client);
            return client;
        }
    }

    public static void remove(ChannelHandlerContext context) {
        inactive(context);
        for (WebSocketClientMap map : group.values()) {
            if (map.containsKey(context.channel().id())) {
                map.remove(context.channel().id());
            }
        }
    }

    public static void publish(String topic, String message) {
        WebSocketCommandExecutor.execute(new WebSocketPublishCommand(topic, message));
    }

    public static WebSocketClientGroup getClientGroup() {
        return group;
    }

    public static boolean publishSync(String topic, String message) {
        if (group.containsKey(topic)) {
            WebSocketClientMap clients = group.get(topic);
            if (clients.isEmpty()) {
                return false;
            }
            Iterator<Entry<ChannelId, WebSocketClient>> iterator = clients.entrySet().iterator();
            while (iterator.hasNext()) {
                Entry<ChannelId, WebSocketClient> entry = iterator.next();
                WebSocketClient client = entry.getValue();
                client.send(topic, message);
            }
        } else {
            return false;
        }
        return true;
    }
}
