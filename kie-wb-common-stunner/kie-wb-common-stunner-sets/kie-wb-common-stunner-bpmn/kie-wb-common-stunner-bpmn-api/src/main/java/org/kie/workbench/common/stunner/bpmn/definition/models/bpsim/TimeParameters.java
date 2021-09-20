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
package org.kie.workbench.common.stunner.bpmn.definition.models.bpsim;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public class TimeParameters {

    @XmlElement(name = "ProcessingTime")
    private ProcessingTime processingTime;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public ProcessingTime getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(ProcessingTime processingTime) {
        this.processingTime = processingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeParameters)) {
            return false;
        }
        TimeParameters that = (TimeParameters) o;
        return Objects.equals(getProcessingTime(), that.getProcessingTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProcessingTime());
    }
}
