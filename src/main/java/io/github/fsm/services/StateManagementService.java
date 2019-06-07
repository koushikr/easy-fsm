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

import io.github.fsm.models.entities.State;

import java.util.Collection;
import java.util.Set;

/**
 * Entity by : koushikr.
 * on 23/10/15.
 *
 * <p>An interface for state management</p>
 */
public interface StateManagementService {

    State getFrom();

    void addEndStates(Collection<State> endStates);

    void setFrom(State state);

    Set<State> getEndStates();

    Set<State> getReferenceStates();

}
