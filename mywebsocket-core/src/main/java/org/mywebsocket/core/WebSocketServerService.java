package org.mywebsocket.core;

import com.alibaba.fastjson.JSON;

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
        server = new WebSocketServer(port, bossGroupThreads, workerGroupThreads, endPoint);
        server.run();
    }

    public static void addHandler(String topic, WebSocketEventHandler handler) {
        handlers.computeIfAbsent(topic, k -> new ArrayList<>()).add(handler);
    }

    public static void addCustomizeHandler(WebSocketCustomizeEventHandler customizeHandler) {
        customizeHandlers.add(customizeHandler);
    }

    public static void onSubscribe(WebSocketClient client, String topic, String data) {
        List<WebSocketEventHandler> webSocketEventHandlers = handlers.get(topic);
        if (webSocketEventHandlers != null && !webSocketEventHandlers.isEmpty()) {
            for (WebSocketEventHandler handler : webSocketEventHandlers) {
                Object message = handler.onSubscribe(topic, data);
                if (message != null) {
                    client.send(JSON.toJSONString(message));
                }
            }
        }
        for (WebSocketCustomizeEventHandler customizeHandler : customizeHandlers) {
            if (customizeHandler.equalsTopic(topic)) {
                Object message = customizeHandler.onSubscribe(topic, data);
                if (message != null) {
                    client.send(JSON.toJSONString(message));
                }
            }
        }
    }

    public static void onMessage(WebSocketClient client, String topic, String data) {
        List<WebSocketEventHandler> webSocketEventHandlers = handlers.get(topic);
        if (webSocketEventHandlers != null && !webSocketEventHandlers.isEmpty()) {
            for (WebSocketEventHandler handler : webSocketEventHandlers) {
                Object message = handler.onMessage(topic, data);
                if (message != null) {
                    client.send(JSON.toJSONString(message));
                }
            }
        }
        for (WebSocketCustomizeEventHandler customizeHandler : customizeHandlers) {
            if (customizeHandler.equalsTopic(topic)) {
                Object message = customizeHandler.onMessage(topic, data);
                if (message != null) {
                    client.send(JSON.toJSONString(message));
                }
            }
        }
    }

    public static void onCancel(WebSocketClient client, String topic, String data) {
        List<WebSocketEventHandler> webSocketEventHandlers = handlers.get(topic);
        if (webSocketEventHandlers != null && !webSocketEventHandlers.isEmpty()) {
            for (WebSocketEventHandler handler : webSocketEventHandlers) {
                Object message = handler.onCancel(topic, data);
                if (message != null) {
                    client.send(JSON.toJSONString(message));
                }
            }
        }
        for (WebSocketCustomizeEventHandler customizeHandler : customizeHandlers) {
            if (customizeHandler.equalsTopic(topic)) {
                Object message = customizeHandler.onCancel(topic, data);
                if (message != null) {
                    client.send(JSON.toJSONString(message));
                }
            }
        }
    }
}
