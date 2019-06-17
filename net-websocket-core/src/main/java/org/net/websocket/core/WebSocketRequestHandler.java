package org.net.websocket.core;

public class WebSocketRequestHandler {

    public static void execute(WebSocketRequest request) {
        if (request.getEvent() != null) {
            if ("subscribe".equals(request.getEvent())) {
                WebSocketCommandExecutor.execute(new WebSocketSubscribeCommand(request));
            } else if ("heartbeat".equals(request.getEvent())) {
                WebSocketCommandExecutor.execute(new WebSocketHeartbeatCommand(request));
            } else if ("cancel".equals(request.getEvent())) {
                WebSocketCommandExecutor.execute(new WebSocketCancelCommand(request));
            } else if ("message".equals(request.getEvent())) {
                WebSocketCommandExecutor.execute(new WebSocketMessageCommand(request));
            }
        }
    }
}
