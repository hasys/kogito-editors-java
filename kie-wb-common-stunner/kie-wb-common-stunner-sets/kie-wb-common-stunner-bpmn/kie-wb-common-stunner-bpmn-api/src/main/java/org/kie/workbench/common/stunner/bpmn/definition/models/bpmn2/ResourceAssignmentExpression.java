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

@XmlRootElement(name = "resourceAssignmentExpression", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class ResourceAssignmentExpression {

    @XmlElement(namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
    private String formalExpression;

    public ResourceAssignmentExpression() {

    }

    public ResourceAssignmentExpression(String formalExpression) {
        this.formalExpression = formalExpression;
    }

    public String getFormalExpression() {
        return formalExpression;
    }

    public void setFormalExpression(String formalExpression) {
        this.formalExpression = formalExpression;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceAssignmentExpression)) {
            return false;
        }
        ResourceAssignmentExpression that = (ResourceAssignmentExpression) o;
        return Objects.equals(getFormalExpression(),
                              that.getFormalExpression());
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(formalExpression));
    }
}
