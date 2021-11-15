/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.bpmn.definition.property.event;

import java.util.Objects;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormDefinition;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.checkBox.type.CheckBoxFieldType;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNPropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.util.HashUtil;

@Portable
@Bindable
@FormDefinition(startElement = "isInterrupting")
public class BaseStartEventExecutionSet implements BPMNPropertySet {

    @Property
    @FormField(type = CheckBoxFieldType.class)
    @Valid
    private Boolean isInterrupting;

    @Property
    @FormField(afterElement = "isInterrupting")
    @Valid
    @XmlElement(name = "metaData")
    private String slaDueDate;

    public BaseStartEventExecutionSet() {
        this(true, "");
    }

    public BaseStartEventExecutionSet(final @MapsTo("isInterrupting") Boolean isInterrupting,
                                      final @MapsTo("slaDueDate") String slaDueDate) {
        this.isInterrupting = isInterrupting;
        this.slaDueDate = slaDueDate;
    }

    public Boolean getIsInterrupting() {
        return isInterrupting;
    }

    public void setIsInterrupting(Boolean isInterrupting) {
        this.isInterrupting = isInterrupting;
    }

    public String getSlaDueDate() {
        return slaDueDate;
    }

    public void setSlaDueDate(String slaDueDate) {
        this.slaDueDate = slaDueDate;
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(isInterrupting),
                                         Objects.hashCode(slaDueDate));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BaseStartEventExecutionSet) {
            BaseStartEventExecutionSet other = (BaseStartEventExecutionSet) o;
            return Objects.equals(isInterrupting, other.isInterrupting) &&
                    Objects.equals(slaDueDate, other.slaDueDate);
        }
        return false;
    }
}
