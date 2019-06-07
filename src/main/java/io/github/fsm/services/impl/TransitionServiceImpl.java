/*
 * Copyright 2015 Koushik R <rkoushik.14@gmail.com>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.fsm.services.impl;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import io.github.fsm.models.entities.Event;
import io.github.fsm.models.entities.State;
import io.github.fsm.models.entities.Transition;
import io.github.fsm.services.TransitionService;
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
