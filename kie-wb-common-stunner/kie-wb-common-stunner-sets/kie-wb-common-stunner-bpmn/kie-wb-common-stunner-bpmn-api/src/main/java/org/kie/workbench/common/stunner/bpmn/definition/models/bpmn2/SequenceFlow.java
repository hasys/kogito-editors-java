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
import javax.xml.bind.annotation.XmlAttribute;
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
import org.kie.workbench.common.stunner.bpmn.definition.property.connectors.SequenceFlowExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.ScriptTypeValue;
import org.kie.workbench.common.stunner.core.definition.annotation.Definition;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.factory.graph.EdgeFactory;
import org.kie.workbench.common.stunner.core.rule.annotation.CanConnect;
import org.kie.workbench.common.stunner.core.rule.annotation.EdgeOccurrences;
import org.kie.workbench.common.stunner.core.rule.annotation.RuleExtension;
import org.kie.workbench.common.stunner.core.rule.ext.impl.ConnectorParentsMatchHandler;
import org.kie.workbench.common.stunner.core.util.HashUtil;

import static org.kie.workbench.common.forms.adf.engine.shared.formGeneration.processing.fields.fieldInitializers.nestedForms.SubFormFieldInitializer.COLLAPSIBLE_CONTAINER;
import static org.kie.workbench.common.forms.adf.engine.shared.formGeneration.processing.fields.fieldInitializers.nestedForms.SubFormFieldInitializer.FIELD_CONTAINER_PARAM;

@Portable
@Bindable
@Definition(graphFactory = EdgeFactory.class)
// *** Connection rules for sequence flows ****
@CanConnect(startRole = "sequence_start", endRole = "sequence_end")
@CanConnect(startRole = "choreography_sequence_start", endRole = "choreography_sequence_end")
@CanConnect(startRole = "Exclusive_Eventbased_Gateway", endRole = "FromEventbasedGateway")
@CanConnect(startRole = "EventbasedGateway", endRole = "FromEventbasedGateway")
// **** Cardinality rules for connectors ****
// No incoming sequence flows for start events.
@EdgeOccurrences(role = "Startevents_all", type = EdgeOccurrences.EdgeType.INCOMING, max = 0)
// No outgoing sequence flows for end events.
@EdgeOccurrences(role = "Endevents_all", type = EdgeOccurrences.EdgeType.OUTGOING, max = 0)
// A single outgoing sequence flows for event types that can be docked (boundary) such as Intermediate Timer Event
@EdgeOccurrences(role = "IntermediateEventOnActivityBoundary", type = EdgeOccurrences.EdgeType.OUTGOING, max = 1)
@EdgeOccurrences(role = "IntermediateEventCatching", type = EdgeOccurrences.EdgeType.INCOMING, max = 1)
@EdgeOccurrences(role = "IntermediateEventThrowing", type = EdgeOccurrences.EdgeType.INCOMING, max = 1)
@EdgeOccurrences(role = "IntermediateEventThrowing", type = EdgeOccurrences.EdgeType.OUTGOING, min = 1)
@EdgeOccurrences(role = "IntermediateEventThrowing", type = EdgeOccurrences.EdgeType.OUTGOING, max = 1)
// Sequence flows cannot exceed bounds when any of the nodes are in an subprocess context.
@RuleExtension(handler = ConnectorParentsMatchHandler.class,
        typeArguments = {EmbeddedSubprocess.class, EventSubprocess.class,
                MultipleInstanceSubprocess.class, AdHocSubprocess.class},
        arguments = {"Sequence flow connectors cannot exceed the parent's subprocess bounds. " +
                "Both source and target nodes must be in the same subprocess."})
