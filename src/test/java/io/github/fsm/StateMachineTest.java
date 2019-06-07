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
package io.github.fsm;

import io.github.fsm.exceptions.InvalidStateException;
import io.github.fsm.exceptions.StateNotFoundException;
import io.github.fsm.models.entities.Context;
import io.github.fsm.models.executors.EventAction;
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
