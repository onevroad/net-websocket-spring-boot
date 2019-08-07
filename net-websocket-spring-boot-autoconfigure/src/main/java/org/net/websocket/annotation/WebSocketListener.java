package org.net.websocket.annotation;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebSocketListener {

    /**
     * 订阅主题，topic
     */
    String value() default "";
}
