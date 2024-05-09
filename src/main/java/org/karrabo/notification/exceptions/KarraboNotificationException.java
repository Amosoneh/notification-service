package org.karrabo.notification.exceptions;

public class KarraboNotificationException  extends Exception{
    public KarraboNotificationException(String msg){
        super(msg);
    }

    public KarraboNotificationException(String msg, Throwable cause){
        super(msg, cause);
    }

    public KarraboNotificationException(Throwable cause){
        super(cause);
    }
}
