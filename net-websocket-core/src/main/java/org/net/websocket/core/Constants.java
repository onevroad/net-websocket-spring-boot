package org.net.websocket.core;

public class Constants {
  public static final String EVENT = "e";
  public static final String TOPIC = "t";
  public static final String DATA = "d";

  public static final String SUBSCRIBE = "subscribe";
  public static final String MESSAGE = "message";
  public static final String CANCEL = "cancel";
  public static final String HEARTBEAT = "heartbeat";

  public static final String HEARTBEAT_TEXT = "{\"e\":\"heartbeat\",\"d\":\"ping\"}";

  public static final String TOPIC_ALL = "all";
}
