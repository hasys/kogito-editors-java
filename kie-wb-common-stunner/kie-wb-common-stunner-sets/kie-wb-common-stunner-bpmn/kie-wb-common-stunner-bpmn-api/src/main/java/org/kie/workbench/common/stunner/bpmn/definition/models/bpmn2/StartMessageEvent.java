/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import elemental2.dom.DomGlobal;
import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.adf.definitions.annotations.FieldParam;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormDefinition;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.forms.adf.definitions.settings.FieldPolicy;
import org.kie.workbench.common.stunner.bpmn.definition.models.drools.MetaData;
import org.kie.workbench.common.stunner.bpmn.definition.property.assignment.AssignmentParser;
import org.kie.workbench.common.stunner.bpmn.definition.property.dataio.DataIOModel;
import org.kie.workbench.common.stunner.bpmn.definition.property.dataio.DataIOSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.event.message.InterruptingMessageEventExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.AdvancedData;
import org.kie.workbench.common.stunner.core.definition.annotation.Definition;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.morph.Morph;
import org.kie.workbench.common.stunner.core.util.HashUtil;
import org.kie.workbench.common.stunner.core.util.StringUtils;
import org.treblereel.gwt.xml.mapper.api.annotation.XmlUnwrappedCollection;

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
public class StartMessageEvent extends StartEvent implements DataIOModel {

    @Property
    @FormField(afterElement = "documentation")
    @Valid
    @XmlTransient
    protected InterruptingMessageEventExecutionSet executionSet;

    @Property
    @FormField(afterElement = "executionSet")
    @Valid
    @XmlTransient
    protected DataIOSet dataIOSet;

    @XmlTransient
    private String messageId;

    public MessageEventDefinition messageEventDefinition;

    @XmlElement(name = "dataOutput")
    @XmlUnwrappedCollection
    public List<DataOutput> dataOutputs;

    @XmlElement(name = "dataOutputAssociation")
    @XmlUnwrappedCollection
    public List<DataOutputAssociation> dataOutputAssociation;

    @XmlElement(name = "outputSet")
    @XmlUnwrappedCollection
    public List<OutputSet> outputSet;

    public StartMessageEvent() {
        this("",
             "",
             new AdvancedData(),
             new DataIOSet(),
             new InterruptingMessageEventExecutionSet());
    }

    public StartMessageEvent(final @MapsTo("name") String name,
                             final @MapsTo("documentation") String documentation,
                             final @MapsTo("advancedData") AdvancedData advancedData,
                             final @MapsTo("dataIOSet") DataIOSet dataIOSet,
                             final @MapsTo("executionSet") InterruptingMessageEventExecutionSet executionSet) {

        super(name,
              documentation,
              advancedData);
        this.dataIOSet = dataIOSet;
        this.executionSet = executionSet;

        labels.add("messageflow_end");
    }

    public InterruptingMessageEventExecutionSet getExecutionSet() {
        return executionSet;
    }

    public void setExecutionSet(final InterruptingMessageEventExecutionSet executionSet) {
        this.executionSet = executionSet;
    }

    public DataIOSet getDataIOSet() {
        return dataIOSet;
    }

    public void setDataIOSet(DataIOSet dataIOSet) {
        this.dataIOSet = dataIOSet;
    }

    @Override
    public boolean hasOutputVars() {
        return true;
    }

    @Override
    public boolean isSingleOutputVar() {
        return true;
    }

    public MessageEventDefinition getMessageEventDefinition() {
        return new MessageEventDefinition(executionSet.getMessageRef().getValue(), messageId);
    }

    public void setMessageEventDefinition(MessageEventDefinition messageEventDefinition) {
        this.messageEventDefinition = messageEventDefinition;
    }

    public Message getMessage() {
        return new Message(getMessageId(),
                           executionSet.getMessageRef().getValue(),
                           executionSet.getMessageRef().getValue() + "Type");
    }

    public String getMessageId() {
        DomGlobal.console.info("ID getter: " + messageId);
        return messageId;
    }

    public void setMessageId(String messageId) {
        DomGlobal.console.info("set ID: " + messageId);
        this.messageId = messageId;
    }

    public List<DataOutput> getDataOutputs() {
        return AssignmentParser.parseDataOutputs(dataIOSet.getAssignmentsinfo().getValue(), getId());
    }

    public void setDataOutputs(List<DataOutput> dataOutputs) {
        this.dataOutputs = dataOutputs;
    }

    public List<DataOutputAssociation> getDataOutputAssociation() {
        return AssignmentParser.parseDataOutputAssociation(getId(), dataIOSet.getAssignmentsinfo().getValue());
    }

    public void setDataOutputAssociation(List<DataOutputAssociation> dataOutputAssociation) {
        this.dataOutputAssociation = dataOutputAssociation;
    }

    public List<OutputSet> getOutputSet() {
        List<OutputSet> outputSets = new ArrayList<>();
        List<DataOutput> outputs = AssignmentParser.parseDataOutputs(dataIOSet.getAssignmentsinfo().getValue(), getId());
        if (!outputs.isEmpty()) {
            OutputSet os = new OutputSet();
            os.getDataOutputRefs().add(new DataOutputRefs(outputs.get(0).getId()));
            outputSets.add(os);
        }
        return outputSets;
    }

    public void setOutputSet(List<OutputSet> outputSet) {
        this.outputSet = outputSet;
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
                                         executionSet.hashCode(),
                                         dataIOSet.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof StartMessageEvent) {
            StartMessageEvent other = (StartMessageEvent) o;
            return super.equals(other) &&
                    Objects.equals(executionSet, other.executionSet) &&
                    Objects.equals(dataIOSet, other.dataIOSet);
        }
        return false;
    }
}
