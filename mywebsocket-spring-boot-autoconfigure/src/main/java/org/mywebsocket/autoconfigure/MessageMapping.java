package org.mywebsocket.autoconfigure;

import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MessageMapping {

    String value();
}
