package org.net.websocket.core;

import java.util.ArrayList;
import java.util.List;

public class ErrorRetryQueue {

    private static List<ErrorRetryMessage> queue = new ArrayList<>();

    public static void add(ErrorRetryMessage message) {
        queue.add(message);
    }

    public static List<ErrorRetryMessage> getAll() {
        return queue;
    }
}
