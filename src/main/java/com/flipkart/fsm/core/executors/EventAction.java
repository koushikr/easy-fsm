package com.flipkart.fsm.core.executors;

import com.flipkart.fsm.core.entities.Context;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 *
 * <p>
 *      Denotes EventAction. Gets Invoked whenever an action gets triggered.
 * </p>
 */
public interface EventAction<C extends Context> extends Action {

    void call(C context) throws Exception;

}
