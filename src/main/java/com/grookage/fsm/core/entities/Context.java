package com.grookage.fsm.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 *
 * <p>
 *     Denotes the Context in which the StateMachine will function
 *     Keeps metadeta about the from and to states along with the causedEvent
 * </p>
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Context implements Serializable {

    private static final long serialVersionUID = 42L;

    private State from;
    private State to;
    private Event causedEvent;

}
