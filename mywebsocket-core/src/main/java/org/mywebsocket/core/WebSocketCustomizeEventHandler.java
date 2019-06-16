package org.mywebsocket.core;


public interface WebSocketCustomizeEventHandler {

    boolean equalsTopic(String topic);

    <T> T onSubscribe(String topic, String data);

    <T> T onMessage(String topic, String data);

    <T> T onCancel(String topic, String data);
}
