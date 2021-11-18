/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.bpmn.definition;

import java.util.Objects;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlCData;

import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.forms.adf.definitions.annotations.metaModel.FieldValue;
import org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textArea.type.TextAreaFieldType;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.FlowNode;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.property.Value;
import org.kie.workbench.common.stunner.core.definition.property.PropertyMetaTypes;
import org.kie.workbench.common.stunner.core.util.HashUtil;

/**
 * Marker interface for all BPMN definitions like nodes or artifacts, connections.
 */
public abstract class FlowElement implements FlowElementInterface {

    @Value
    @FieldValue
    @Valid
    @XmlAttribute
    @FormField(type = TextAreaFieldType.class)
    @Property(meta = PropertyMetaTypes.NAME)
    protected String name;

    @Property
    @Value
    @Valid
    @FieldValue
    @XmlCData
    @FormField(
            type = TextAreaFieldType.class,
            afterElement = "documentation"
    )
    @XmlAttribute(namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
    protected String documentation;

    @XmlAttribute
    private String id;

    public FlowElement() {
    }

    public FlowElement(final String name, final String documentation) {
        this.name = name;
        this.documentation = documentation;
    }

    public FlowElement(final String id, final String name, final String documentation) {
        this.id = id;
        this.name = name;
        this.documentation = documentation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlowNode)) {
            return false;
        }
        FlowNode flowNode = (FlowNode) o;
        return Objects.equals(getName(), flowNode.getName())
                && Objects.equals(getDocumentation(), flowNode.getDocumentation())
                && Objects.equals(getId(), flowNode.getId());
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(getClass()),
                                         Objects.hashCode(getId()),
                                         Objects.hashCode(getName()),
                                         Objects.hashCode(getDocumentation()));
    }
}
