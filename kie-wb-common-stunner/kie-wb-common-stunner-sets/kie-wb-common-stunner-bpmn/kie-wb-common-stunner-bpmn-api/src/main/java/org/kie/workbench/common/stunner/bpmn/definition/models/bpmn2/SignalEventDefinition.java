/*
 * Copyright 2022 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.kie.workbench.common.stunner.core.util.HashUtil;

@XmlRootElement(name = "signalEventDefinition", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class SignalEventDefinition {

    @XmlAttribute
    private String signalRef;

    public SignalEventDefinition() {

    }

    public SignalEventDefinition(String signalRef) {
        this.signalRef = signalRef;
    }

    public String getSignalRef() {
        return signalRef;
    }

    public void setSignalRef(String signalRef) {
        this.signalRef = signalRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SignalEventDefinition)) {
            return false;
        }
        SignalEventDefinition that = (SignalEventDefinition) o;
        return Objects.equals(getSignalRef(), that.getSignalRef());
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(signalRef));
    }
}
