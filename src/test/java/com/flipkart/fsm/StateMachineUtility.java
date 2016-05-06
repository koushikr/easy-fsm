package com.flipkart.fsm;

import com.flipkart.fsm.core.entities.Context;
import com.flipkart.fsm.core.entities.Event;
import com.flipkart.fsm.core.entities.State;
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
