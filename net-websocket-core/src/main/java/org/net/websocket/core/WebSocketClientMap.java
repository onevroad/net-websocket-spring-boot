package org.net.websocket.core;

import io.netty.channel.ChannelId;

import java.util.concurrent.ConcurrentHashMap;

public class WebSocketClientMap extends ConcurrentHashMap<ChannelId, WebSocketClient> {
}
