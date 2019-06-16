package org.mywebsocket.core;

import io.netty.channel.ChannelHandlerContext;


public class WebSocketClientService {

    private static WebSocketClientGroup group = new WebSocketClientGroup();

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
            WebSocketClient client = new WebSocketClient(context.channel());
            client.subscribe(topic);
            map.put(context.channel().id(), client);
            return client;
        }
    }

    public static void remove(ChannelHandlerContext context) {
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
