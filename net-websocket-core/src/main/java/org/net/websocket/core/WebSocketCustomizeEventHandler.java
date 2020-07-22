package org.net.websocket.core;


public interface WebSocketCustomizeEventHandler<Request, Response> {

    boolean equalsTopic(String topic);

    Response onSubscribe(String topic, Request data);

    Response onMessage(String topic, Request data);

    Response onCancel(String topic, Request data);
}
