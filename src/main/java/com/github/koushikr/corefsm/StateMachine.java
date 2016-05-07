package com.github.koushikr.corefsm;

import com.github.koushikr.models.entities.*;
import com.github.koushikr.models.executors.ErrorAction;
import com.github.koushikr.models.executors.EventAction;
import com.github.koushikr.exceptions.InvalidStateException;
import com.github.koushikr.exceptions.RunningtimeException;
import com.github.koushikr.exceptions.StateNotFoundException;
import com.github.koushikr.services.ActionService;
import com.github.koushikr.services.StateManagementService;
import com.github.koushikr.services.TransitionService;
import com.google.common.collect.Multimap;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 *
 * <p>
 *     This statemachine is inspired from EasyFlow {@rel https://github.com/Beh01der/EasyFlow}
 *     While the models idea is the same (Duh! there is only one way to implement a state machine),
 *     good load of changes have been made to the abstractions and event transitions.
 *
 *     Couldn't use it as it is, because of the volume of changes(constructs and abstractions)
 *     required for my usage
 * </p>
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
        State to = transition.get().getTo();
        handleTakeOff(event, from, to, context);
        handleStateTransition(event, from, to, context);
        handleLanding(event, from, to, context);
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
        map.keySet().stream().forEach(state -> {
            allStates.add(state);
            map.get(state).stream().forEach(transition -> {
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