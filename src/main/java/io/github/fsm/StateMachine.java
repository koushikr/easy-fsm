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

import com.google.common.collect.Multimap;
import io.github.fsm.exceptions.InvalidStateException;
import io.github.fsm.exceptions.RunningtimeException;
import io.github.fsm.exceptions.StateNotFoundException;
import io.github.fsm.models.entities.*;
import io.github.fsm.models.executors.ErrorAction;
import io.github.fsm.models.executors.EventAction;
import io.github.fsm.services.ActionService;
import io.github.fsm.services.StateManagementService;
import io.github.fsm.services.TransitionService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 */
@Slf4j
public class StateMachine<C extends Context> {

    public class DefaultErrorAction implements ErrorAction<Context> {
        @Override
        public void call(RunningtimeException error, Context context) {
            String errorMessage = "Runtime Error in state [" + error.getState() + "]";
            if(!Objects.isNull(error.getEvent())) errorMessage += "on Event [" + error.getEvent() + "]";
            errorMessage += "with context [" + error.getContext() + "]";
            log.error("ERROR", new Exception(errorMessage, error));
        }
    }

    private TransitionService transitionService;
    private StateManagementService stateManagementService;
    private ActionService actionService;

    public StateMachine(State startState, TransitionService transitionService, StateManagementService stateManagementService, ActionService actionService){
        this.transitionService = transitionService;
        this.stateManagementService = stateManagementService;
        this.actionService = actionService;
        this.stateManagementService.setFrom(startState);
        this.actionService.setHandler(EventType.ERROR, null, null, new DefaultErrorAction());
    }

    public void addTransition(State key , Transition transition){
        transitionService.addTransition(key, transition);
    }

    public void addEndStates(Collection<State> endStates){
        stateManagementService.addEndStates(endStates);
    }

    public <C extends Context> StateMachine<C> addError(ErrorAction<C> errorHandler) throws Exception{
        this.actionService.setHandler(EventType.ERROR, null, null, errorHandler);
        return (StateMachine<C>)  this;
    }

    public <C extends Context> StateMachine<C> beforeTransition(EventAction<C> before) throws Exception{
        actionService.beforeTransition(null, before);
        return (StateMachine<C>)  this;
    }

    public <C extends Context> StateMachine<C> afterTransition(EventAction<C> after) throws Exception{
        actionService.afterTransition(null, after);
        return (StateMachine<C>) this;
    }

    public <C extends Context> StateMachine<C> beforeTransitionTo(State state, EventAction<C> before) throws Exception{
        actionService.beforeTransition(state, before);
        return (StateMachine<C>) this;
    }

    public <C extends Context> StateMachine<C> afterTransitionFrom(State state, EventAction<C> after) throws Exception{
        actionService.afterTransition(state, after);
        return (StateMachine<C>) this;
    }

    public <C extends Context> StateMachine<C> anyTransition(EventAction<C> transition) throws Exception {
        actionService.anyTransition(transition);
        return (StateMachine<C>) this;
    }

    public <C extends Context> StateMachine<C> forTransition(State state, EventAction<C> context) throws Exception{
        actionService.forTransition(null, state, context);
        return (StateMachine<C>) this;
    }

    public <C extends Context> StateMachine<C> forTransition(Event event, State state, EventAction<C> context) throws Exception{
        actionService.forTransition(event, state, context);
        return (StateMachine<C>) this;
    }

    public <C extends Context> StateMachine<C> onFinalState(State state, EventAction<C> context) throws Exception{
        actionService.onFinalState(state, context);
        return (StateMachine<C>) this;
    }

    private void handleStateTransition(Event event, State from, State to, C context) throws Exception{
        actionService.handleTransition(event, from, to, context);
    }

    private void handleLanding(Event event, State from, State to, C context) throws Exception {
        actionService.handleLanding(event, from, to, context);
    }

    private void handleTakeOff(Event event, State from, State to, C context) throws Exception {
        actionService.handleTakeOff(event, from, to, context, stateManagementService.getFrom());
    }

    public Optional<Transition> getTransition(State from, Event event) throws Exception{
        return transitionService.getTransition(from, event);
    }

    public void handleError(RunningtimeException error){
        actionService.handleError(error);
    }

    public void fire(final Event event, final C context) throws Exception {
        final State from = context.getFrom();
        final Optional<Transition> transition = getTransition(from, event);
        if(!transition.isPresent()) throw new StateNotFoundException("Invalid Event: " + event + " triggered while in State: " + context.getFrom() + " for " + context);
        try{
            State to = transition.get().getTo();
            handleTakeOff(event, from, to, context);
            handleStateTransition(event, from, to, context);
            handleLanding(event, from, to, context);
        }catch (Exception e){
            handleError(new RunningtimeException(from, event, e, e.getMessage(), context));
        }
    }

    /**
     * A stateMachine is said to be valid iff it meets the following conditions
     * <ul>
     *     <li>It should have a valid start state and a nonempty set of end states. It has to be halting</li>
     *     <li>For all the states defined, make sure there are transitions from each one of 'em except for the end state</li>
     *     <li>Make sure there are no transitions defined from the endstate</li>
     * </ul>
     * @throws InvalidStateException
     */
    public void validate() throws InvalidStateException{
        if(Objects.isNull(stateManagementService.getFrom())) throw new InvalidStateException("No start state found");
        if(stateManagementService.getEndStates().isEmpty()) throw new InvalidStateException("No end states found");

        Set<State> allStates = stateManagementService.getReferenceStates();
        Multimap<State, Transition> map = transitionService.getTransitionDetails();
        map.keySet().forEach(state -> {
            allStates.add(state);
            map.get(state).forEach(transition -> {
                allStates.add(transition.getFrom());
                allStates.add(transition.getTo());
            });
        });

        for(State state: allStates){
            Set<Transition> transitions = (Set<Transition>) map.get(state);
            if(isNullOrEmpty(transitions)){
                if(!stateManagementService.getEndStates().contains(state)){
                    throw new InvalidStateException("state :"+ state +" is not an end state but"
                            + " has no outgoing transitions");
                }
                if(stateManagementService.getEndStates().contains(state)){
                    if(!transitions.isEmpty()){
                        throw new InvalidStateException("state :"+ state +" is an end state"
                                + " and cannot have any out going transition");
                    }
                }
            }
        }
    }

    private boolean isNullOrEmpty(Collection<?> clx){
        return Objects.isNull(clx) || clx.isEmpty();
    }
}