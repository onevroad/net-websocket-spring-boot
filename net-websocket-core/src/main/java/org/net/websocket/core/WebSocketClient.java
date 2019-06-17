package org.net.websocket.core;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.ArrayList;
import java.util.List;

public class WebSocketClient {

    private Channel channel;
    private List<String> topics = new ArrayList<>();
    private Long lastUpdateTime = System.currentTimeMillis();
    private Long inactiveTime = 60000L;
    private TextWebSocketFrame heartbeat = new TextWebSocketFrame("{\"event\":\"heartbeat\",\"data\":\"ping\"}");

    public WebSocketClient(Channel channel) {
        this.channel = channel;
    }

    public void send(String topic, String message) {
        if (this.topics.contains(topic)) {
            channel.writeAndFlush(new TextWebSocketFrame(message));
            lastUpdateTime = System.currentTimeMillis();
        }
    }

    public void send(String message) {
        channel.writeAndFlush(new TextWebSocketFrame(message));
        lastUpdateTime = System.currentTimeMillis();
    }

    public void sendHeartbeat() {
        channel.writeAndFlush(heartbeat);
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
        return System.currentTimeMillis() - lastUpdateTime > inactiveTime;
    }
}
