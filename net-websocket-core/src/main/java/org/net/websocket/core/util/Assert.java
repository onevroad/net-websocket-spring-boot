package org.net.websocket.core.util;

import javax.annotation.Nullable;

public class Assert {

    public static void notNull(@Nullable Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
