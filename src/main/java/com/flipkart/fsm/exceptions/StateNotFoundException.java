package com.flipkart.fsm.exceptions;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 */
public class StateNotFoundException extends Exception {

    public StateNotFoundException(String errorMessage){
        super(errorMessage);
    }

}
