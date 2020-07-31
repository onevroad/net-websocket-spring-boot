package org.net.websocket.core.server;

import lombok.extern.slf4j.Slf4j;
import org.net.websocket.core.channel.WebSocketChannelCheckCommandExecutor;
import org.net.websocket.core.client.WebSocketClient;
import org.net.websocket.core.handler.WebSocketCustomizeEventHandler;
import org.net.websocket.core.handler.WebSocketEventHandler;
import org.net.websocket.core.handler.method.WebSocketHandlerMethod;
import org.net.websocket.core.util.ObjectConverter;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class WebSocketServerService {

    private static int bossGroupThreads = 1;
    private static int workerGroupThreads = 0;
    private static String endPoint = "/ws";
    private static WebSocketServer server = null;
    private static Map<String, List<WebSocketEventHandler>> handlers = new HashMap<>();
    private static List<WebSocketCustomizeEventHandler> customizeHandlers = new ArrayList<>();
    private static Map<String, List<WebSocketHandlerMethod>> subscribeHandlerMethods = new HashMap<>();
    private static Map<String, List<WebSocketHandlerMethod>> messageHandlerMethods = new HashMap<>();
    private static Map<String, List<WebSocketHandlerMethod>> cancelHandlerMethods = new HashMap<>();

    public static void start(int port) {
        start(port, endPoint);
    }

    public static void start(int port, String endPoint) {
        start(port, bossGroupThreads, workerGroupThreads, endPoint);
    }

    public static void start(int port, int bossGroupThreads, int workerGroupThreads) {
        start(port, bossGroupThreads, workerGroupThreads, endPoint);
    }

    public static void start(int port, int bossGroupThreads, int workerGroupThreads, String endPoint) {
        WebSocketChannelCheckCommandExecutor.start();
        server = new WebSocketServer(port, bossGroupThreads, workerGroupThreads, endPoint);
        server.run();
    }

    public static void addHandler(String topic, WebSocketEventHandler handler) {
        handlers.computeIfAbsent(topic, k -> new ArrayList<>()).add(handler);
    }

    public static void addCustomizeHandler(WebSocketCustomizeEventHandler customizeHandler) {
        customizeHandlers.add(customizeHandler);
    }

    public static void addSubscribeHandlerMethod(String topic, WebSocketHandlerMethod handlerMethod) {
        subscribeHandlerMethods.computeIfAbsent(topic, k -> new ArrayList<>()).add(handlerMethod);
    }

    public static void addMessageHandlerMethod(String topic, WebSocketHandlerMethod handlerMethod) {
        messageHandlerMethods.computeIfAbsent(topic, k -> new ArrayList<>()).add(handlerMethod);
    }

    public static void addCancelHandlerMethod(String topic, WebSocketHandlerMethod handlerMethod) {
        cancelHandlerMethods.computeIfAbsent(topic, k -> new ArrayList<>()).add(handlerMethod);
    }

    public static void onSubscribe(WebSocketClient client, String topic, Object data) {
        List<WebSocketEventHandler> webSocketEventHandlers = handlers.get(topic);
        if (webSocketEventHandlers != null && !webSocketEventHandlers.isEmpty()) {
            for (WebSocketEventHandler handler : webSocketEventHandlers) {
                Object message = handler.onSubscribe(topic, ObjectConverter.convertRequestData(data, handler.getClass()));
                if (message != null) {
                    client.sendSimple(topic, ObjectConverter.toJson(message));
                }
            }
        }

        for (WebSocketCustomizeEventHandler customizeHandler : customizeHandlers) {
            if (customizeHandler.equalsTopic(topic)) {
                Object message = customizeHandler.onSubscribe(topic, ObjectConverter.convertRequestData(data, customizeHandler.getClass()));
                if (message != null) {
                    client.sendSimple(topic, ObjectConverter.toJson(message));
                }
            }
        }

        executeHandlerMethod(client, topic, data, subscribeHandlerMethods);
    }

    public static void onMessage(WebSocketClient client, String topic, Object data) {
        List<WebSocketEventHandler> webSocketEventHandlers = handlers.get(topic);
        if (webSocketEventHandlers != null && !webSocketEventHandlers.isEmpty()) {
            for (WebSocketEventHandler handler : webSocketEventHandlers) {
                Object message = handler.onMessage(topic, ObjectConverter.convertRequestData(data, handler.getClass()));
                if (message != null) {
                    client.sendSimple(topic, ObjectConverter.toJson(message));
                }
            }
        }

        for (WebSocketCustomizeEventHandler customizeHandler : customizeHandlers) {
            if (customizeHandler.equalsTopic(topic)) {
                Object message = customizeHandler.onMessage(topic, ObjectConverter.convertRequestData(data, customizeHandler.getClass()));
                if (message != null) {
                    client.sendSimple(topic, ObjectConverter.toJson(message));
                }
            }
        }

        executeHandlerMethod(client, topic, data, messageHandlerMethods);
    }

    public static void onCancel(WebSocketClient client, String topic, Object data) {
        List<WebSocketEventHandler> webSocketEventHandlers = handlers.get(topic);
        if (webSocketEventHandlers != null && !webSocketEventHandlers.isEmpty()) {
            for (WebSocketEventHandler handler : webSocketEventHandlers) {
                Object message = handler.onCancel(topic, ObjectConverter.convertRequestData(data, handler.getClass()));
                if (message != null) {
                    client.sendSimple(topic, ObjectConverter.toJson(message));
                }
            }
        }

        for (WebSocketCustomizeEventHandler customizeHandler : customizeHandlers) {
            if (customizeHandler.equalsTopic(topic)) {
                Object message = customizeHandler.onCancel(topic, ObjectConverter.convertRequestData(data, customizeHandler.getClass()));
                if (message != null) {
                    client.sendSimple(topic, ObjectConverter.toJson(message));
                }
            }
        }

        executeHandlerMethod(client, topic, data, cancelHandlerMethods);
    }

    private static void executeHandlerMethod(WebSocketClient client, String topic, Object data, Map<String, List<WebSocketHandlerMethod>> handlerMethods) {
        List<WebSocketHandlerMethod> webSocketHandlerMethods = handlerMethods.get(topic);
        if (webSocketHandlerMethods != null && !webSocketHandlerMethods.isEmpty()) {
            for (WebSocketHandlerMethod handlerMethod : webSocketHandlerMethods) {
                Object message = doInvoke(handlerMethod, topic, data);
                if (message != null) {
                    client.sendSimple(topic, ObjectConverter.toJson(message));
                }
            }
        }
    }

    private static Object doInvoke(WebSocketHandlerMethod handlerMethod, String topic, Object data) {
        Object message = null;
        if (handlerMethod != null) {
            try {
                message = handlerMethod.invoke(topic, data);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
        return message;
    }
}
