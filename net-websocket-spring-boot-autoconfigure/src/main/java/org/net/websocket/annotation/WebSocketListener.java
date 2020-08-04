package org.net.websocket.annotation;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WebSocketListener {

    @AliasFor("topic")
    String value() default "";

    /**
     * 订阅主题，topic
     */
    @AliasFor("value")
    String topic() default "";
}
