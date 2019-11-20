package org.net.websocket.core;

import java.util.ArrayList;
import java.util.List;

public class NotFoundRetryQueue {

    private static List<NotFoundRetryMessage> queue = new ArrayList<>();

    public static void add(NotFoundRetryMessage message) {
        queue.add(message);
    }

    public static void addAll(List<NotFoundRetryMessage> messages) {
        queue.addAll(messages);
    }

    public static List<NotFoundRetryMessage> getAll() {
        return queue;
    }
}