@FormDefinition(
        startElement = "name",
        policy = FieldPolicy.ONLY_MARKED,
        defaultFieldSettings = {@FieldParam(name = FIELD_CONTAINER_PARAM, value = COLLAPSIBLE_CONTAINER)}
)
@XmlRootElement(name = "sequenceFlow", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class SequenceFlow extends BaseConnector {

    // Marshalled in Extensions metadata
    @Property
    @FormField(
            afterElement = "documentation"
    )
    @Valid
    @XmlTransient
    protected SequenceFlowExecutionSet executionSet;

    @XmlAttribute
    private String id;

    // Marshalled in Extensions metadata
    @XmlTransient
    private boolean isAutoConnectionSource = false;

    // Marshalled in Extensions metadata
    @XmlTransient
    private boolean isAutoConnectionTarget = false;

    // This variable is never assigner, used for marshalling,
    // get and set methods leads to executionSet.priority
    @XmlAttribute(namespace = "http://www.jboss.org/drools")
    private String priority;

    @XmlAttribute
    private String sourceRef;

    @XmlAttribute
    private String targetRef;

    private ConditionExpression conditionExpression;

    public SequenceFlow() {
        this("",
             "",
             new SequenceFlowExecutionSet());
    }

    public SequenceFlow(final @MapsTo("name") String name,
                        final @MapsTo("documentation") String documentation,
                        final @MapsTo("executionSet") SequenceFlowExecutionSet executionSet) {
        super(name,
              documentation);
        this.executionSet = executionSet;
    }

    public SequenceFlowExecutionSet getExecutionSet() {
        return executionSet;
    }

    public void setExecutionSet(final SequenceFlowExecutionSet executionSet) {
        this.executionSet = executionSet;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPriority() {
        return executionSet.getPriority();
    }

    public void setPriority(String priority) {
        executionSet.setPriority(priority);
    }

    public String getSourceRef() {
        return sourceRef;
    }

    public void setSourceRef(String sourceRef) {
        this.sourceRef = sourceRef;
    }

    public String getTargetRef() {
        return targetRef;
    }

    public void setTargetRef(String targetRef) {
        this.targetRef = targetRef;
    }

    public ConditionExpression getConditionExpression() {
        ConditionExpression expression = new ConditionExpression();
        expression.setLanguage(executionSet.getConditionExpression().getValue().getLanguage());
        expression.setValue(executionSet.getConditionExpression().getValue().getScript());

        return expression;
    }

    public void setConditionExpression(ConditionExpression conditionExpression) {
        ScriptTypeValue scriptTypeValue = new ScriptTypeValue();
        scriptTypeValue.setScript(conditionExpression.getValue());
        scriptTypeValue.setLanguage(conditionExpression.getLanguage());

        org.kie.workbench.common.stunner.bpmn.definition.property.common.ConditionExpression expression = new org.kie.workbench.common.stunner.bpmn.definition.property.common.ConditionExpression(scriptTypeValue);

        executionSet = new SequenceFlowExecutionSet();
        executionSet.setConditionExpression(expression);
    }

    public boolean isAutoConnectionSource() {
        return isAutoConnectionSource;
    }

    public void setAutoConnectionSource(boolean autoConnectionSource) {
        isAutoConnectionSource = autoConnectionSource;
    }

    public boolean isAutoConnectionTarget() {
        return isAutoConnectionTarget;
    }

    public void setAutoConnectionTarget(boolean autoConnectionTarget) {
        isAutoConnectionTarget = autoConnectionTarget;
    }

    @Override
    public ExtensionElements getExtensionElements() {
        ExtensionElements elements = super.getExtensionElements();
        if (elements == null) {
            elements = new ExtensionElements();
        }

        List<MetaData> metaData = new ArrayList<>();
        if (isAutoConnectionSource) {
            MetaData autoConnectionSource = new MetaData("isAutoConnection.source",
                                                         "true");
            metaData.add(autoConnectionSource);
        }

        if (isAutoConnectionTarget) {
            MetaData autoConnectionTarget = new MetaData("isAutoConnection.target",
                                                         "true");
            metaData.add(autoConnectionTarget);
        }
        elements.setMetaData(metaData);

        return elements.getMetaData().isEmpty() ? null : elements;
    }

    @Override
    public void setExtensionElements(ExtensionElements extensionElements) {
        super.setExtensionElements(extensionElements);
        for (MetaData meta : extensionElements.getMetaData()) {
            if ("isAutoConnection.source".equals(meta.getName())) {
                isAutoConnectionSource = Boolean.parseBoolean(meta.getMetaValue());
            }

            if ("isAutoConnection.target".equals(meta.getName())) {
                isAutoConnectionTarget = Boolean.parseBoolean(meta.getMetaValue());
            }
        }
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(super.hashCode(),
                                         Objects.hashCode(sourceRef),
                                         Objects.hashCode(targetRef),
                                         Objects.hashCode(isAutoConnectionSource),
                                         Objects.hashCode(isAutoConnectionTarget),
                                         Objects.hashCode(executionSet));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SequenceFlow) {
            SequenceFlow other = (SequenceFlow) o;
            return super.equals(other)
                    && Objects.equals(sourceRef, other.sourceRef)
                    && Objects.equals(targetRef, other.targetRef)
                    && Objects.equals(isAutoConnectionSource, other.isAutoConnectionSource)
                    && Objects.equals(isAutoConnectionTarget, other.isAutoConnectionTarget)
                    && Objects.equals(executionSet, other.executionSet);
        }
        return false;
    }
}
