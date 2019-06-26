package org.net.websocket.core;

public class Constants {
  public static final String EVENT = "event";
  public static final String TOPIC = "topic";
  public static final String DATA = "data";

  public static final String SUBSCRIBE = "subscribe";
  public static final String MESSAGE = "message";
  public static final String CANCEL = "cancel";
  public static final String HEARTBEAT = "heartbeat";

  public static final String HEARTBEAT_TEXT = "{\"event\":\"heartbeat\",\"data\":\"ping\"}";

  public static final String TOPIC_ALL = "all";
}
