package com.github.koushikr.corefsm;

import com.github.koushikr.models.entities.State;
import com.github.koushikr.models.entities.Transition;
import com.github.koushikr.services.ActionService;
import com.github.koushikr.services.StateManagementService;
import com.github.koushikr.services.impl.ActionServiceImpl;
import com.github.koushikr.models.entities.Context;
import com.github.koushikr.models.entities.Event;
import com.github.koushikr.services.TransitionService;
import com.github.koushikr.services.impl.StateManagementServiceImpl;
import com.github.koushikr.services.impl.TransitionServiceImpl;

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
        fromStates.stream().forEach(state -> addTransition(event, state, to));
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
