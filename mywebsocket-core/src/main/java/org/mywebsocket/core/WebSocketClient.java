package org.mywebsocket.core;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.ArrayList;
import java.util.List;

public class WebSocketClient {

    private Channel channel;
    private List<String> topic = new ArrayList<>();
    private Long lastUpdateTime = System.currentTimeMillis();
    private Long inactiveTime = 60000L;
    private TextWebSocketFrame heartbeat = new TextWebSocketFrame("{\"event\":\"heartbeat\",\"data\":\"ping\"}");

    public WebSocketClient(Channel channel) {
        this.channel = channel;
    }

    public void send(String topic, String message) {
        if (this.topic.contains(topic)) {
            channel.writeAndFlush(new TextWebSocketFrame(message));
            lastUpdateTime = System.currentTimeMillis();
        }
    }

    public void sendHeartbeat() {
        channel.writeAndFlush(heartbeat);
    }

    public void receiveHeartbeat() {
        lastUpdateTime = System.currentTimeMillis();
    }

    public void subscribe(String topic) {
        if (!this.topic.contains(topic)) {
            this.topic.add(topic);
        }
        lastUpdateTime = System.currentTimeMillis();
    }

    public void cancel() {
        topic.clear();
        lastUpdateTime = System.currentTimeMillis();
    }

    public void cancel(String topic) {
        if (this.topic.contains(topic)) {
            this.topic.remove(topic);
        }
        lastUpdateTime = System.currentTimeMillis();
    }

    public boolean isActive() {
        return System.currentTimeMillis() - lastUpdateTime > inactiveTime;
    }
}
