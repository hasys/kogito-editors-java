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

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.kie.soup.commons.util.Maps;
import org.kie.soup.commons.util.Sets;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNCategories;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNViewDefinition;
import org.kie.workbench.common.stunner.bpmn.definition.property.simulation.SimulationSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskType;
import org.kie.workbench.common.stunner.bpmn.definition.property.task.TaskTypes;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.AdvancedData;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Category;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Labels;
import org.kie.workbench.common.stunner.core.definition.annotation.morph.MorphBase;
import org.kie.workbench.common.stunner.core.definition.annotation.morph.MorphProperty;
import org.kie.workbench.common.stunner.core.definition.annotation.morph.MorphPropertyValueBinding;
import org.kie.workbench.common.stunner.core.util.HashUtil;

@MorphBase(defaultType = NoneTask.class, targets = {BaseNonContainerSubprocess.class})
public abstract class BaseTask extends FlowNode implements BPMNViewDefinition {

    @XmlTransient
    public static final Set<String> TASK_LABELS = new Sets.Builder<String>()
            .add("all")
            .add("lane_child")
            .add("sequence_start")
            .add("sequence_end")
            .add("from_task_event")
            .add("to_task_event")
            .add("messageflow_start")
            .add("messageflow_end")
            .add("fromtoall")
            .add("ActivitiesMorph")
            .add("cm_activity")
            .build();

    @Category
    @XmlTransient
    public static final transient String category = BPMNCategories.ACTIVITIES;

    @Property
    @XmlTransient
    @MorphProperty(binder = TaskTypeMorphPropertyBinding.class)
    protected TaskType taskType;

    @Property
    @XmlTransient
    protected SimulationSet simulationSet;

    @Property
    @FormField(
            afterElement = "dimensionsSet"
    )
    @Valid
    @XmlTransient
    protected AdvancedData advancedData;

    @XmlTransient
    public static class TaskTypeMorphPropertyBinding implements MorphPropertyValueBinding<TaskType, TaskTypes> {

        @XmlTransient
        private static final Map<TaskTypes, Class<?>> MORPH_TARGETS =
                new Maps.Builder<TaskTypes, Class<?>>()
                        .put(TaskTypes.NONE,
                             NoneTask.class)
                        .put(TaskTypes.USER,
                             UserTask.class)
                        .put(TaskTypes.SCRIPT,
                             ScriptTask.class)
                        .put(TaskTypes.BUSINESS_RULE,
                             BusinessRuleTask.class)
                        .put(TaskTypes.SERVICE_TASK,
                             GenericServiceTask.class)
                        .build();

        @Override
        public TaskTypes getValue(final TaskType property) {
            return property.getValue();
        }

        @Override
        public Map<TaskTypes, Class<?>> getMorphTargets() {
            return MORPH_TARGETS;
        }
    }

    @Labels
    @XmlTransient
    protected final Set<String> labels = new HashSet<>(TASK_LABELS);

    public BaseTask() {
        this("", "", new SimulationSet(), new TaskType(), new AdvancedData());
    }

    public BaseTask(final @MapsTo("name") String name,
                    final @MapsTo("documentation") String documentation,
                    final @MapsTo("simulationSet") SimulationSet simulationSet,
                    final @MapsTo("taskType") TaskType taskType,
                    final @MapsTo("advancedData") AdvancedData advancedData) {
        super(name, documentation, advancedData);
        this.simulationSet = simulationSet;
        this.taskType = taskType;
    }

    public String getCategory() {
        return category;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(final TaskType taskType) {
        this.taskType = taskType;
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(getClass()),
                                         super.hashCode(),
                                         Objects.hashCode(taskType),
                                         Objects.hashCode(simulationSet),
                                         Objects.hashCode(labels),
                                         Objects.hashCode(advancedData));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof BaseTask) {
            BaseTask other = (BaseTask) o;
            return super.equals(other) &&
                    Objects.equals(taskType, other.taskType) &&
                    Objects.equals(simulationSet, other.simulationSet) &&
                    Objects.equals(labels, other.labels) &&
                    Objects.equals(advancedData, other.advancedData);
        }
        return false;
    }
}
