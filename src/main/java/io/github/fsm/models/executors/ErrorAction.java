package io.github.fsm.models.executors;

import io.github.fsm.exceptions.RunningtimeException;
import io.github.fsm.models.entities.Context;

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
