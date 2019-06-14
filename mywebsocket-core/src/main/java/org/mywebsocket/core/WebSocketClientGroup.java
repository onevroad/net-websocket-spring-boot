package org.mywebsocket.core;

import java.util.concurrent.ConcurrentHashMap;

public class WebSocketClientGroup extends ConcurrentHashMap<String, WebSocketClientMap> {//topic:id,client

}
