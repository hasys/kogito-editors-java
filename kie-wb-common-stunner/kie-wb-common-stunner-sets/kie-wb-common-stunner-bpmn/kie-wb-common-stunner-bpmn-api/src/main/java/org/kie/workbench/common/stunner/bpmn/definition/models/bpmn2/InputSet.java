/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.treblereel.gwt.xml.mapper.api.annotation.XmlUnwrappedCollection;

@XmlRootElement(name = "inputSet", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class InputSet {

    @XmlElement(name = "dataInputRefs")
    @XmlUnwrappedCollection
    private List<DataInputRefs> dataInputRefs = new ArrayList<>();

    public List<DataInputRefs> getDataInputRefs() {
        return dataInputRefs;
    }

    public void setDataInputRefs(List<DataInputRefs> dataInputRefs) {
        this.dataInputRefs = dataInputRefs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof InputSet)) {
            return false;
        }
        InputSet inputSet = (InputSet) o;
        return Objects.equals(getDataInputRefs(), inputSet.getDataInputRefs());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDataInputRefs());
    }
}
