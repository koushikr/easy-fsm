package com.grookage.fsm.core.entities;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 *
 * <p>
 *     The type of events possible for the state machine.
 * </p>
 */
public enum EventType {

    /**
     * <p>
     *     Gets Triggered before any transition is triggered.
     *     Default : void return
     * </p>
     */
    BEFORE_ANY_TRANSITION,

    /**
     * <p>
     *     Gets Triggered after any transition is completed
     *     Default : void return
     * </p>
     */
    AFTER_ANY_TRANSITION,

    /**
     * <p>
     *     Gets triggered before a particular state transition
     *     Default : void return
     * </p>
     */
    BEFORE_STATE_TRANSITION,

    /**
     * <p>
     *     Gets triggered after a particular state transition
     *     Default : void return
     * </p>
     */
    AFTER_STATE_TRANSITION,

    /**
     *  <p>
     *      Gets triggered after a certain state transition caused by a certain
     *      event
     *  </p>
     */
    STATE_TRANSITION,

    /**
     * <p>
     *     Gets triggered after any state transition
     * </p>
     */
    ANY_STATE_TRANSITION,

    /**
     * <p>
     *     Gets triggered whenever there is an error
     * </p>
     */
    ERROR,

    /**
     * <p>
     *     Gets triggered when the final state is reached
     * </p>
     */
    FINAL_STATE
}
