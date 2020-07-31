package org.net.websocket.core.channel;

import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.extern.slf4j.Slf4j;
import org.net.websocket.core.retry.WebSocketRetryService;
import org.net.websocket.core.retry.ErrorRetryMessage;

@Slf4j
public class WebSocketChannelListener implements GenericFutureListener<ChannelFuture> {

    private ErrorRetryMessage message;

    public WebSocketChannelListener(ErrorRetryMessage message) {
        this.message = message;
    }

    @Override
    public void operationComplete(ChannelFuture channelFuture) {
        if (channelFuture.cause() != null) {
            WebSocketRetryService.retry(message);
            log.warn("WebSocket send message failed by error!", channelFuture.cause());
        }
        if (channelFuture.isCancelled()) {
            WebSocketRetryService.retry(message);
            log.warn("WebSocket send message failed by cancelled!");
        }
    }
}
