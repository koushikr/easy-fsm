package com.github.koushikr.fsm.services.impl;

import com.github.koushikr.fsm.models.entities.Event;
import com.github.koushikr.fsm.models.entities.State;
import com.github.koushikr.fsm.models.entities.Transition;
import com.github.koushikr.fsm.services.TransitionService;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import lombok.AllArgsConstructor;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 */
public class TransitionServiceImpl implements TransitionService {

    private Multimap<State, Transition> transitionDetails;

    @AllArgsConstructor
    private static final class TransitionPredicate implements Predicate<Transition> {
        private Event event;

        @Override
        public boolean test(Transition transition) {
            return transition.getEvent().equals(event);
        }
    }

    public TransitionServiceImpl() { transitionDetails = HashMultimap.create(); }

    @Override
    public void addTransition(State state, Transition transition) {
        transitionDetails.put(state, transition);
    }

    @Override
    public Optional<Transition> getTransition(State from, Event event) throws Exception {
        return transitionDetails.get(from).stream().filter(new TransitionPredicate(event)).findFirst();
    }

    @Override
    public Multimap<State, Transition> getTransitionDetails() {
        return transitionDetails;
    }
}
