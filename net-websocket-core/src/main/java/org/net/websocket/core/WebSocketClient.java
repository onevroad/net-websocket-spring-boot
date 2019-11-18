package org.net.websocket.core;

import static org.net.websocket.core.Constants.HEARTBEAT_TEXT;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.ArrayList;
import java.util.List;

public class WebSocketClient {

    private Channel channel;
    private List<String> topics = new ArrayList<>();
    private Long lastUpdateTime = System.currentTimeMillis();
    private Long inactiveTime = 60000L;

    public WebSocketClient(Channel channel) {
        this.channel = channel;
    }

    public void send(String topic, String message) {
        if (this.topics.contains(topic)) {
            sendSimple(topic, message);
        }
    }

    public void sendSimple(String topic, String message) {
        ChannelFuture channelFuture = channel.write(new TextWebSocketFrame(message));
        channelFuture.addListener(new WebSocketChannelListener(new RetryMessage(this, topic, message)));
        channel.flush();
        lastUpdateTime = System.currentTimeMillis();
    }

    public void send(RetryMessage retryMessage) {
        ChannelFuture channelFuture = channel.write(new TextWebSocketFrame(retryMessage.getMessage()));
        channelFuture.addListener(new WebSocketChannelListener(retryMessage));
        channel.flush();
        lastUpdateTime = System.currentTimeMillis();
    }

    public void sendHeartbeat() {
        channel.writeAndFlush(new TextWebSocketFrame(HEARTBEAT_TEXT));
    }

    public void receiveHeartbeat() {
        lastUpdateTime = System.currentTimeMillis();
    }

    public void subscribe(String topic) {
        if (!this.topics.contains(topic)) {
            this.topics.add(topic);
        }
        lastUpdateTime = System.currentTimeMillis();
    }

    public void cancel(String data) {
        for (String topic : topics) {
            WebSocketServerService.onCancel(this, topic, data);
        }
        topics.clear();
        lastUpdateTime = System.currentTimeMillis();
    }

    public void cancel(String topic, String data) {
        if (this.topics.contains(topic)) {
            this.topics.remove(topic);
            WebSocketServerService.onCancel(this, topic, data);
        }
        lastUpdateTime = System.currentTimeMillis();
    }

    public boolean isActive() {
        return System.currentTimeMillis() - lastUpdateTime <= inactiveTime;
    }

    public boolean needClose() {
        return System.currentTimeMillis() - lastUpdateTime > inactiveTime * 3;
    }

    public void close() {
        channel.close();
    }
}
