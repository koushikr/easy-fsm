package com.grookage.fsm.services.impl;

import com.grookage.fsm.core.entities.*;
import com.grookage.fsm.core.executors.Action;
import com.grookage.fsm.core.executors.ErrorAction;
import com.grookage.fsm.core.executors.EventAction;
import com.grookage.fsm.exceptions.RunningtimeException;
import com.grookage.fsm.services.ActionService;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 *
 */
public class ActionServiceImpl implements ActionService {

    private Map<HandlerType, Action> handlers;

    public ActionServiceImpl(){
        handlers = new HashMap();
    }

    /**
     * {@inheritDoc}
     * @param context
     * @param <C>
     * @throws Exception
     */
    @Override
    public <C extends Context> void anyTransition(EventAction<C> context) throws Exception {
        handlers.put(new HandlerType(EventType.ANY_STATE_TRANSITION, null, null), context);
    }

    /**
     * {@inheritDoc}
     * @param state
     * @param context
     * @param <C>
     * @throws Exception
     */
    @Override
    public <C extends Context> void beforeTransition(State state, EventAction<C> context) throws Exception {
        handlers.put(Objects.isNull(state) ?
                        new HandlerType(EventType.BEFORE_ANY_TRANSITION, null, state)
                        : new HandlerType(EventType.BEFORE_STATE_TRANSITION, null, state),
                context
        );
    }

    /**
     * {@inheritDoc}
     * @param state
     * @param context
     * @param <C>
     * @throws Exception
     */
    @Override
    public <C extends Context> void afterTransition(State state, EventAction<C> context) throws Exception {
        handlers.put(Objects.isNull(state) ?
                        new HandlerType(EventType.AFTER_ANY_TRANSITION, null, state)
                        : new HandlerType(EventType.AFTER_STATE_TRANSITION, null, state),
                context
        );
    }

    /**
     * {@inheritDoc}
     * @param event
     * @param state
     * @param context
     * @param <C>
     * @throws Exception
     */
    @Override
    public <C extends Context> void forTransition(Event event, State state, EventAction<C> context) throws Exception {
        handlers.put(new HandlerType(EventType.STATE_TRANSITION, event, state), context);
    }

    /**
     * {@inheritDoc}
     * @param state
     * @param context
     * @param <C>
     * @throws Exception
     */
    @Override
    public <C extends Context> void onFinalState(State state, EventAction<C> context) throws Exception {
        handlers.put(new HandlerType(EventType.FINAL_STATE, null, state), context);
    }

    /**
     * {@inheritDoc}
     * @param event
     * @param from
     * @param to
     * @param context
     * @param <C>
     * @throws Exception
     */
    @Override
    public <C extends Context> void handleTransition(Event event, State from, State to, C context) throws Exception {
        Action h = handlers.get(new HandlerType(EventType.STATE_TRANSITION, null, to));
        if(!Objects.isNull(h)) ((EventAction<C>) h).call(context);

        h = handlers.get(new HandlerType(EventType.STATE_TRANSITION, event, to));
        if(!Objects.isNull(h)) ((EventAction<C>) h).call(context);

        h = handlers.get(new HandlerType(EventType.ANY_STATE_TRANSITION, null, null));
        if(!Objects.isNull(h)) ((EventAction<C>) h).call(context);
    }

    /**
     * {@inheritDoc}
     * @param event
     * @param from
     * @param to
     * @param context
     * @param <C>
     * @throws Exception
     */
    @Override
    public <C extends Context> void handleLanding(Event event, State from, State to, C context) throws Exception {
        Action h = handlers.get(new HandlerType(EventType.AFTER_STATE_TRANSITION, null, from));
        if(!Objects.isNull(h)) ((EventAction<C>) h).call(context);

        h = handlers.get(new HandlerType(EventType.AFTER_ANY_TRANSITION, null, null));
        if(!Objects.isNull(h)) ((EventAction<C>) h).call(context);
    }

    /**
     * {@inheritDoc}
     * @param event
     * @param from
     * @param to
     * @param context
     * @param initState
     * @param <C>
     * @throws Exception
     */
    @Override
    public <C extends Context> void handleTakeOff(Event event, State from, State to, C context, State initState) throws Exception {
        Action h = handlers.get(new HandlerType(EventType.BEFORE_STATE_TRANSITION, null, to));
        if(!Objects.isNull(h)) ((EventAction<C>) h).call(context);

        h = handlers.get(new HandlerType(EventType.BEFORE_ANY_TRANSITION, null, null));
        if(!Objects.isNull(h)) ((EventAction<C>) h).call(context);
    }

    /**
     * {@inheritDoc}
     * @param error
     */
    @Override
    public void handleError(RunningtimeException error) {
        Action h = handlers.get(new HandlerType(EventType.ERROR, null, null));
        if(!Objects.isNull(h)) ((ErrorAction) h).call(error, error.getContext());
    }

    /**
     * {@inheritDoc}
     * @param eventType
     * @param state
     * @param event
     * @param action
     */
    @Override
    public void setHandler(EventType eventType, State state, Event event, Action action) {
        handlers.put(new HandlerType(eventType, event, state), action);
    }
}
