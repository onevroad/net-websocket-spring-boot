package org.net.websocket.core.client;

import static org.net.websocket.core.common.Constants.HEARTBEAT_TEXT;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;
import org.net.websocket.core.server.WebSocketServerService;
import org.net.websocket.core.channel.WebSocketChannelListener;
import org.net.websocket.core.retry.ErrorRetryMessage;

import java.util.ArrayList;
import java.util.List;

@Slf4j
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
        channelFuture.addListener(new WebSocketChannelListener(new ErrorRetryMessage(this, topic, message)));
        channel.flush();
        lastUpdateTime = System.currentTimeMillis();
    }

    public void send(ErrorRetryMessage errorRetryMessage) {
        ChannelFuture channelFuture = channel.write(new TextWebSocketFrame(errorRetryMessage.getMessage()));
        channelFuture.addListener(new WebSocketChannelListener(errorRetryMessage));
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

    public void cancel(Object data) {
        for (String topic : topics) {
            WebSocketServerService.onCancel(this, topic, data);
        }
        topics.clear();
        lastUpdateTime = System.currentTimeMillis();
    }

    public void cancel(String topic, Object data) {
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
