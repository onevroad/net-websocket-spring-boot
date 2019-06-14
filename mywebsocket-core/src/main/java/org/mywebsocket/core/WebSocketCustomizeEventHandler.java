package org.mywebsocket.core;


public interface WebSocketCustomizeEventHandler {

    <T> T subscribe(String topic, String data);

    <T> T cancel(String topic, String data);
}
