package org.mywebsocket.core;

public interface WebSocketEventHandler {

    <T> T onSubscribe(String topic, String data);

    <T> T onMessage(String topic, String data);

    <T> T onCancel(String topic, String data);
}
