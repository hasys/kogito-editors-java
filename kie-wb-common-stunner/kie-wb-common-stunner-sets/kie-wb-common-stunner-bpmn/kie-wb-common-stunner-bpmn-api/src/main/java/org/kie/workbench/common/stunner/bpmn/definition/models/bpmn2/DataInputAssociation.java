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

@XmlRootElement(name = "dataInputAssociation", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class DataInputAssociation {

    @XmlElement
    private TargetRef targetRef;

    @XmlElement
    private Assignment assignment;

    @XmlElement
    private SourceRef sourceRef;

    public DataInputAssociation() {
        this(new TargetRef(), new Assignment());
    }

    public DataInputAssociation(String target, String source) {
        this(new TargetRef(target), new SourceRef(source));
    }

    public DataInputAssociation(TargetRef target, Assignment assignment) {
        this.targetRef = target;
        this.assignment = assignment;
    }

    public DataInputAssociation(TargetRef target, SourceRef source) {
        this.targetRef = target;
        this.sourceRef = source;
    }

    public TargetRef getTargetRef() {
        return targetRef;
    }

    public void setTargetRef(TargetRef targetRef) {
        this.targetRef = targetRef;
    }

    public Assignment getAssignment() {
        return assignment;
    }

    public void setAssignment(Assignment assignment) {
        this.assignment = assignment;
    }

    public SourceRef getSourceRef() {
        return sourceRef;
    }

    public void setSourceRef(SourceRef sourceRef) {
        this.sourceRef = sourceRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DataInputAssociation)) {
            return false;
        }
        DataInputAssociation that = (DataInputAssociation) o;
        return Objects.equals(getTargetRef(), that.getTargetRef())
                && Objects.equals(getAssignment(), that.getAssignment());
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(targetRef),
                                         Objects.hashCode(assignment));
    }
}
