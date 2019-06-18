package org.net.websocket.core;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

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

}
