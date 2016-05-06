package com.flipkart.fsm.services;

import com.flipkart.fsm.core.entities.Event;
import com.flipkart.fsm.core.entities.State;
import com.flipkart.fsm.core.entities.Transition;
import com.google.common.collect.Multimap;

import java.util.Optional;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 */
public interface TransitionService {

    void addTransition(State state, Transition transition);

    Optional<Transition> getTransition(State from, Event event) throws Exception;

    Multimap<State, Transition> getTransitionDetails();

}
