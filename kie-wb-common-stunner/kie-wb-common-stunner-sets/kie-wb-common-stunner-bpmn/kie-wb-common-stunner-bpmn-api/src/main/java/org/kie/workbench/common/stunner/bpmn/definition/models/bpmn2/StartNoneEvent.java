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

package org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2;

import java.util.Objects;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.adf.definitions.annotations.FieldParam;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormDefinition;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.forms.adf.definitions.settings.FieldPolicy;
import org.kie.workbench.common.stunner.bpmn.definition.models.drools.MetaData;
import org.kie.workbench.common.stunner.bpmn.definition.property.event.BaseStartEventExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.AdvancedData;
import org.kie.workbench.common.stunner.core.definition.annotation.Definition;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.morph.Morph;
import org.kie.workbench.common.stunner.core.util.HashUtil;
import org.kie.workbench.common.stunner.core.util.StringUtils;

import static org.kie.workbench.common.forms.adf.engine.shared.formGeneration.processing.fields.fieldInitializers.nestedForms.SubFormFieldInitializer.COLLAPSIBLE_CONTAINER;
import static org.kie.workbench.common.forms.adf.engine.shared.formGeneration.processing.fields.fieldInitializers.nestedForms.SubFormFieldInitializer.FIELD_CONTAINER_PARAM;

@Portable
@Bindable
@Definition
@Morph(base = StartEvent.class)
@FormDefinition(
        startElement = "name",
        policy = FieldPolicy.ONLY_MARKED,
        defaultFieldSettings = {@FieldParam(name = FIELD_CONTAINER_PARAM, value = COLLAPSIBLE_CONTAINER)}
)
@XmlRootElement(name = "startEvent", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class StartNoneEvent extends StartEvent {

    @Property
    @FormField(afterElement = "documentation")
    @Valid
    @XmlTransient
    protected BaseStartEventExecutionSet executionSet;

    public StartNoneEvent() {
        this("",
             "",
             new AdvancedData(),
             new BaseStartEventExecutionSet());
    }

    public StartNoneEvent(final @MapsTo("name") String name,
                          final @MapsTo("documentation") String documentation,
                          final @MapsTo("advancedData") AdvancedData advancedData,
                          final @MapsTo("executionSet") BaseStartEventExecutionSet executionSet) {
        super(name,
              documentation,
              advancedData);
        this.executionSet = executionSet;
    }

    public BaseStartEventExecutionSet getExecutionSet() {
        return executionSet;
    }

    public void setExecutionSet(BaseStartEventExecutionSet executionSet) {
        this.executionSet = executionSet;
    }

    /*
    Used only for marshalling/unmarshalling purposes. Shouldn't be handled in Equals/HashCode.
    Variable is not used and always null. Getters/setters redirect data from other execution sets.
    Execution sets not removed due to how forms works now, should be refactored during the migration
    to the new forms.
     */
    @Override
    public ExtensionElements getExtensionElements() {
        ExtensionElements elements = super.getExtensionElements();
        if (StringUtils.nonEmpty(this.getExecutionSet().getSlaDueDate())) {
            MetaData sla = new MetaData("customSLADueDate", this.getExecutionSet().getSlaDueDate());
            elements.getMetaData().add(sla);
        }

        return elements.getMetaData().isEmpty() ? null : elements;
    }

    /*
    Used only for marshalling/unmarshalling purposes. Shouldn't be handled in Equals/HashCode.
    Variable is not used and always null. Getters/setters redirect data from other execution sets.
    Execution sets not removed due to how forms works now, should be refactored during the migration
    to the new forms.
     */
    @Override
    public void setExtensionElements(ExtensionElements extensionElements) {
        super.setExtensionElements(extensionElements);
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(super.hashCode(),
                                         Objects.hashCode(executionSet));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StartNoneEvent) {
            StartNoneEvent other = (StartNoneEvent) o;
            return super.equals(other) &&
                    Objects.equals(executionSet, other.executionSet);
        }
        return false;
    }
}
