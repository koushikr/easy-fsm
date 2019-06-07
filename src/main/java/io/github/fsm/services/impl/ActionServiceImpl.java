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
package io.github.fsm.services.impl;

import io.github.fsm.exceptions.RunningtimeException;
import io.github.fsm.models.entities.*;
import io.github.fsm.models.executors.Action;
import io.github.fsm.models.executors.ErrorAction;
import io.github.fsm.models.executors.EventAction;
import io.github.fsm.services.ActionService;

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
