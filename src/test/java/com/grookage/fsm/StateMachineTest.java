package com.grookage.fsm;

import com.grookage.fsm.core.entities.Context;
import com.grookage.fsm.core.executors.EventAction;
import com.grookage.fsm.exceptions.InvalidStateException;
import com.grookage.fsm.exceptions.StateNotFoundException;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
/**
 * Entity by : koushikr.
 * on 26/10/15.
 */
public class StateMachineTest {

    @BeforeClass
    public void setUp() {
    }

    @Test
    public void testForValidStateMachine() throws InvalidStateException{
        StateMachine<Context> stateMachine = StateMachineUtility.getValidStateMachine();
        stateMachine.validate();
    }

    @Test(expectedExceptions = InvalidStateException.class)
    public void testForInvalidStateMachine() throws InvalidStateException{
        StateMachine<Context> stateMachine = StateMachineUtility.getInvalidStateMachine();
        stateMachine.validate();
    }

    @Test
    public void testAnyEvent() throws Exception{
        Context stateContext = new Context();
        stateContext.setFrom(StateMachineUtility.TestState.STARTED);
        stateContext.setTo(StateMachineUtility.TestState.CREATED);
        stateContext.setCausedEvent(StateMachineUtility.TestEvent.CREATE);
        StateMachine<Context> stateMachine = StateMachineUtility.getValidStateMachine();
        stateMachine.anyTransition(new EventAction<Context>() {
            @Override
            public void call(Context context) throws Exception {
                Assert.assertTrue(context.getFrom() == StateMachineUtility.TestState.STARTED);
            }
        });
        stateMachine.fire(StateMachineUtility.TestEvent.CREATE, stateContext);
    }

    @Test(expectedExceptions = StateNotFoundException.class)
    public void testInvalidTransitionOnAnyEvent() throws Exception{
        Context stateContext = new Context();
        stateContext.setFrom(StateMachineUtility.TestState.STARTED);
        stateContext.setTo(StateMachineUtility.TestState.CREATED);
        stateContext.setCausedEvent(StateMachineUtility.TestEvent.CREATE);
        StateMachine<Context> stateMachine = StateMachineUtility.getValidStateMachine();
        stateMachine.anyTransition(new EventAction<Context>() {
            @Override
            public void call(Context context) throws Exception {
                org.testng.Assert.assertTrue(context.getFrom() == StateMachineUtility.TestState.STARTED);
            }
        });
        stateMachine.fire(StateMachineUtility.TestEvent.FULFILL, stateContext);
    }

}
