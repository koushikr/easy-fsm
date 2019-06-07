/*
 * Copyright 2016 Koushik R <rkoushik.14@gmail.com>.
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

import com.google.common.collect.Sets;
import io.github.fsm.models.entities.State;
import io.github.fsm.services.StateManagementService;

import java.util.Collection;
import java.util.Set;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 */
public class StateManagementServiceImpl implements StateManagementService {

    private Set<State> endStates;
    private State from;

    public StateManagementServiceImpl(){
        endStates = Sets.newHashSet();
    }

    @Override
    public State getFrom() {
        return from;
    }

    @Override
    public void addEndStates(Collection<State> endStates) {
        this.endStates.addAll(endStates);
    }

    @Override
    public void setFrom(State state) {
        this.from = state;
    }

    @Override
    public Set<State> getEndStates() {
        return endStates;
    }

    @Override
    public Set<State> getReferenceStates() {
        Set<State> states = Sets.newHashSet(this.from);
        states.addAll(this.endStates);
        return states;
    }
}
