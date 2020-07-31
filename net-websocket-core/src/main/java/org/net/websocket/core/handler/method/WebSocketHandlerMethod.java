package org.net.websocket.core.handler.method;

import lombok.Getter;
import org.net.websocket.core.server.WebSocketMessage;
import org.net.websocket.core.util.Assert;
import org.net.websocket.core.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

@Getter
public class WebSocketHandlerMethod {

    private final Object bean;

    private final Class<?> beanType;

    private final Method method;

    private final Parameter[] parameters;

    private final List<HandlerMethodArgumentResolver> resolvers;

    public WebSocketHandlerMethod(Object bean, Method method) {
        Assert.notNull(bean, "Bean is required");
        Assert.notNull(method, "Method is required");
        this.bean = bean;
        this.beanType = ClassUtils.getUserClass(bean);
        this.method = method;
        this.parameters = method.getParameters();
        this.resolvers = initResolvers();
    }

    public Object invoke(String topic, Object data) throws Exception {
        WebSocketMessage<Object> message = new WebSocketMessage<>();
        message.setTopic(topic);
        message.setData(data);

        Object[] args = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            HandlerMethodArgumentResolver resolver = getArgumentResolver(parameter);
            if (resolver == null) {
                throw new IllegalArgumentException("Unknown parameter type [" + parameter.getName() + "]");
            }

            args[i] = resolver.resolveArgument(parameter, message);

            if (args[i] == null) {
                throw new IllegalStateException("Could not resolve method parameter at index " + i + " in " + beanType.toGenericString() +
                        ": No suitable resolver for argument " + i + " of type '" + parameter.getName() + "'");
            }
        }
        return this.method.invoke(bean, args);
    }

    private List<HandlerMethodArgumentResolver> initResolvers() {
        List<HandlerMethodArgumentResolver> resolvers = new ArrayList<>();
        resolvers.add(new RequestTopicHandlerMethodArgumentResolver());
        resolvers.add(new RequestDataHandlerMethodArgumentResolver());
        return resolvers;
    }

    private HandlerMethodArgumentResolver getArgumentResolver(Parameter parameter) {
        HandlerMethodArgumentResolver result = null;
        for (HandlerMethodArgumentResolver resolver : resolvers) {
            if (resolver.supportsParameter(parameter)) {
                result = resolver;
                break;
            }
        }
        return result;
    }
}
