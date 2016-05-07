package com.github.koushikr.models.entities;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 *
 * <p>
 *     State is an abstraction for the FSM states. The FSM implementaiton should be
 *     implementing this State interface in their enums for binding
 * </p>
 */

public interface State {

    String name();

}
