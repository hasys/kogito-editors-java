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
import org.kie.workbench.common.stunner.bpmn.definition.models.drools.OnEntryScript;
import org.kie.workbench.common.stunner.bpmn.definition.models.drools.OnExitScript;
import org.kie.workbench.common.stunner.bpmn.definition.property.simulation.SimulationSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.ScriptTypeValue;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskType;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskTypes;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.UserTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.AdvancedData;
import org.kie.workbench.common.stunner.core.definition.annotation.Definition;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.morph.Morph;
import org.kie.workbench.common.stunner.core.rule.annotation.CanDock;
import org.kie.workbench.common.stunner.core.util.HashUtil;

import static org.kie.workbench.common.forms.adf.engine.shared.formGeneration.processing.fields.fieldInitializers.nestedForms.SubFormFieldInitializer.COLLAPSIBLE_CONTAINER;
import static org.kie.workbench.common.forms.adf.engine.shared.formGeneration.processing.fields.fieldInitializers.nestedForms.SubFormFieldInitializer.FIELD_CONTAINER_PARAM;

@Portable
@Bindable
@Definition
@CanDock(roles = {"IntermediateEventOnActivityBoundary"})
@Morph(base = BaseTask.class)
@FormDefinition(
        policy = FieldPolicy.ONLY_MARKED,
        startElement = "general",
        defaultFieldSettings = {@FieldParam(name = FIELD_CONTAINER_PARAM, value = COLLAPSIBLE_CONTAINER)}
)
@XmlRootElement(name = "task", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class UserTask extends BaseUserTask<UserTaskExecutionSet> {

    @Property
    @FormField(
            afterElement = "general"
    )
    @Valid
    @XmlTransient
    protected UserTaskExecutionSet executionSet;

    @XmlAttribute(namespace = "http://www.jboss.org/drools")
    private String taskName;

    public UserTask() {
        this("Task",
             "",
             new UserTaskExecutionSet(),
             new SimulationSet(),
             new TaskType(TaskTypes.USER),
             new AdvancedData());
    }

    public UserTask(final @MapsTo("name") String name,
                    final @MapsTo("documentation") String documentation,
                    final @MapsTo("executionSet") UserTaskExecutionSet executionSet,
                    final @MapsTo("simulationSet") SimulationSet simulationSet,
                    final @MapsTo("taskType") TaskType taskType,
                    final @MapsTo("advancedData") AdvancedData advancedData) {
        super(name,
              documentation,
              simulationSet,
              taskType,
              advancedData);
        this.executionSet = executionSet;
    }

    @Override
    public UserTaskExecutionSet getExecutionSet() {
        return executionSet;
    }

    @Override
    public void setExecutionSet(final UserTaskExecutionSet executionSet) {
        this.executionSet = executionSet;
    }

    public String getTaskName() {
        return executionSet.getTaskName().getValue();
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public ExtensionElements getExtensionElements() {
        ExtensionElements elements = super.getExtensionElements();
        if (elements == null) {
            elements = new ExtensionElements();
        }

        List<MetaData> metaData = new ArrayList<>();
        if (executionSet.getIsAsync().getValue()) {
            MetaData customAutoStart = new MetaData("customAsync",
                                                    "true");
            metaData.add(customAutoStart);
        }

        if (executionSet.getAdHocAutostart().getValue()) {
            MetaData customAutoStart = new MetaData("customAutoStart",
                                                    "true");
            metaData.add(customAutoStart);
        }

        if (executionSet.getSlaDueDate().getValue() != null && !executionSet.getSlaDueDate().getValue().isEmpty()) {
            MetaData customAutoStart = new MetaData("customSLADueDate",
                                                    executionSet.getSlaDueDate().getValue());
            metaData.add(customAutoStart);
        }
        elements.setMetaData(metaData);

        if (!executionSet.getOnEntryAction().getValue().isEmpty()
                && !executionSet.getOnEntryAction().getValue().getValues().isEmpty()) {
            ScriptTypeValue value = executionSet.getOnEntryAction().getValue().getValues().get(0);
            OnEntryScript entryScript = new OnEntryScript(value.getLanguage(), value.getScript());
            elements.setOnEntryScript(entryScript);
        }

        if (!executionSet.getOnExitAction().getValue().isEmpty()
                && !executionSet.getOnExitAction().getValue().getValues().isEmpty()) {
            ScriptTypeValue value = executionSet.getOnExitAction().getValue().getValues().get(0);
            OnExitScript exitScript = new OnExitScript(value.getLanguage(), value.getScript());
            elements.setOnExitScript(exitScript);
        }

        return elements.getMetaData().isEmpty() ? null : elements;
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(super.hashCode(),
                                         executionSet.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof UserTask) {
            UserTask other = (UserTask) o;
            return super.equals(other) &&
                    executionSet.equals(other.executionSet);
        }
        return false;
    }
}
