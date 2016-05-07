package com.github.koushikr.fsm.services;

import com.github.koushikr.fsm.models.entities.Event;
import com.github.koushikr.fsm.models.entities.State;
import com.github.koushikr.fsm.models.entities.Transition;
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
