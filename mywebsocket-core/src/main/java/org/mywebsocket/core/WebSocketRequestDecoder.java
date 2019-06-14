package org.mywebsocket.core;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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

            if (input.containsKey("event")) {
                String event = input.getString("event");
                request.setEvent(event);
            }

            if (input.containsKey("topic")) {
                String[] topic = input.getObject("topic", String[].class);
                request.setTopic(topic);
            }

            if (input.containsKey("data")) {
                String data = input.getString("data");
                request.setData(data);
            }

            return request;
        } catch (Exception e) {
            log.error("webSocketRequest decode exception!", e);
            return null;
        }
    }
}
