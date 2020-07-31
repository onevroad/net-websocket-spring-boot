package org.net.websocket.core.retry;


import java.util.Iterator;
import java.util.List;

public class WebSocketErrorRetryCommand implements Runnable {

    @Override
    public void run() {
        List<ErrorRetryMessage> errorRetryMessages = ErrorRetryQueue.getAll();
        if (!errorRetryMessages.isEmpty()) {
            Iterator<ErrorRetryMessage> iterator = errorRetryMessages.iterator();
            while (iterator.hasNext()) {
                ErrorRetryMessage errorRetryMessage = iterator.next();
                if (WebSocketRetryService.isContinueRetry(errorRetryMessage.getRetryTime())) {
                    if (WebSocketRetryService.isRetryTime(errorRetryMessage.getLastSendTime())) {
                        iterator.remove();
                        errorRetryMessage.retrySend();
                    }
                } else {
                    iterator.remove();
                }
            }
        }
    }
}
