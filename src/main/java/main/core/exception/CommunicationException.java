package main.core.exception;

import java.io.IOException;

/**
 * Created by martijn on 3-6-2016.
 */
public class CommunicationException extends IOException {

    public CommunicationException(String message) {
        super(message);
    }
}
