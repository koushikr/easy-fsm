package com.flipkart.fsm.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 *
 * <p>
 *     Denotes Transition from State from to State to when an Event event happens.
 * </p>
 */
@AllArgsConstructor @Getter
@Setter
public class Transition {

    private Event event;

    private State from;

    private State to;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (Objects.isNull(o) || getClass() != o.getClass()) return false;

        Transition that = (Transition) o;

        return event.equals(that.event) && from.equals(that.from);
    }

    @Override
    public int hashCode() {
        int result = event.hashCode();
        result = 31 * result + from.hashCode();
        return result;
    }

}
