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

import com.google.common.collect.Sets;
import io.github.fsm.models.entities.Context;
import io.github.fsm.models.entities.Event;
import io.github.fsm.models.entities.State;

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
