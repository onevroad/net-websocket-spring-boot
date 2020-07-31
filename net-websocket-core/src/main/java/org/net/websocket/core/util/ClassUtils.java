package org.net.websocket.core.util;

public class ClassUtils {

    public static Class<?> getUserClass(Object instance) {
        Assert.notNull(instance, "Instance must not be null");
        return getUserClass(instance.getClass());
    }
}
