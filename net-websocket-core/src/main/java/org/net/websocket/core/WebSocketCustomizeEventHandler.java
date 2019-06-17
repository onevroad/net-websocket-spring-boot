package org.net.websocket.core;


public interface WebSocketCustomizeEventHandler<T> {

    boolean equalsTopic(String topic);

    T onSubscribe(String topic, String data);

    T onMessage(String topic, String data);

    T onCancel(String topic, String data);
}
