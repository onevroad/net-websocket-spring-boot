package org.net.websocket.core;


import java.util.Iterator;
import java.util.List;

public class WebSocketRetryCommand implements Runnable {

    @Override
    public void run() {
        List<RetryMessage> retryMessages = RetryQueue.getAll();
        if (!retryMessages.isEmpty()) {
            Iterator<RetryMessage> iterator = retryMessages.iterator();
            while (iterator.hasNext()) {
                RetryMessage retryMessage = iterator.next();
                if (WebSocketRetryService.isContinueRetry(retryMessage.getRetryTime())) {
                    if (WebSocketRetryService.isRetryTime(retryMessage.getLastSendTime())) {
                        iterator.remove();
                        retryMessage.retrySend();
                    }
                } else {
                    iterator.remove();
                }
            }
        }
    }
}
