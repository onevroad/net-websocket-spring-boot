package org.net.websocket.annotation;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MessageListener {

    String value() default "";
}
