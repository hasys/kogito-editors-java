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

import org.kie.workbench.common.stunner.core.util.HashUtil;
import org.treblereel.gwt.xml.mapper.api.annotation.XmlUnwrappedCollection;

@XmlRootElement(name = "ioSpecification", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class IoSpecification {

    @XmlElement(name = "dataInput")
    @XmlUnwrappedCollection
    private List<DataInput> dataInput = new ArrayList<>();

    @XmlElement(name = "dataOutput")
    @XmlUnwrappedCollection
    private List<DataOutput> dataOutput = new ArrayList<>();

    @XmlElement(name = "inputSet")
    private InputSet inputSet = new InputSet();

    @XmlElement(name = "outputSet")
    private OutputSet outputSet = new OutputSet();

    public List<DataInput> getDataInput() {
        return dataInput;
    }

    public void setDataInput(List<DataInput> dataInput) {
        this.dataInput = dataInput;
    }

    public InputSet getInputSet() {
        return inputSet;
    }

    public void setInputSet(InputSet inputSet) {
        this.inputSet = inputSet;
    }

    public List<DataOutput> getDataOutput() {
        return dataOutput;
    }

    public void setDataOutput(List<DataOutput> dataOutput) {
        this.dataOutput = dataOutput;
    }

    public OutputSet getOutputSet() {
        return outputSet;
    }

    public void setOutputSet(OutputSet outputSet) {
        this.outputSet = outputSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IoSpecification)) {
            return false;
        }
        IoSpecification that = (IoSpecification) o;
        return Objects.equals(getDataInput(), that.getDataInput())
                && Objects.equals(getInputSet(), that.getInputSet());
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(inputSet),
                                         Objects.hashCode(dataInput));
    }
}
