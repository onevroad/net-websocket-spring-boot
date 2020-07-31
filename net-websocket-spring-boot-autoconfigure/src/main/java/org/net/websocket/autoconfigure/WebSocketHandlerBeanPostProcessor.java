package org.net.websocket.autoconfigure;

import org.net.websocket.annotation.OnCancel;
import org.net.websocket.annotation.OnMessage;
import org.net.websocket.annotation.OnSubscribe;
import org.net.websocket.annotation.WebSocketListener;
import org.net.websocket.core.handler.WebSocketCustomizeEventHandler;
import org.net.websocket.core.handler.WebSocketEventHandler;
import org.net.websocket.core.handler.method.WebSocketHandlerMethod;
import org.net.websocket.core.server.WebSocketServerService;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class WebSocketHandlerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = AopUtils.getTargetClass(bean);
        WebSocketListener annotation = clazz.getAnnotation(WebSocketListener.class);
        if (annotation != null) {
            if (WebSocketEventHandler.class.isAssignableFrom(clazz)) {
                WebSocketServerService.addHandler(annotation.value(), (WebSocketEventHandler) bean);
            } else if (WebSocketCustomizeEventHandler.class.isAssignableFrom(clazz)) {
                WebSocketServerService.addCustomizeHandler((WebSocketCustomizeEventHandler) bean);
            } else {
                Method[] methods = clazz.getMethods();
                if (methods.length > 0) {
                    for (Method method : methods) {
                        if (method.getAnnotation(OnSubscribe.class) != null) {
                            WebSocketHandlerMethod handlerMethod = new WebSocketHandlerMethod(bean, method);
                            WebSocketServerService.addSubscribeHandlerMethod(annotation.value(), handlerMethod);
                        } else if (method.getAnnotation(OnMessage.class) != null) {
                            WebSocketHandlerMethod handlerMethod = new WebSocketHandlerMethod(bean, method);
                            WebSocketServerService.addMessageHandlerMethod(annotation.value(), handlerMethod);
                        } else if (method.getAnnotation(OnCancel.class) != null) {
                            WebSocketHandlerMethod handlerMethod = new WebSocketHandlerMethod(bean, method);
                            WebSocketServerService.addCancelHandlerMethod(annotation.value(), handlerMethod);
                        }
                    }
                }
            }
        }
        return bean;
    }
}
