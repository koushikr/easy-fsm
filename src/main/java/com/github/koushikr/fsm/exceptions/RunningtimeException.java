package com.github.koushikr.fsm.exceptions;

import com.github.koushikr.fsm.models.entities.State;
import com.github.koushikr.fsm.models.entities.Context;
import com.github.koushikr.fsm.models.entities.Event;
import lombok.Getter;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 */
@Getter
public class RunningtimeException extends Exception {
    private static final long serialVersionUID = 4362053831847081229L;
    private State state;
    private Event event;
    private Context context;

    public RunningtimeException(State state, Event event, Exception error, String message, Context context) {
        super(message, error);

        this.state = state;
        this.event = event;
        this.context = context;
    }

}
