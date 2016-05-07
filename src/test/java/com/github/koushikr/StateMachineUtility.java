package com.github.koushikr;

import com.github.koushikr.models.entities.State;
import com.github.koushikr.models.entities.Context;
import com.github.koushikr.models.entities.Event;
import com.github.koushikr.corefsm.MachineBuilder;
import com.github.koushikr.corefsm.StateMachine;
import com.google.common.collect.Sets;

/**
 * Entity by : koushikr.
 * on 26/10/15.
 */
public class StateMachineUtility {

    public enum TestState implements State {
        STARTED, CREATED, IN_PROGRESS, COMPLETED, CANCELLED;
    }

    public enum TestEvent implements Event {
        CREATE, FULFILL, COMPLETED, CANCEL;
    }

    public static StateMachine<Context> getValidStateMachine(){
        return constructStateMachine();
    }

    public static StateMachine<Context> getInvalidStateMachine(){
        return MachineBuilder.start(TestState.STARTED)
                .onTransition(TestEvent.CREATE, TestState.STARTED, TestState.CREATED)
                .onTransition(TestEvent.COMPLETED, TestState.IN_PROGRESS, TestState.COMPLETED)
                .onTransition(TestEvent.CANCEL, Sets.newHashSet(TestState.STARTED, TestState.IN_PROGRESS), TestState.CANCELLED)
                .end(Sets.newHashSet(TestState.COMPLETED, TestState.CANCELLED));
    }

    private static StateMachine<Context> constructStateMachine(){
        return MachineBuilder.start(TestState.STARTED)
                .onTransition(TestEvent.CREATE, TestState.STARTED, TestState.CREATED)
                .onTransition(TestEvent.FULFILL, TestState.CREATED, TestState.IN_PROGRESS)
                .onTransition(TestEvent.COMPLETED, TestState.IN_PROGRESS, TestState.COMPLETED)
                .onTransition(TestEvent.CANCEL, Sets.newHashSet(TestState.STARTED, TestState.CREATED, TestState.IN_PROGRESS), TestState.CANCELLED)
                .end(Sets.newHashSet(TestState.COMPLETED, TestState.CANCELLED));
    }
}
