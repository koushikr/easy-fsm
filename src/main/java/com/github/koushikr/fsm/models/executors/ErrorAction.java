package com.github.koushikr.fsm.models.executors;

import com.github.koushikr.fsm.exceptions.RunningtimeException;
import com.github.koushikr.fsm.models.entities.Context;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 *
 * <p>
 *     Denotes ErrorAction. Gets Invoked whenever there is an exception thrown
 * </p>
 */
public interface ErrorAction<C extends Context> extends Action {

    void call(RunningtimeException error, C context);

}
