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
package io.github.fsm.exceptions;

import io.github.fsm.models.entities.Context;
import io.github.fsm.models.entities.Event;
import io.github.fsm.models.entities.State;
import lombok.Getter;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 */
@Getter
public class RunningtimeException extends Exception {
    private static final long serialVersionUID = 4362053831847081229L;
    private State state;
    private Event event;
    private Context context;

    public RunningtimeException(State state, Event event, Exception error, String message, Context context) {
        super(message, error);

        this.state = state;
        this.event = event;
        this.context = context;
    }

}
