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
package io.github.fsm.services;

import io.github.fsm.StateMachine;
import io.github.fsm.exceptions.RunningtimeException;
import io.github.fsm.models.entities.Context;
import io.github.fsm.models.entities.Event;
import io.github.fsm.models.entities.EventType;
import io.github.fsm.models.entities.State;
import io.github.fsm.models.executors.Action;
import io.github.fsm.models.executors.EventAction;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 *
 * <p>
 *     Denotes the Action Service to help for transitions and event handler bindings
 * </p>
 */
public interface ActionService {

    /**
     * <p>
     *     Trigger on anyTransition from any state to any and for any event to any
     * </p>
     * @param context
     * @param <C>
     * @throws Exception
     */
    <C extends Context> void anyTransition(EventAction<C> context) throws Exception;

    /**
     * <p>
     *     Trigger before any transition to the said state
     * </p>
     * @param state
     * @param context
     * @param <C>
     * @throws Exception
     */
    <C extends Context> void beforeTransition(State state, EventAction<C> context) throws Exception;

    /**
     * <p>
     *     Trigger after any transition to the said state
     * </p>
     * @param state
     * @param context
     * @param <C>
     * @throws Exception
     */
    <C extends Context> void afterTransition(State state, EventAction<C> context) throws Exception;

    /**
     * <p>
     *     Guard condition for a transition from the said state and on the said event
     * </p>
     * @param event
     * @param state
     * @param context
     * @param <C>
     * @throws Exception
     */
    <C extends Context> void forTransition(Event event, State state, EventAction<C> context) throws Exception;

    /**
     * <p>
     *     Trigger on reaching the final state
     * </p>
     * @param state
     * @param context
     * @param <C>
     * @throws Exception
     */
    <C extends Context> void onFinalState(State state, EventAction<C> context) throws Exception;

    /**
     * <p>
     *     Handle any transition between two states on a said event for the given context
     * </p>
     * @param event
     * @param from
     * @param to
     * @param context
     * @param <C>
     * @throws Exception
     */
    <C extends Context> void handleTransition(Event event, State from, State to, C context) throws Exception;

    /**
     * <p>
     *     Handle transitions when landing on a particular state. Would be used to implement
     *     BEFORE_TRANSITIONS
     * </p>
     * @param event
     * @param from
     * @param to
     * @param context
     * @param <C>
     * @throws Exception
     */
    <C extends Context> void handleLanding(Event event, State from, State to, C context) throws Exception;

    /**
     * <p>
     *     Handle transitions when taking off from a particular state. Would be used to implement
     *     AFTER_TRANSITIONS
     * </p>
     * @param event
     * @param from
     * @param to
     * @param context
     * @param initState
     * @param <C>
     * @throws Exception
     */
    <C extends Context> void handleTakeOff(Event event, State from, State to, C context, State initState) throws Exception;

    /**
     * <p>
     *     Bind ErrorHandler {@link StateMachine.DefaultErrorAction}
     * </p>
     * @param error
     */
    void handleError(RunningtimeException error);

    /**
     * <p>
     *     Used to explicitly set transitions
     * </p>
     * @param eventType
     * @param state
     * @param event
     * @param action
     */
    void setHandler(EventType eventType, State state, Event event, Action action);

}
