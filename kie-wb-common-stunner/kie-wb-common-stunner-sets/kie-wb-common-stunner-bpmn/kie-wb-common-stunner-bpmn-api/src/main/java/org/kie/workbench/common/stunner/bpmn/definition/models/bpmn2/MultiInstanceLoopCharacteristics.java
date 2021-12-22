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

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.kie.workbench.common.stunner.core.util.HashUtil;

@XmlRootElement(name = "multiInstanceLoopCharacteristics", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class MultiInstanceLoopCharacteristics {

    @XmlElement(namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
    private String loopDataInputRef;

    @XmlElement(namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
    private String loopDataOutputRef;

    @XmlElement(namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
    private InputDataItem inputDataItem = new InputDataItem();

    @XmlElement(namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
    private OutputDataItem outputDataItem = new OutputDataItem();

    @XmlElement(namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
    private CompletionCondition completionCondition = new CompletionCondition();

    public String getLoopDataInputRef() {
        return loopDataInputRef;
    }

    public void setLoopDataInputRef(String loopDataInputRef) {
        this.loopDataInputRef = loopDataInputRef;
    }

    public String getLoopDataOutputRef() {
        return loopDataOutputRef;
    }

    public void setLoopDataOutputRef(String loopDataOutputRef) {
        this.loopDataOutputRef = loopDataOutputRef;
    }

    public InputDataItem getInputDataItem() {
        return inputDataItem;
    }

    public void setInputDataItem(InputDataItem inputDataItem) {
        this.inputDataItem = inputDataItem;
    }

    public OutputDataItem getOutputDataItem() {
        return outputDataItem;
    }

    public void setOutputDataItem(OutputDataItem outputDataItem) {
        this.outputDataItem = outputDataItem;
    }

    public CompletionCondition getCompletionCondition() {
        return completionCondition;
    }

    public void setCompletionCondition(CompletionCondition completionCondition) {
        this.completionCondition = completionCondition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MultiInstanceLoopCharacteristics)) {
            return false;
        }
        MultiInstanceLoopCharacteristics that = (MultiInstanceLoopCharacteristics) o;
        return Objects.equals(getLoopDataInputRef(), that.getLoopDataInputRef())
                && Objects.equals(getLoopDataOutputRef(), that.getLoopDataOutputRef())
                && Objects.equals(getInputDataItem(), that.getInputDataItem())
                && Objects.equals(getOutputDataItem(), that.getOutputDataItem())
                && Objects.equals(getCompletionCondition(), that.getCompletionCondition());
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(loopDataInputRef),
                                         Objects.hashCode(loopDataOutputRef),
                                         Objects.hashCode(inputDataItem),
                                         Objects.hashCode(outputDataItem),
                                         Objects.hashCode(completionCondition));
    }
}
