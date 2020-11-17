package org.net.websocket.core.retry;


import org.net.websocket.core.client.WebSocketClientService;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WebSocketNotFoundRetryCommand implements Runnable {

    @Override
    public void run() {
        List<NotFoundRetryMessage> notFoundRetryMessages = NotFoundRetryQueue.getAll();
        if (!notFoundRetryMessages.isEmpty()) {
            List<NotFoundRetryMessage> retryFailedMessages = new ArrayList<>();
            Iterator<NotFoundRetryMessage> iterator = notFoundRetryMessages.iterator();
            while (iterator.hasNext()) {
                NotFoundRetryMessage notFoundRetryMessage = iterator.next();
                if (WebSocketRetryService.isContinueRetry(notFoundRetryMessage.getRetryTime())) {
                    if (WebSocketRetryService.isRetryTime(notFoundRetryMessage.getLastSendTime())) {
                        iterator.remove();
                        notFoundRetryMessage.retry();
                        if (!WebSocketClientService.publishSync(notFoundRetryMessage.getTopic(), notFoundRetryMessage.getScope(), notFoundRetryMessage.getMessage())) {
                            retryFailedMessages.add(notFoundRetryMessage);
                        }
                    }
                } else {
                    iterator.remove();
                }
            }
            if (!retryFailedMessages.isEmpty()) {
                NotFoundRetryQueue.addAll(retryFailedMessages);
            }
        }
    }
}
