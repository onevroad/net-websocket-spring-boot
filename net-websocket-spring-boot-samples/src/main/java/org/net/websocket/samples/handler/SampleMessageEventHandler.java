package org.net.websocket.samples.handler;

import lombok.extern.slf4j.Slf4j;
import org.net.websocket.annotation.MessageListener;
import org.net.websocket.core.WebSocketEventHandler;

@Slf4j
@MessageListener("test")
public class SampleMessageEventHandler implements WebSocketEventHandler<String> {
    @Override
    public String onSubscribe(String topic, String data) {
        log.info("subscribe topic: {}, data: {}", topic, data);
        return "subscribe success!";
    }

    @Override
    public String onMessage(String topic, String data) {
        log.info("message topic: {}, data: {}", topic, data);
        return "message received!";
    }

    @Override
    public String onCancel(String topic, String data) {
        log.info("cancel topic: {}, data: {}", topic, data);
        return "cancel success!";
    }
}
