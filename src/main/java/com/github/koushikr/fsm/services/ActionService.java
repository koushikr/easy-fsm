package com.github.koushikr.fsm.services;

import com.github.koushikr.fsm.models.entities.EventType;
import com.github.koushikr.fsm.models.entities.State;
import com.github.koushikr.fsm.models.entities.Context;
import com.github.koushikr.fsm.models.entities.Event;
import com.github.koushikr.fsm.models.executors.Action;
import com.github.koushikr.fsm.models.executors.EventAction;
import com.github.koushikr.fsm.exceptions.RunningtimeException;
import com.github.koushikr.fsm.StateMachine;

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
