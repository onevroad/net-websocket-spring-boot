package org.net.websocket.core.server;

import org.net.websocket.core.channel.WebSocketCancelCommand;

import static org.net.websocket.core.common.Constants.*;

public class WebSocketRequestHandler {

    public static void execute(WebSocketRequest request) {
        if (request.getEvent() != null) {
            if (SUBSCRIBE.equals(request.getEvent())) {
                WebSocketCommandExecutor.execute(new WebSocketSubscribeCommand(request));
            } else if (HEARTBEAT.equals(request.getEvent())) {
                WebSocketCommandExecutor.execute(new WebSocketHeartbeatCommand(request));
            } else if (CANCEL.equals(request.getEvent())) {
                WebSocketCommandExecutor.execute(new WebSocketCancelCommand(request));
            } else if (MESSAGE.equals(request.getEvent())) {
                WebSocketCommandExecutor.execute(new WebSocketMessageCommand(request));
            }
        }
    }
}
