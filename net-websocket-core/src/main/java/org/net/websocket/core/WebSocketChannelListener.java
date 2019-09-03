package org.net.websocket.core;

import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketChannelListener implements GenericFutureListener<ChannelFuture> {

    private RetryMessage message;

    public WebSocketChannelListener(RetryMessage message) {
        this.message = message;
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (channelFuture.cause() != null) {
            WebSocketRetryService.retry(message);
            log.warn("WebSocket send message failed by error!", channelFuture.cause());
        }
        if (channelFuture.isCancelled()) {
            WebSocketRetryService.retry(message);
            log.warn("WebSocket send message failed by cancellation!");
        }
    }
}
