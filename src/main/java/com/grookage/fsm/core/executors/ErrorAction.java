package com.grookage.fsm.core.executors;

import com.grookage.fsm.core.entities.Context;
import com.grookage.fsm.exceptions.RunningtimeException;

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
