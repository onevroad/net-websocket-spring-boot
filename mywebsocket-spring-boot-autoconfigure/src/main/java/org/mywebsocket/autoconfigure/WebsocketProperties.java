package org.mywebsocket.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author ZhangYunxiang
 * date 2019/6/10
 */
@Data
@ConfigurationProperties(prefix = "spring.websocket")
public class WebsocketProperties {

    private int port;

    private String endPoint;
}
