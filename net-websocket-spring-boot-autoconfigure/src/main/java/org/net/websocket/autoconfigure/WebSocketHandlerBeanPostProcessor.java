package org.net.websocket.autoconfigure;

import org.net.websocket.annotation.WebSocketListener;
import org.net.websocket.core.WebSocketCustomizeEventHandler;
import org.net.websocket.core.WebSocketEventHandler;
import org.net.websocket.core.WebSocketServerService;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

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
            }
        }
        return bean;
    }
}
