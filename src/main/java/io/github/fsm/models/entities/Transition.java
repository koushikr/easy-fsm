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
package io.github.fsm.models.entities;

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
