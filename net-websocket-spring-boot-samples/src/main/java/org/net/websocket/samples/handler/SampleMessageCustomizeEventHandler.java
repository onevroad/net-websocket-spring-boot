package org.net.websocket.samples.handler;

import lombok.extern.slf4j.Slf4j;
import org.net.websocket.annotation.WebSocketListener;
import org.net.websocket.core.WebSocketCustomizeEventHandler;
import org.springframework.beans.factory.annotation.Value;

@Slf4j
@WebSocketListener
public class SampleMessageCustomizeEventHandler implements WebSocketCustomizeEventHandler<String> {

    @Value("${test.topic}")
    private String topic;

    @Override
    public boolean equalsTopic(String topic) {
        return this.topic.equals(topic);
    }

    @Override
    public String onSubscribe(String topic, String data) {
        log.info("customize subscribe topic: {}, data: {}", topic, data);
        return "subscribe success!";
    }

    @Override
    public String onMessage(String topic, String data) {
        log.info("customize message topic: {}, data: {}", topic, data);
        return "message received!";
    }

    @Override
    public String onCancel(String topic, String data) {
        log.info("customize cancel topic: {}, data: {}", topic, data);
        return "cancel success!";
    }
}
