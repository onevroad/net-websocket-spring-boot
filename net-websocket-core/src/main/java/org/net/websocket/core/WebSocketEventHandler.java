package org.net.websocket.core;

public interface WebSocketEventHandler<T>  {

    T onSubscribe(String topic, String data);

    T onMessage(String topic, String data);

    T onCancel(String topic, String data);
}
