package org.mywebsocket.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author ZhangYunxiang
 * date 2019/6/10
 */
@Configuration
@EnableConfigurationProperties(WebsocketProperties.class)
public class WebsocketAutoConfiguration {

    @Autowired
    private WebsocketProperties properties;


}
