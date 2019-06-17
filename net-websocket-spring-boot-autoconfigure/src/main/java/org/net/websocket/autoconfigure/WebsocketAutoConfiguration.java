package org.net.websocket.autoconfigure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(WebsocketProperties.class)
@ComponentScan(basePackages = {"org.net.websocket.autoconfigure"})
public class WebsocketAutoConfiguration {

    @Autowired
    private WebsocketProperties properties;

    @Bean(initMethod = "init")
    @ConditionalOnMissingBean
    public WebSocketServerInitialization initialization() {
        return new WebSocketServerInitialization(properties);
    }
}
