package org.net.websocket.core;

public interface WebSocketEventHandler  {

    String onSubscribe(String topic, String data);

    String onMessage(String topic, String data);

    String onCancel(String topic, String data);
}
