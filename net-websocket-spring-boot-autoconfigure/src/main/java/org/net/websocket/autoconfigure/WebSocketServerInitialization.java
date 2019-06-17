package org.net.websocket.autoconfigure;

import org.net.websocket.annotation.MessageListener;
import org.net.websocket.core.WebSocketCustomizeEventHandler;
import org.net.websocket.core.WebSocketEventHandler;
import org.net.websocket.core.WebSocketServerService;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;


public class WebSocketServerInitialization implements BeanPostProcessor {

    private WebsocketProperties properties;

    public WebSocketServerInitialization(WebsocketProperties properties) {
        this.properties = properties;
    }

    public void init() {
        WebSocketServerService.start(properties.getPort(), properties.getBossGroupThreads(), properties.getWorkerGroupThreads(), properties.getEndPoint());
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = AopUtils.getTargetClass(bean);
        MessageListener annotation = clazz.getAnnotation(MessageListener.class);
        if (annotation != null) {
            if (clazz.isInstance(WebSocketEventHandler.class)) {
                WebSocketServerService.addHandler(annotation.value(), (WebSocketEventHandler) bean);
            } else if (clazz.isInstance(WebSocketCustomizeEventHandler.class)) {
                WebSocketServerService.addCustomizeHandler((WebSocketCustomizeEventHandler) bean);
            }
        }
        return bean;
    }
}
