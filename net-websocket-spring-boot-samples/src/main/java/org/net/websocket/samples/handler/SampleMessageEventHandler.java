package org.net.websocket.samples.handler;

import lombok.extern.slf4j.Slf4j;
import org.net.websocket.annotation.WebSocketListener;
import org.net.websocket.core.WebSocketEventHandler;
import org.net.websocket.samples.model.Demo;

@Slf4j
@WebSocketListener("test")
public class SampleMessageEventHandler implements WebSocketEventHandler<Demo, Demo> {
    @Override
    public Demo onSubscribe(String topic, Demo data) {
        log.info("subscribe topic: {}, data: {}", topic, data);
        Demo demo = new Demo();
        demo.setName("subscribe");
        return demo;
    }

    @Override
    public Demo onMessage(String topic, Demo data) {
        log.info("message topic: {}, data: {}", topic, data);
        Demo demo = new Demo();
        demo.setName("message");
        return demo;
    }

    @Override
    public Demo onCancel(String topic, Demo data) {
        log.info("cancel topic: {}, data: {}", topic, data);
        Demo demo = new Demo();
        demo.setName("cancel");
        return demo;
    }
}
