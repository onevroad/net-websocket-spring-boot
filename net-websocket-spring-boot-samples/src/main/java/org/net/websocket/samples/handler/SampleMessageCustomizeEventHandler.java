package org.net.websocket.samples.handler;

import org.net.websocket.annotation.WebSocketListener;
import org.net.websocket.core.WebSocketCustomizeEventHandler;

@WebSocketListener
public class SampleMessageCustomizeEventHandler implements WebSocketCustomizeEventHandler<String> {
    @Override
    public boolean equalsTopic(String topic) {
        return "test2".equals(topic);
    }

    @Override
    public String onSubscribe(String topic, String data) {
        return "subscribe success!";
    }

    @Override
    public String onMessage(String topic, String data) {
        return "message received!";
    }

    @Override
    public String onCancel(String topic, String data) {
        return "cancel success!";
    }
}
