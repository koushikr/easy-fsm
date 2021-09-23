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
package io.github.fsm;

import io.github.fsm.models.entities.Context;
import io.github.fsm.models.entities.Event;
import io.github.fsm.models.entities.State;
import io.github.fsm.models.entities.Transition;
import io.github.fsm.models.executors.ErrorAction;
import io.github.fsm.services.ActionService;
import io.github.fsm.services.StateManagementService;
import io.github.fsm.services.TransitionService;
import io.github.fsm.services.impl.ActionServiceImpl;
import io.github.fsm.services.impl.StateManagementServiceImpl;
import io.github.fsm.services.impl.TransitionServiceImpl;

import java.util.Collection;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 */
public class MachineBuilder<C extends Context> {

    private StateMachine<C> stateMachine;

    private void addTransition(Event event, State from, State to){
        stateMachine.addTransition(from, new Transition(event, from, to));
    }

    protected MachineBuilder(State startState, TransitionService transitionService, StateManagementService stateManagementService, ActionService actionService){
        stateMachine = new StateMachine<C>(startState, transitionService, stateManagementService, actionService);
    }

    public static <C extends Context> MachineBuilder<C> start(State startState){
        return new MachineBuilder<C>(startState, new TransitionServiceImpl(), new StateManagementServiceImpl(), new ActionServiceImpl());
    }

    public <C extends Context> MachineBuilder<C> onTransition(Event event, Collection<State> fromStates, State to){
        fromStates.forEach(state -> addTransition(event, state, to));
        return (MachineBuilder<C>) this;
    }

    public <C extends Context> MachineBuilder<C> onError(ErrorAction<C> eventAction) throws Exception {
        stateMachine.addError(eventAction);
        return (MachineBuilder<C>) this;
    }

    public <C extends Context> MachineBuilder<C> onTransition(Event event, State from, State to){
        addTransition(event, from, to);
        return (MachineBuilder<C>) this;
    }

    public StateMachine<C> end(Collection<State> endStates){
        stateMachine.addEndStates(endStates);
        return stateMachine;
    }
}
