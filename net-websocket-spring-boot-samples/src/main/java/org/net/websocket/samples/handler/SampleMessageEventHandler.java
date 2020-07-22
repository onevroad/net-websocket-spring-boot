package org.net.websocket.samples.handler;

import lombok.extern.slf4j.Slf4j;
import org.net.websocket.annotation.WebSocketListener;
import org.net.websocket.core.WebSocketEventHandler;
import org.net.websocket.samples.model.User;

@Slf4j
@WebSocketListener("test")
public class SampleMessageEventHandler implements WebSocketEventHandler<User, User> {
    @Override
    public User onSubscribe(String topic, User data) {
        log.info("subscribe topic: {}, data: {}", topic, data);
        User user = new User();
        user.setName("subscribe");
        return user;
    }

    @Override
    public User onMessage(String topic, User data) {
        log.info("message topic: {}, data: {}", topic, data);
        User user = new User();
        user.setName("message");
        return user;
    }

    @Override
    public User onCancel(String topic, User data) {
        log.info("cancel topic: {}, data: {}", topic, data);
        User user = new User();
        user.setName("cancel");
        return user;
    }
}
