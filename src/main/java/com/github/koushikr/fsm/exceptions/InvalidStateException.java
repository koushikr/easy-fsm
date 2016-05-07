package com.github.koushikr.fsm.exceptions;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 *
 */
public class InvalidStateException extends Exception {

    public InvalidStateException(String errorMessage){
        super(errorMessage);
    }

}
