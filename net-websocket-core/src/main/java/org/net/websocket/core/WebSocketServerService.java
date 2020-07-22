package org.net.websocket.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebSocketServerService {

    private static int bossGroupThreads = 1;
    private static int workerGroupThreads = 0;
    private static String endPoint = "/ws";
    private static WebSocketServer server = null;
    private static Map<String, List<WebSocketEventHandler>> handlers = new HashMap<>();
    private static List<WebSocketCustomizeEventHandler> customizeHandlers = new ArrayList<>();

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

    public static void onSubscribe(WebSocketClient client, String topic, Object data) {
        List<WebSocketEventHandler> webSocketEventHandlers = handlers.get(topic);
        if (webSocketEventHandlers != null && !webSocketEventHandlers.isEmpty()) {
            for (WebSocketEventHandler handler : webSocketEventHandlers) {
                Object message = handler.onSubscribe(topic, ObjectConvert.convertRequestData(data, handler.getClass()));
                if (message != null) {
                    client.sendSimple(topic, ObjectConvert.toJson(message));
                }
            }
        }
        for (WebSocketCustomizeEventHandler customizeHandler : customizeHandlers) {
            if (customizeHandler.equalsTopic(topic)) {
                Object message = customizeHandler.onSubscribe(topic, ObjectConvert.convertRequestData(data, customizeHandler.getClass()));
                if (message != null) {
                    client.sendSimple(topic, ObjectConvert.toJson(message));
                }
            }
        }
    }

    public static void onMessage(WebSocketClient client, String topic, Object data) {
        List<WebSocketEventHandler> webSocketEventHandlers = handlers.get(topic);
        if (webSocketEventHandlers != null && !webSocketEventHandlers.isEmpty()) {
            for (WebSocketEventHandler handler : webSocketEventHandlers) {
                Object message = handler.onMessage(topic, ObjectConvert.convertRequestData(data, handler.getClass()));
                if (message != null) {
                    client.sendSimple(topic, ObjectConvert.toJson(message));
                }
            }
        }
        for (WebSocketCustomizeEventHandler customizeHandler : customizeHandlers) {
            if (customizeHandler.equalsTopic(topic)) {
                Object message = customizeHandler.onMessage(topic, ObjectConvert.convertRequestData(data, customizeHandler.getClass()));
                if (message != null) {
                    client.sendSimple(topic, ObjectConvert.toJson(message));
                }
            }
        }
    }

    public static void onCancel(WebSocketClient client, String topic, Object data) {
        List<WebSocketEventHandler> webSocketEventHandlers = handlers.get(topic);
        if (webSocketEventHandlers != null && !webSocketEventHandlers.isEmpty()) {
            for (WebSocketEventHandler handler : webSocketEventHandlers) {
                Object message = handler.onCancel(topic, ObjectConvert.convertRequestData(data, handler.getClass()));
                if (message != null) {
                    client.sendSimple(topic, ObjectConvert.toJson(message));
                }
            }
        }
        for (WebSocketCustomizeEventHandler customizeHandler : customizeHandlers) {
            if (customizeHandler.equalsTopic(topic)) {
                Object message = customizeHandler.onCancel(topic, ObjectConvert.convertRequestData(data, customizeHandler.getClass()));
                if (message != null) {
                    client.sendSimple(topic, ObjectConvert.toJson(message));
                }
            }
        }
    }
}
