package org.net.websocket.annotation;

import org.net.websocket.autoconfigure.WebSocketScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import({WebSocketScannerRegistrar.class})
public @interface WebSocketScan {

    String[] basePackages() default {};
}
