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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kie.workbench.common.stunner.core.util.HashUtil;

@XmlRootElement(name = "outputDataItem", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class OutputDataItem {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String itemSubjectRef;

    @XmlAttribute
    private String name;

    // Used to create connection with ItemDefinition in Definitions
    @XmlTransient
    private String variableType;

    public OutputDataItem() {
    }

    public OutputDataItem(String id, String name, String itemSubjectRef) {
        this.id = id;
        this.name = name;
        this.itemSubjectRef = itemSubjectRef;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getItemSubjectRef() {
        return itemSubjectRef;
    }

    public void setItemSubjectRef(String itemSubjectRef) {
        this.itemSubjectRef = itemSubjectRef;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVariableType() {
        return variableType;
    }

    public void setVariableType(String variableType) {
        this.variableType = variableType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Property)) {
            return false;
        }
        Property property = (Property) o;
        return Objects.equals(getId(), property.getId())
                && Objects.equals(getItemSubjectRef(), property.getItemSubjectRef())
                && Objects.equals(getName(), property.getName());
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(id),
                                         Objects.hashCode(itemSubjectRef),
                                         Objects.hashCode(name));
    }
}
