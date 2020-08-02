package org.net.websocket.core.handler.method;

import org.net.websocket.core.util.ObjectConverter;

import java.lang.reflect.Parameter;

public class RequestDataConverter {

    public Object convert(Parameter parameter, Object value) {
        return ObjectConverter.convert(value, parameter.getType());
    }
}
