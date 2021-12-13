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
import org.kie.workbench.common.stunner.bpmn.definition.property.simulation.SimulationSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.ScriptTaskExecutionSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskType;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskTypes;
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
@XmlRootElement(name = "scriptTask", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class ScriptTask extends BaseTask {

    @Property
    @FormField(
            afterElement = "general"
    )
    @Valid
    @XmlTransient
    protected ScriptTaskExecutionSet executionSet;

    // For marshallers only. Always null value is taken from execution set for Forms compatibility.
    @XmlAttribute
    private String scriptFormat;

    // For marshallers only. Always null value is taken from execution set for Forms compatibility.
    private String script;

    public ScriptTask() {
        this("Task",
             "",
             new ScriptTaskExecutionSet(),
             new SimulationSet(),
             new TaskType(TaskTypes.SCRIPT),
             new AdvancedData());
    }

    public ScriptTask(final @MapsTo("name") String name,
                      final @MapsTo("documentation") String documentation,
                      final @MapsTo("executionSet") ScriptTaskExecutionSet executionSet,
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

    public ScriptTaskExecutionSet getExecutionSet() {
        return executionSet;
    }

    public void setExecutionSet(final ScriptTaskExecutionSet executionSet) {
        this.executionSet = executionSet;
    }

    // For marshallers only. Always null value is taken from execution set for Forms compatibility.
    public String getScriptFormat() {
        return executionSet.getScript().getValue().getLanguage();
    }

    // For marshallers only. Always null value is taken from execution set for Forms compatibility.
    public void setScriptFormat(String scriptFormat) {
        executionSet.getScript().getValue().setLanguage(scriptFormat);
    }

    public String getScript() {
        return executionSet.getScript().getValue().getScript();
    }

    public void setScript(String script) {
        executionSet.getScript().getValue().setScript(script);
    }

    /*
    Used only for marshalling/unmarshalling purposes. Shouldn't be handled in Equals/HashCode.
    Execution sets not removed due to how forms works now, should be refactored during the migration
    to the new forms.
     */
    @Override
    public ExtensionElements getExtensionElements() {
        ExtensionElements elements = super.getExtensionElements();

        MetaData customAsync = new MetaData("customAsync", executionSet.getIsAsync().getValue().toString());
        MetaData customAutoStart = new MetaData("customAutoStart", executionSet.getAdHocAutostart().getValue().toString());

        elements.getMetaData().add(customAsync);
        elements.getMetaData().add(customAutoStart);

        return elements;
    }

    /*
    Used only for marshalling/unmarshalling purposes. Shouldn't be handled in Equals/HashCode.
    Execution sets not removed due to how forms works now, should be refactored during the migration
    to the new forms.
     */
    @Override
    public void setExtensionElements(ExtensionElements extensionElements) {
        super.setExtensionElements(extensionElements);
        extensionElements.getMetaData().forEach(data -> {
            if ("customAsync".equals(data.getName())) {
                executionSet.getIsAsync().setValue(Boolean.parseBoolean(data.getMetaValue()));
            }
            if ("customAutoStart".equals(data.getName())) {
                executionSet.getAdHocAutostart().setValue(Boolean.parseBoolean(data.getMetaValue()));
            }
        });
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(super.hashCode(),
                                         executionSet.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ScriptTask) {
            ScriptTask other = (ScriptTask) o;
            return super.equals(other) &&
                    executionSet.equals(other.executionSet);
        }
        return false;
    }
}
