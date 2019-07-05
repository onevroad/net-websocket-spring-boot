package org.net.websocket.core;


public interface WebSocketCustomizeEventHandler {

    boolean equalsTopic(String topic);

    String onSubscribe(String topic, String data);

    String onMessage(String topic, String data);

    String onCancel(String topic, String data);
}
