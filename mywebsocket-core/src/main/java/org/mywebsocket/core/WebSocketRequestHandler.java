package org.mywebsocket.core;

public class WebSocketRequestHandler {

    public static void execute(WebSocketRequest request) {
        if (request.getEvent() != null) {
            if (request.getEvent().equals("subscribe")) {
                WebSocketCommandExecutor.execute(new WebSocketSubscribeCommand(request));
            } else if (request.getEvent().equals("heartbeat")) {
                WebSocketCommandExecutor.execute(new WebSocketHeartbeatCommand(request));
            } else if (request.getEvent().equals("cancel")) {
                WebSocketCommandExecutor.execute(new WebSocketCancelCommand(request));
            } else if (request.getEvent().equals("message")) {
                WebSocketCommandExecutor.execute(new WebSocketMessageCommand(request));
            }
        }
    }
}
