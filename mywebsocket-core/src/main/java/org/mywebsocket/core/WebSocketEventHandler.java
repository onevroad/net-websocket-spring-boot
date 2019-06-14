package org.mywebsocket.core;

public interface WebSocketEventHandler {

    <T> T subscribe(String data);

    <T> T cancel(String data);
}
