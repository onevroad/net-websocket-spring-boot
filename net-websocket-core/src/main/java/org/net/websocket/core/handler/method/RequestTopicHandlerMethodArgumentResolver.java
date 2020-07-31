package org.net.websocket.core.handler.method;

import org.net.websocket.core.annotation.RequestTopic;
import org.net.websocket.core.server.WebSocketMessage;

import java.lang.reflect.Parameter;

public class RequestTopicHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.getAnnotation(RequestTopic.class) != null;
    }

    @Override
    public Object resolveArgument(Parameter parameter, WebSocketMessage message) {
        return message.getTopic();
    }
}
