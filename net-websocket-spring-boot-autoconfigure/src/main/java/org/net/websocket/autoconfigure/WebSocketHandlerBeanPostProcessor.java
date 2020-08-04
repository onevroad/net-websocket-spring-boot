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
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Component
public class WebSocketHandlerBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?> clazz = AopUtils.getTargetClass(bean);
        WebSocketListener annotation = AnnotationUtils.findAnnotation(clazz, WebSocketListener.class);
        if (annotation != null) {
            if (WebSocketEventHandler.class.isAssignableFrom(clazz)) {
                if (StringUtils.isEmpty(annotation.topic())) {
                    throw new IllegalArgumentException("The topic is missing on the class: " + clazz.getName());
                }
                WebSocketServerService.addHandler(annotation.topic(), (WebSocketEventHandler) bean);
            } else if (WebSocketCustomizeEventHandler.class.isAssignableFrom(clazz)) {
                WebSocketServerService.addCustomizeHandler((WebSocketCustomizeEventHandler) bean);
            } else {
                addHandlerMethod(bean, clazz, annotation.topic());
            }
        } else {
            addHandlerMethod(bean, clazz, null);
        }
        return bean;
    }

    private void addHandlerMethod(Object bean, Class<?> clazz, String topic) {
        Method[] methods = clazz.getMethods();
        if (methods.length > 0) {
            for (Method method : methods) {
                OnSubscribe onSubscribe = AnnotationUtils.findAnnotation(method, OnSubscribe.class);
                if (onSubscribe != null) {
                    String subscribeTopic = topic;
                    if (!StringUtils.isEmpty(onSubscribe.topic())) {
                        subscribeTopic = onSubscribe.topic();
                    }
                    if (StringUtils.isEmpty(subscribeTopic)) {
                        throw new IllegalArgumentException("The OnSubscribe topic is missing on the method: " + clazz.getName() + "." + method.getName());
                    }
                    WebSocketHandlerMethod handlerMethod = new WebSocketHandlerMethod(bean, method);
                    WebSocketServerService.addSubscribeHandlerMethod(subscribeTopic, handlerMethod);
                }
                OnMessage onMessage = AnnotationUtils.findAnnotation(method, OnMessage.class);
                if (onMessage != null) {
                    String messageTopic = topic;
                    if (!StringUtils.isEmpty(onMessage.topic())) {
                        messageTopic = onMessage.topic();
                    }
                    if (StringUtils.isEmpty(messageTopic)) {
                        throw new IllegalArgumentException("The OnMessage topic is missing on the method: " + clazz.getName() + "." + method.getName());
                    }
                    WebSocketHandlerMethod handlerMethod = new WebSocketHandlerMethod(bean, method);
                    WebSocketServerService.addMessageHandlerMethod(messageTopic, handlerMethod);
                }
                OnCancel onCancel = AnnotationUtils.findAnnotation(method, OnCancel.class);
                if (onCancel != null) {
                    String cancelTopic = topic;
                    if (!StringUtils.isEmpty(onCancel.topic())) {
                        cancelTopic = onCancel.topic();
                    }
                    if (StringUtils.isEmpty(cancelTopic)) {
                        throw new IllegalArgumentException("The OnCancel topic is missing on the method: " + clazz.getName() + "." + method.getName());
                    }
                    WebSocketHandlerMethod handlerMethod = new WebSocketHandlerMethod(bean, method);
                    WebSocketServerService.addCancelHandlerMethod(cancelTopic, handlerMethod);
                }
            }
        }
    }
}
