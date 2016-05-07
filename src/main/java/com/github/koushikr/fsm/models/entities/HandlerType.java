package com.github.koushikr.fsm.models.entities;

import com.github.koushikr.fsm.models.executors.EventAction;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 *
 * <p>
 *     HandlerType is the type of {EventType, Event, State} tuple against which transition transitions
 *     will be saved. The same request mapping is later used to fetch the appropriate handler
 *     {@link EventAction} for execution
 * </p>
 */
public class HandlerType {

    EventType eventType;
    Event event;
    State state;

    public HandlerType(EventType eventType, Event event, State state) {
        this.eventType = eventType;
        this.event = event;
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HandlerType that = (HandlerType) o;

        if (event != null ? !event.equals(that.event) : that.event != null) return false;
        if (eventType != that.eventType) return false;
        if (state != null ? !state.equals(that.state) : that.state != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = eventType.hashCode();
        result = 31 * result + (event != null ? event.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        return result;
    }

}
