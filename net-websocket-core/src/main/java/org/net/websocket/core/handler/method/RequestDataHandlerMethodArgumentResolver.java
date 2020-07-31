package org.net.websocket.core.handler.method;

import org.net.websocket.core.annotation.RequestData;
import org.net.websocket.core.server.WebSocketMessage;

import java.lang.reflect.Parameter;

public class RequestDataHandlerMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(Parameter parameter) {
        return parameter.getAnnotation(RequestData.class) != null;
    }

    @Override
    public Object resolveArgument(Parameter parameter, WebSocketMessage message) {
        RequestDataConverter converter = new RequestDataConverter();
        return converter.convert(parameter, message.getData());
    }
}
