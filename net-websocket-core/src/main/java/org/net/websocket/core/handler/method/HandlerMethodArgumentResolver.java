package org.net.websocket.core.handler.method;

import org.net.websocket.core.server.WebSocketMessage;

import java.lang.reflect.Parameter;

public interface HandlerMethodArgumentResolver {

    boolean supportsParameter(Parameter parameter);

    Object resolveArgument(Parameter parameter, WebSocketMessage message);
}
