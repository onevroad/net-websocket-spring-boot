package org.net.websocket.samples.handler;

import org.net.websocket.annotation.OnCancel;
import org.net.websocket.annotation.OnMessage;
import org.net.websocket.annotation.OnSubscribe;
import org.net.websocket.annotation.WebSocketListener;
import org.net.websocket.core.annotation.RequestData;
import org.net.websocket.core.annotation.RequestTopic;

@WebSocketListener("test-annotation")
public class SampleMessageAnnotationEventHandler {

    @OnSubscribe("test-annotation-subscribe")
    public String onSubscribe(@RequestTopic String topic, @RequestData String data) {
        return "subscribe success!";
    }

    @OnMessage
    public String onMessage(@RequestTopic String topic, @RequestData String data) {
        return "message received!";
    }

    @OnCancel("test-annotation-cancel")
    public String onCancel(@RequestTopic String topic, @RequestData String data) {
        return "cancel success!";
    }
}
