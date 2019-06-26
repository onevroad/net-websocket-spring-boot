package org.net.websocket.core;


import static org.net.websocket.core.Constants.DATA;
import static org.net.websocket.core.Constants.EVENT;
import static org.net.websocket.core.Constants.TOPIC;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketRequestDecoder {

    public WebSocketRequest decode(ChannelHandlerContext ctx, String message) {
        try {

            WebSocketRequest request = new WebSocketRequest();
            JSONObject input = JSON.parseObject(message);

            request.setContext(ctx);

            if (input.containsKey(EVENT)) {
                String event = input.getString(EVENT);
                request.setEvent(event);
            }

            if (input.containsKey(TOPIC)) {
                String[] topic = input.getObject(TOPIC, String[].class);
                request.setTopic(topic);
            }

            if (input.containsKey(DATA)) {
                String data = input.getString(DATA);
                request.setData(data);
            }

            return request;
        } catch (Exception e) {
            log.error("WebSocketRequest decode exception!", e);
            return null;
        }
    }
}
