package com.grookage.fsm.services;

import com.grookage.fsm.core.entities.State;

import java.util.Collection;
import java.util.Set;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 *
 * <p>An interface for state management</p>
 */
public interface StateManagementService {

    State getFrom();

    void addEndStates(Collection<State> endStates);

    void setFrom(State state);

    Set<State> getEndStates();

    Set<State> getReferenceStates();

}
