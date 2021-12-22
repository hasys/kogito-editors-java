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
import javax.xml.bind.annotation.XmlElement;
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
import org.kie.workbench.common.stunner.bpmn.definition.property.assignment.AssignmentParser;
import org.kie.workbench.common.stunner.bpmn.definition.property.notification.NotificationValue;
import org.kie.workbench.common.stunner.bpmn.definition.property.reassignment.ReassignmentValue;
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
import org.treblereel.gwt.xml.mapper.api.annotation.XmlUnwrappedCollection;

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

    // Used to easier creation of ItemDefinitions in Definitions root node
    @XmlTransient
    private List<ItemDefinition> itemDefinitions = new ArrayList<>();

    @XmlElement
    private IoSpecification ioSpecification;

    @XmlElement(name = "dataInputAssociation")
    @XmlUnwrappedCollection
    private List<DataInputAssociation> dataInputAssociation = new ArrayList<>();

    @XmlElement(name = "dataOutputAssociation")
    @XmlUnwrappedCollection
    private List<DataOutputAssociation> dataOutputAssociation = new ArrayList<>();

    @XmlElement(name = "potentialOwner")
    @XmlUnwrappedCollection
    private List<PotentialOwner> potentialOwners = new ArrayList<>();

    @XmlElement(name = "multiInstanceLoopCharacteristics")
    private MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics;

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

    @Override
    public ExtensionElements getExtensionElements() {
        ExtensionElements elements = super.getExtensionElements();
        if (elements == null) {
            elements = new ExtensionElements();
        }

        List<MetaData> metaData = elements.getMetaData();
        metaData = metaData != null ? metaData : new ArrayList<>();
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
                && !executionSet.getOnEntryAction().getValue().getValues().isEmpty()
                && !executionSet.getOnEntryAction().getValue().getValues().get(0).getScript().isEmpty()) {
            ScriptTypeValue value = executionSet.getOnEntryAction().getValue().getValues().get(0);
            OnEntryScript entryScript = new OnEntryScript(value.getLanguage(), value.getScript());
            elements.setOnEntryScript(entryScript);
        }

        if (!executionSet.getOnExitAction().getValue().isEmpty()
                && !executionSet.getOnExitAction().getValue().getValues().isEmpty()
                && !executionSet.getOnExitAction().getValue().getValues().get(0).getScript().isEmpty()) {
            ScriptTypeValue value = executionSet.getOnExitAction().getValue().getValues().get(0);
            OnExitScript exitScript = new OnExitScript(value.getLanguage(), value.getScript());
            elements.setOnExitScript(exitScript);
        }

        return elements.getMetaData().isEmpty() ? null : elements;
    }

    public List<ItemDefinition> getItemDefinitions() {
        itemDefinitions = new ArrayList<>();
        itemDefinitions.add(new ItemDefinition("_" + getId() + "_SkippableInputXItem", "Object"));
        itemDefinitions.add(new ItemDefinition("_" + getId() + "_PriorityInputXItem", "Object"));
        itemDefinitions.add(new ItemDefinition("_" + getId() + "_CommentInputXItem", "Object"));
        itemDefinitions.add(new ItemDefinition("_" + getId() + "_DescriptionInputXItem", "Object"));
        itemDefinitions.add(new ItemDefinition("_" + getId() + "_CreatedByInputXItem", "Object"));
        itemDefinitions.add(new ItemDefinition("_" + getId() + "_TaskNameInputXItem", "Object"));
        itemDefinitions.add(new ItemDefinition("_" + getId() + "_GroupIdInputXItem", "Object"));
        itemDefinitions.add(new ItemDefinition("_" + getId() + "_ContentInputXItem", "Object"));
        itemDefinitions.add(new ItemDefinition("_" + getId() + "_NotStartedReassignInputXItem", "Object"));
        itemDefinitions.add(new ItemDefinition("_" + getId() + "_NotCompletedReassignInputXItem", "Object"));
        itemDefinitions.add(new ItemDefinition("_" + getId() + "_NotStartedNotifyInputXItem", "Object"));
        itemDefinitions.add(new ItemDefinition("_" + getId() + "_NotCompletedNotifyInputXItem", "Object"));

        if (executionSet.getIsMultipleInstance().getValue()) {
            String[] inVar = executionSet.getMultipleInstanceDataInput().getValue().split(":");
            String[] outVar = executionSet.getMultipleInstanceDataOutput().getValue().split(":");
            itemDefinitions.add(new ItemDefinition(getId() + "_multiInstanceItemType_" + inVar[0], inVar[1]));
            itemDefinitions.add(new ItemDefinition(getId() + "_multiInstanceItemType_" + outVar[0], outVar[1]));
        }

        if (!executionSet.getAssignmentsinfo().getValue().isEmpty()) {
            String wholeAssignments = executionSet.getAssignmentsinfo().getValue();
            String inputVariables = AssignmentParser.getInputAssignmentVariables(wholeAssignments);
            String[] inVars = inputVariables.split(",");
            for (String inVar : inVars) {
                String[] var = inVar.split(":");
                String name = var[0];
                String type = (var.length > 1 && var[1] != null && !var[1].isEmpty()) ? var[1] : "Object";
                itemDefinitions.add(new ItemDefinition("_" + getId() + "_" + name + "InputXItem", type));
            }

            String outputVariables = AssignmentParser.getOutputAssignmentVariables(wholeAssignments);
            String[] outVars = outputVariables.split(",");
            for (String outVar : outVars) {
                String[] var = outVar.split(":");
                String name = var[0];
                String type = (var.length > 1 && var[1] != null && !var[1].isEmpty()) ? var[1] : "Object";
                itemDefinitions.add(new ItemDefinition("_" + getId() + "_" + name + "OutputXItem", type));
            }
        }

        return itemDefinitions;
    }

    public void setItemDefinitions(List<ItemDefinition> itemDefinitions) {
        this.itemDefinitions = itemDefinitions;
    }

    public IoSpecification getIoSpecification() {
        if (getId() == null) {
            return null;
        }
        ioSpecification = new IoSpecification();

        DataInput inputTaskName = createDataInput("TaskName");
        ioSpecification.getDataInput().add(inputTaskName);
        ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(inputTaskName.getId()));

        DataInput inputSkippable = createDataInput("Skippable");
        ioSpecification.getDataInput().add(inputSkippable);
        ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(inputSkippable.getId()));

        if (!executionSet.getContent().getValue().isEmpty()) {
            DataInput content = createDataInput("Content");
            ioSpecification.getDataInput().add(content);
            ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(content.getId()));
        }

        if (!executionSet.getSubject().getValue().isEmpty()) {
            DataInput comment = createDataInput("Comment");
            ioSpecification.getDataInput().add(comment);
            ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(comment.getId()));
        }

        if (!executionSet.getGroupid().getValue().isEmpty()) {
            DataInput groups = createDataInput("GroupId");
            ioSpecification.getDataInput().add(groups);
            ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(groups.getId()));
        }

        if (!executionSet.getGroupid().getValue().isEmpty()) {
            DataInput priority = createDataInput("Priority");
            ioSpecification.getDataInput().add(priority);
            ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(priority.getId()));
        }

        if (!executionSet.getDescription().getValue().isEmpty()) {
            DataInput description = createDataInput("Description");
            ioSpecification.getDataInput().add(description);
            ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(description.getId()));
        }

        if (!executionSet.getCreatedBy().getValue().isEmpty()) {
            DataInput createdBy = createDataInput("CreatedBy");
            ioSpecification.getDataInput().add(createdBy);
            ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(createdBy.getId()));
        }

        if (!executionSet.getReassignmentsInfo().getValue().getValues().isEmpty()) {
            List<ReassignmentValue> values = executionSet.getReassignmentsInfo().getValue().getValues();
            for (ReassignmentValue v : values) {
                if ("NotStartedReassign".equals(v.getType())) {
                    DataInput nsr = createDataInput("NotStartedReassign");
                    ioSpecification.getDataInput().add(nsr);
                    ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(nsr.getId()));
                    break;
                }
            }
            for (ReassignmentValue v : values) {
                if ("NotCompletedReassign".equals(v.getType())) {
                    DataInput ncr = createDataInput("NotCompletedReassign");
                    ioSpecification.getDataInput().add(ncr);
                    ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(ncr.getId()));
                    break;
                }
            }
        }

        if (!executionSet.getNotificationsInfo().getValue().getValues().isEmpty()) {
            List<NotificationValue> values = executionSet.getNotificationsInfo().getValue().getValues();
            for (NotificationValue v : values) {
                if ("NotStartedNotify".equals(v.getType())) {
                    DataInput nsn = createDataInput("NotStartedNotify");
                    ioSpecification.getDataInput().add(nsn);
                    ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(nsn.getId()));
                    break;
                }
            }
            for (NotificationValue v : values) {
                if ("NotCompletedNotify".equals(v.getType())) {
                    DataInput ncn = createDataInput("NotCompletedNotify");
                    ioSpecification.getDataInput().add(ncn);
                    ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(ncn.getId()));
                    break;
                }
            }
        }

        if (!executionSet.getAssignmentsinfo().getValue().isEmpty()) {
            String wholeAssignments = executionSet.getAssignmentsinfo().getValue();

            List<DataInput> dataInputs = AssignmentParser.parseDataInputs(getId(), wholeAssignments);
            for (DataInput dataInput : dataInputs) {
                ioSpecification.getDataInput().add(dataInput);
                ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(dataInput.getId()));
            }

            List<DataOutput> dataOutputs = AssignmentParser.parseDataOutputs(getId(), wholeAssignments);
            for (DataOutput dataOutput : dataOutputs) {
                ioSpecification.getDataOutput().add(dataOutput);
                ioSpecification.getOutputSet().getDataOutputRefs().add(new DataOutputRefs(dataOutput.getId()));
            }
        }

        if (executionSet.getIsMultipleInstance().getValue()) {
            DataInput inputCollection = new DataInput();
            inputCollection.setName("IN_COLLECTION");
            inputCollection.setId(getId() + "_IN_COLLECTIONInputX");
            inputCollection.setItemSubjectRef("_" + executionSet.getMultipleInstanceCollectionInput().getValue() + "Item");
            ioSpecification.getDataInput().add(inputCollection);
            ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(inputCollection.getId()));

            DataInput inputItem = new DataInput();
            inputItem.setName(executionSet.getMultipleInstanceDataInput().getValue().split(":")[0]);
            inputItem.setId(getId() + "_" + executionSet.getMultipleInstanceDataInput().getValue().split(":")[0] + "InputX");
            inputItem.setItemSubjectRef(getId() + "_multiInstanceItemType_" + executionSet.getMultipleInstanceDataInput().getValue().split(":")[0]);
            ioSpecification.getDataInput().add(inputItem);
            ioSpecification.getInputSet().getDataInputRefs().add(new DataInputRefs(inputItem.getId()));

            DataOutput outputCollection = new DataOutput();
            outputCollection.setName("OUT_COLLECTION");
            outputCollection.setId(getId() + "_OUT_COLLECTIONInputX");
            outputCollection.setItemSubjectRef("_" + executionSet.getMultipleInstanceCollectionOutput().getValue() + "Item");
            ioSpecification.getDataOutput().add(outputCollection);
            ioSpecification.getOutputSet().getDataOutputRefs().add(new DataOutputRefs(outputCollection.getId()));

            DataOutput outputItem = new DataOutput();
            outputItem.setName(executionSet.getMultipleInstanceDataOutput().getValue().split(":")[0]);
            outputItem.setId(getId() + "_" + executionSet.getMultipleInstanceDataOutput().getValue().split(":")[0] + "OutputX");
            outputItem.setItemSubjectRef(getId() + "_multiInstanceItemType_" + executionSet.getMultipleInstanceDataOutput().getValue().split(":")[0]);
            ioSpecification.getDataOutput().add(outputItem);
            ioSpecification.getOutputSet().getDataOutputRefs().add(new DataOutputRefs(outputItem.getId()));
        }

        return ioSpecification;
    }

    private DataInput createDataInput(String name) {
        return createDataInput(name, "Object");
    }

    private DataInput createDataInput(String name, String type) {
        DataInput dataInput = new DataInput();
        dataInput.setDtype(type);
        dataInput.setName(name);
        dataInput.setId(getId() + "_" + name + "InputX");
        dataInput.setItemSubjectRef("_" + dataInput.getId() + "Item");

        return dataInput;
    }

    public void setIoSpecification(IoSpecification ioSpecification) {
        this.ioSpecification = ioSpecification;
    }

    public List<DataInputAssociation> getDataInputAssociation() {
        dataInputAssociation = new ArrayList<>();

        // Can happen on designer startup during CDI proxies creation phase
        if (getId() != null) {
            DataInputAssociation taskNameAssociation = new DataInputAssociation();
            taskNameAssociation.getTargetRef().setValue(getId() + "_TaskNameInputX");
            taskNameAssociation.getAssignment().setFrom(new From(executionSet.getTaskName().getValue()));
            taskNameAssociation.getAssignment().setTo(new To(getId() + "_TaskNameInputX"));
            dataInputAssociation.add(taskNameAssociation);

            DataInputAssociation skippableAssociation = new DataInputAssociation();
            skippableAssociation.getTargetRef().setValue(getId() + "_SkippableInputX");
            skippableAssociation.getAssignment().setFrom(new From(executionSet.getSkippable().getValue().toString()));
            skippableAssociation.getAssignment().setTo(new To(getId() + "_SkippableInputX"));
            dataInputAssociation.add(skippableAssociation);

            if (!executionSet.getContent().getValue().isEmpty()) {
                DataInputAssociation content = new DataInputAssociation();
                content.getTargetRef().setValue(getId() + "_ContentInputX");
                content.getAssignment().setFrom(new From(executionSet.getContent().getValue()));
                content.getAssignment().setTo(new To(getId() + "_ContentInputX"));
                dataInputAssociation.add(content);
            }

            if (!executionSet.getSubject().getValue().isEmpty()) {
                DataInputAssociation comment = new DataInputAssociation();
                comment.getTargetRef().setValue(getId() + "_CommentInputX");
                comment.getAssignment().setFrom(new From(executionSet.getSubject().getValue()));
                comment.getAssignment().setTo(new To(getId() + "_CommentInputX"));
                dataInputAssociation.add(comment);
            }

            if (!executionSet.getGroupid().getValue().isEmpty()) {
                DataInputAssociation groups = new DataInputAssociation();
                groups.getTargetRef().setValue(getId() + "_GroupIdInputX");
                groups.getAssignment().setFrom(new From(executionSet.getGroupid().getValue()));
                groups.getAssignment().setTo(new To(getId() + "_GroupIdInputX"));
                dataInputAssociation.add(groups);
            }

            if (!executionSet.getPriority().getValue().isEmpty()) {
                DataInputAssociation priority = new DataInputAssociation();
                priority.getTargetRef().setValue(getId() + "_PriorityInputX");
                priority.getAssignment().setFrom(new From(executionSet.getPriority().getValue()));
                priority.getAssignment().setTo(new To(getId() + "_PriorityInputX"));
                dataInputAssociation.add(priority);
            }

            if (!executionSet.getDescription().getValue().isEmpty()) {
                DataInputAssociation description = new DataInputAssociation();
                description.getTargetRef().setValue(getId() + "_DescriptionInputX");
                description.getAssignment().setFrom(new From(executionSet.getDescription().getValue()));
                description.getAssignment().setTo(new To(getId() + "_DescriptionInputX"));
                dataInputAssociation.add(description);
            }

            if (!executionSet.getCreatedBy().getValue().isEmpty()) {
                DataInputAssociation createdBy = new DataInputAssociation();
                createdBy.getTargetRef().setValue(getId() + "_CreatedByInputX");
                createdBy.getAssignment().setFrom(new From(executionSet.getCreatedBy().getValue()));
                createdBy.getAssignment().setTo(new To(getId() + "_CreatedByInputX"));
                dataInputAssociation.add(createdBy);
            }

            List<ReassignmentValue> values = executionSet.getReassignmentsInfo().getValue().getValues();
            if (!values.isEmpty()) {
                String notStartedReassign = "";
                String notCompletedReassign = "";
                for (ReassignmentValue v : values) {
                    if ("NotStartedReassign".equals(v.getType())) {
                        notStartedReassign += v.toCDATAFormat() + "^";
                    }
                    if ("NotCompletedReassign".equals(v.getType())) {
                        notCompletedReassign += v.toCDATAFormat() + "^";
                    }
                }
                if (!notStartedReassign.isEmpty()) {
                    notStartedReassign = notStartedReassign.substring(0, notStartedReassign.lastIndexOf("^"));
                    DataInputAssociation nsr = new DataInputAssociation();
                    nsr.getTargetRef().setValue(getId() + "_NotStartedReassignInputX");
                    nsr.getAssignment().setFrom(new From(notStartedReassign));
                    nsr.getAssignment().setTo(new To(getId() + "_NotStartedReassignInputX"));
                    dataInputAssociation.add(nsr);
                }
                if (!notCompletedReassign.isEmpty()) {
                    notCompletedReassign = notCompletedReassign.substring(0, notCompletedReassign.lastIndexOf("^"));
                    DataInputAssociation ncr = new DataInputAssociation();
                    ncr.getTargetRef().setValue(getId() + "_NotCompletedReassignInputX");
                    ncr.getAssignment().setFrom(new From(notCompletedReassign));
                    ncr.getAssignment().setTo(new To(getId() + "_NotCompletedReassignInputX"));
                    dataInputAssociation.add(ncr);
                }
            }

            List<NotificationValue> nValues = executionSet.getNotificationsInfo().getValue().getValues();
            if (!nValues.isEmpty()) {
                String notStartedNotify = "";
                String notCompletedNotify = "";
                for (NotificationValue v : nValues) {
                    if ("NotStartedNotify".equals(v.getType())) {
                        notStartedNotify += v.toCDATAFormat() + "^";
                    }
                    if ("NotCompletedNotify".equals(v.getType())) {
                        notCompletedNotify += v.toCDATAFormat() + "^";
                    }
                }
                if (!notStartedNotify.isEmpty()) {
                    notStartedNotify = notStartedNotify.substring(0, notStartedNotify.lastIndexOf("^"));
                    DataInputAssociation nsn = new DataInputAssociation();
                    nsn.getTargetRef().setValue(getId() + "_NotStartedNotifyInputX");
                    nsn.getAssignment().setFrom(new From(notStartedNotify));
                    nsn.getAssignment().setTo(new To(getId() + "_NotStartedNotifyInputX"));
                    dataInputAssociation.add(nsn);
                }
                if (!notCompletedNotify.isEmpty()) {
                    notCompletedNotify = notCompletedNotify.substring(0, notCompletedNotify.lastIndexOf("^"));
                    DataInputAssociation ncn = new DataInputAssociation();
                    ncn.getTargetRef().setValue(getId() + "_NotCompletedNotifyInputX");
                    ncn.getAssignment().setFrom(new From(notCompletedNotify));
                    ncn.getAssignment().setTo(new To(getId() + "_NotCompletedNotifyInputX"));
                    dataInputAssociation.add(ncn);
                }
            }

            if (!executionSet.getAssignmentsinfo().getValue().isEmpty()) {
                dataInputAssociation.addAll(AssignmentParser.parseDataInputAssociation(getId(),
                                                                                       executionSet.getAssignmentsinfo().getValue()));
            }

            if (executionSet.getIsMultipleInstance().getValue()) {
                DataInputAssociation miInputCollection = new DataInputAssociation(getId() + "_IN_COLLECTIONInputX", executionSet.getMultipleInstanceCollectionInput().getValue());
                dataInputAssociation.add(miInputCollection);

                DataInputAssociation miInputAssociation = new DataInputAssociation(getId() + "_" + executionSet.getMultipleInstanceDataInput().getValue().split(":")[0] + "InputX",
                                                                                   executionSet.getMultipleInstanceDataInput().getValue().split(":")[0]);
                dataInputAssociation.add(miInputAssociation);
            }
        }

        return dataInputAssociation;
    }

    public void setDataInputAssociation(List<DataInputAssociation> dataInputAssociation) {
        this.dataInputAssociation = dataInputAssociation;
    }

    public List<DataOutputAssociation> getDataOutputAssociation() {
        dataOutputAssociation = new ArrayList<>();

        if (executionSet.getIsMultipleInstance().getValue()) {
            DataOutputAssociation miOutCollection = new DataOutputAssociation(getId() + "_OUT_COLLECTIONOutputX", executionSet.getMultipleInstanceCollectionOutput().getValue());
            dataOutputAssociation.add(miOutCollection);

            DataOutputAssociation miOutputAssociation = new DataOutputAssociation(getId() + "_" + executionSet.getMultipleInstanceDataOutput().getValue().split(":")[0] + "OutputX",
                                                                                  executionSet.getMultipleInstanceDataOutput().getValue().split(":")[0]);
            dataOutputAssociation.add(miOutputAssociation);
        }

        if (!executionSet.getAssignmentsinfo().getValue().isEmpty()) {
            dataOutputAssociation.addAll(AssignmentParser.parseDataOutputAssociation(getId(),
                                                                                     executionSet.getAssignmentsinfo().getValue()));
        }

        return dataOutputAssociation;
    }

    public void setDataOutputAssociation(List<DataOutputAssociation> dataOutputAssociation) {
        this.dataOutputAssociation = dataOutputAssociation;
    }

    public List<PotentialOwner> getPotentialOwners() {
        potentialOwners = new ArrayList<>();

        String value = executionSet.getActors().getValue();
        if (value == null || value.isEmpty()) {
            return potentialOwners;
        }

        String[] owners = value.split(",");
        for (String owner : owners) {
            potentialOwners.add(new PotentialOwner(owner));
        }

        return potentialOwners;
    }

    public void setPotentialOwners(List<PotentialOwner> potentialOwners) {
        this.potentialOwners = potentialOwners;
    }

    public MultiInstanceLoopCharacteristics getMultiInstanceLoopCharacteristics() {
        if (getId() == null || !executionSet.getIsMultipleInstance().getValue()) {
            return null;
        }

        multiInstanceLoopCharacteristics = new MultiInstanceLoopCharacteristics();
        multiInstanceLoopCharacteristics.setLoopDataInputRef(getId() + "_IN_COLLECTIONInputX");
        multiInstanceLoopCharacteristics.setLoopDataOutputRef(getId() + "_OUT_COLLECTIONOutputX");
        multiInstanceLoopCharacteristics.getCompletionCondition().setValue(executionSet.getMultipleInstanceCompletionCondition().getValue());
        multiInstanceLoopCharacteristics.setInputDataItem(new InputDataItem(
                executionSet.getMultipleInstanceDataInput().getValue().split(":")[0],
                getId() + "_multiInstanceItemType_" + executionSet.getMultipleInstanceDataInput().getValue().split(":")[0],
                executionSet.getMultipleInstanceDataInput().getValue().split(":")[0]
        ));
        multiInstanceLoopCharacteristics.setOutputDataItem(new OutputDataItem(
                executionSet.getMultipleInstanceDataOutput().getValue().split(":")[0],
                getId() + "_multiInstanceItemType_" + executionSet.getMultipleInstanceDataOutput().getValue().split(":")[0],
                executionSet.getMultipleInstanceDataOutput().getValue().split(":")[0]
        ));

        return null;
    }

    public void setMultiInstanceLoopCharacteristics(MultiInstanceLoopCharacteristics multiInstanceLoopCharacteristics) {
        this.multiInstanceLoopCharacteristics = multiInstanceLoopCharacteristics;
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
