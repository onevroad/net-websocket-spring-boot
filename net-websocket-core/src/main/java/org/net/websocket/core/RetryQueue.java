package org.net.websocket.core;

import java.util.ArrayList;
import java.util.List;

public class RetryQueue {

    private static List<RetryMessage> queue = new ArrayList<>();

    public static void add(RetryMessage message) {
        queue.add(message);
    }

    public static List<RetryMessage> getAll() {
        return queue;
    }
}
