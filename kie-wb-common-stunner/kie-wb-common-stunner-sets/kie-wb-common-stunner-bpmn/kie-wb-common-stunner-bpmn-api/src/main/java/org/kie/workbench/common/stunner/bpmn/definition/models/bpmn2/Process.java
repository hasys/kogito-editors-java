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
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlCData;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.validator.constraints.NotEmpty;
import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.soup.commons.util.Sets;
import org.kie.workbench.common.forms.adf.definitions.annotations.FieldParam;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormDefinition;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.forms.adf.definitions.annotations.field.selector.SelectorDataProvider;
import org.kie.workbench.common.forms.adf.definitions.annotations.metaModel.FieldValue;
import org.kie.workbench.common.forms.adf.definitions.settings.FieldPolicy;
import org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.selectors.listBox.type.ListBoxFieldType;
import org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textArea.type.TextAreaFieldType;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNCategories;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNDiagram;
import org.kie.workbench.common.stunner.bpmn.definition.models.drools.MetaData;
import org.kie.workbench.common.stunner.bpmn.definition.property.background.BackgroundSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.cm.CaseManagementSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.diagram.DiagramSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.diagram.imports.Imports;
import org.kie.workbench.common.stunner.bpmn.definition.property.dimensions.RectangleDimensionsSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.font.FontSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.ProcessData;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.RootProcessAdvancedData;
import org.kie.workbench.common.stunner.bpmn.forms.model.ImportsFieldType;
import org.kie.workbench.common.stunner.core.definition.annotation.Definition;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Category;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Labels;
import org.kie.workbench.common.stunner.core.definition.annotation.property.Value;
import org.kie.workbench.common.stunner.core.rule.annotation.CanContain;
import org.kie.workbench.common.stunner.core.util.HashUtil;
import org.treblereel.gwt.xml.mapper.api.annotation.XmlUnwrappedCollection;

import static org.kie.workbench.common.forms.adf.engine.shared.formGeneration.processing.fields.fieldInitializers.nestedForms.SubFormFieldInitializer.COLLAPSIBLE_CONTAINER;
import static org.kie.workbench.common.forms.adf.engine.shared.formGeneration.processing.fields.fieldInitializers.nestedForms.SubFormFieldInitializer.FIELD_CONTAINER_PARAM;

@Portable
@Bindable
@Definition()
@CanContain(roles = {"all"})
@FormDefinition(
        startElement = "caseManagementSet",
        policy = FieldPolicy.ONLY_MARKED,
        defaultFieldSettings = {@FieldParam(name = FIELD_CONTAINER_PARAM, value = COLLAPSIBLE_CONTAINER)}
)
@XmlRootElement(name = "process", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class Process implements BPMNDiagram<DiagramSet, ProcessData, RootProcessAdvancedData> {

    @Category
    public static final transient String category = BPMNCategories.CONTAINERS;
    public static final String ADHOC = "adHoc";
    public static final String PROCESS_DATA = "processData";
    public static final String ADVANCED_DATA = "advancedData";
    public static final String CASE_MANAGEMENT_SET = "caseManagementSet";

    @Valid
    @Value
    @FieldValue
    @NotNull
    @NotEmpty
    @Property
    @XmlAttribute
    @FormField(afterElement = "caseManagementSet")
    private String name;

    @Property
    @Valid
    @Value
    @FieldValue
    @FormField(
            type = TextAreaFieldType.class,
            afterElement = "name"
    )
    @XmlCData
    private String documentation;

    @Property
    @Valid
    @Value
    @NotNull
    @NotEmpty
    @FieldValue
    @XmlAttribute
    @FormField(afterElement = "documentation")
    private String id;

    @Property
    @Valid
    @Value
    @FieldValue
    @NotNull
    @NotEmpty
    @FormField(afterElement = "id")
    @XmlAttribute(namespace = "http://www.jboss.org/drools")
    private String packageName;

    @Property
    @Valid
    @Value
    @NotNull
    @NotEmpty
    @FieldValue
    @XmlAttribute
    @FormField(
            type = ListBoxFieldType.class,
            settings = {@FieldParam(name = "addEmptyOption", value = "false")},
            afterElement = "packageName"
    )
    @SelectorDataProvider(
            type = SelectorDataProvider.ProviderType.CLIENT,
            className = "org.kie.workbench.common.stunner.bpmn.client.dataproviders.ProcessTypeProvider"
    )
    private String processType;

    @Property
    @Valid
    @Value
    @FieldValue
    @NotNull
    @NotEmpty
    @FormField(afterElement = "processType")
    @XmlAttribute(namespace = "http://www.jboss.org/drools")
    private String version;

    @Property
    @Valid
    @Value
    @FieldValue
    @FormField(afterElement = "version")
    @XmlAttribute(namespace = "http://www.jboss.org/drools")
    private Boolean adHoc;

    @Property
    @Valid
    @Value
    @FieldValue
    @FormField(afterElement = ADHOC)
    @XmlTransient
    private String processInstanceDescription;

    @Property
    @Valid
    @XmlTransient
    @FormField(
            afterElement = "processInstanceDescription",
            type = ImportsFieldType.class
    )
    private Imports imports;

    @Property
    @Value
    @FieldValue
    @FormField(afterElement = "imports")
    @XmlAttribute(name = "isExecutable")
    private Boolean executable;

    @Property
    @Value
    @FieldValue
    @FormField(afterElement = "executable")
    private String slaDueDate;

    @Property
    @Valid
    @XmlTransient
    @FormField(afterElement = "slaDueDate")
    protected ProcessData processData;

    @Property
    @Valid
    @XmlTransient
    @FormField(afterElement = PROCESS_DATA)
    protected RootProcessAdvancedData advancedData;

    @Property
    @FormField
    @XmlTransient
    protected CaseManagementSet caseManagementSet;

    @Property
    @XmlTransient
    private BackgroundSet backgroundSet;

    @Property
    @XmlTransient
    private FontSet fontSet;

    @Property
    @XmlTransient
    protected RectangleDimensionsSet dimensionsSet;

    @Labels
    private final Set<String> labels = new Sets.Builder<String>()
            .add("canContainArtifacts")
            .add("diagram")
            .build();

    public static final Double WIDTH = 950d;
    public static final Double HEIGHT = 950d;

    @XmlElement(name = "startEvent")
    @XmlUnwrappedCollection
    @XmlElements({
            @XmlElement(name = "_StartNoneEvent", type = StartNoneEvent.class),
            @XmlElement(name = "_StartCompensationEvent", type = StartCompensationEvent.class),
            @XmlElement(name = "_StartMessageEvent", type = StartMessageEvent.class),
            @XmlElement(name = "_StartSignalEvent", type = StartSignalEvent.class),
            @XmlElement(name = "_StartTimerEvent", type = StartTimerEvent.class),
            @XmlElement(name = "_StartEscalationEvent", type = StartEscalationEvent.class),
            @XmlElement(name = "_StartErrorEvent", type = StartErrorEvent.class),
            @XmlElement(name = "_StartConditionalEvent", type = StartConditionalEvent.class)
    })
    private List<StartEvent> startEvents = new ArrayList<>();

    @XmlElement(name = "endEvent")
    @XmlUnwrappedCollection
    @XmlElements({
            @XmlElement(name = "_EndNoneEvent", type = EndNoneEvent.class),
            @XmlElement(name = "_EndTerminateEvent", type = EndTerminateEvent.class)
    })
    private List<EndEvent> endEvents = new ArrayList<>();

    @XmlElement(name = "sequenceFlow")
    @XmlUnwrappedCollection
    private List<SequenceFlow> sequenceFlows = new ArrayList<>();

    @XmlElement(name = "task")
    @XmlUnwrappedCollection
    @XmlElements({
            @XmlElement(name = "_NoneTask", type = NoneTask.class),
            @XmlElement(name = "_UserTask", type = UserTask.class)
    })
    private List<BaseTask> tasks = new ArrayList<>();

    @XmlElement(name = "scriptTask")
    @XmlUnwrappedCollection
    @XmlElements({
            @XmlElement(name = "_ScriptTask", type = ScriptTask.class)
    })
    private List<BaseTask> scriptTasks = new ArrayList<>();

    @XmlElement(name = "userTask")
    @XmlUnwrappedCollection
    @XmlElements({
            @XmlElement(name = "_UserTask", type = UserTask.class)
    })
    private List<BaseTask> userTasks = new ArrayList<>();

    @XmlElement(name = "property")
    @XmlUnwrappedCollection
    private List<org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Property> properties = new ArrayList<>();

    public Process() {
        this("",
             "",
             "",
             "",
             "",
             "",
             false,
             "",
             new Imports(),
             false,
             "",
             new ProcessData(),
             new CaseManagementSet(),
             new BackgroundSet(),
             new FontSet(),
             new RectangleDimensionsSet(WIDTH,
                                        HEIGHT),
             new RootProcessAdvancedData());
    }

    public Process(final @MapsTo("name") String name,
                   final @MapsTo("documentation") String documentation,
                   final @MapsTo("id") String id,
                   final @MapsTo("packageName") String packageName,
                   final @MapsTo("processType") String processType,
                   final @MapsTo("version") String version,
                   final @MapsTo(ADHOC) boolean adHoc,
                   final @MapsTo("processInstanceDescription") String processInstanceDescription,
                   final @MapsTo("imports") Imports imports,
                   final @MapsTo("executable") boolean executable,
                   final @MapsTo("slaDueDate") String slaDueDate,
                   final @MapsTo(PROCESS_DATA) ProcessData processData,
                   final @MapsTo(CASE_MANAGEMENT_SET) CaseManagementSet caseManagementSet,
                   final @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                   final @MapsTo("fontSet") FontSet fontSet,
                   final @MapsTo("dimensionsSet") RectangleDimensionsSet dimensionsSet,
                   final @MapsTo(ADVANCED_DATA) RootProcessAdvancedData advancedData) {
        this.name = name;
        this.documentation = documentation;
        this.id = id;
        this.packageName = packageName;
        this.processType = processType;
        this.version = version;
        this.adHoc = adHoc;
        this.processInstanceDescription = processInstanceDescription;
        this.imports = imports;
        this.executable = executable;
        this.slaDueDate = slaDueDate;
        this.processData = processData;
        this.caseManagementSet = caseManagementSet;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.dimensionsSet = dimensionsSet;
        this.advancedData = advancedData;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDocumentation() {
        return documentation;
    }

    @Override
    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    @Override
    public String getProcessType() {
        return processType;
    }

    @Override
    public void setProcessType(String processType) {
        this.processType = processType;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public void setVersion(String version) {
        this.version = version;
    }

    public Boolean isAdHoc() {
        return adHoc;
    }

    @Override
    public void setAdHoc(Boolean adHoc) {
        this.adHoc = adHoc;
    }

    @Override
    public String getProcessInstanceDescription() {
        return processInstanceDescription;
    }

    @Override
    public void setProcessInstanceDescription(String processInstanceDescription) {
        this.processInstanceDescription = processInstanceDescription;
    }

    @Override
    public Imports getImports() {
        return imports;
    }

    @Override
    public void setImports(Imports imports) {
        this.imports = imports;
    }

    @Override
    public Boolean isExecutable() {
        return executable;
    }

    @Override
    public void setExecutable(Boolean executable) {
        this.executable = executable;
    }

    @Override
    public String getSlaDueDate() {
        return slaDueDate;
    }

    @Override
    public void setSlaDueDate(String slaDueDate) {
        this.slaDueDate = slaDueDate;
    }

    public String getCategory() {
        return category;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public RectangleDimensionsSet getDimensionsSet() {
        return dimensionsSet;
    }

    public void setDimensionsSet(final RectangleDimensionsSet dimensionsSet) {
        this.dimensionsSet = dimensionsSet;
    }

    @Override
    public ProcessData getProcessData() {
        return processData;
    }

    @Override
    public CaseManagementSet getCaseManagementSet() {
        return caseManagementSet;
    }

    @Override
    public void setCaseManagementSet(final CaseManagementSet caseManagementSet) {
        this.caseManagementSet = caseManagementSet;
    }

    @Override
    public BackgroundSet getBackgroundSet() {
        return backgroundSet;
    }

    @Override
    public FontSet getFontSet() {
        return fontSet;
    }

    @Override
    public void setProcessData(final ProcessData processData) {
        this.processData = processData;
    }

    @Override
    public void setBackgroundSet(final BackgroundSet backgroundSet) {
        this.backgroundSet = backgroundSet;
    }

    @Override
    public void setFontSet(final FontSet fontSet) {
        this.fontSet = fontSet;
    }

    @Override
    public RootProcessAdvancedData getAdvancedData() {
        return advancedData;
    }

    @Override
    public void setAdvancedData(RootProcessAdvancedData advancedData) {
        this.advancedData = advancedData;
    }

    public List<StartEvent> getStartEvents() {
        return startEvents;
    }

    public void setStartEvents(List<StartEvent> startEvents) {
        this.startEvents = startEvents;
    }

    public List<EndEvent> getEndEvents() {
        return endEvents;
    }

    public void setEndEvents(List<EndEvent> endEvents) {
        this.endEvents = endEvents;
    }

    public List<BaseTask> getTasks() {
        return tasks;
    }

    public void setTasks(List<BaseTask> tasks) {
        this.tasks = tasks;
    }

    public List<BaseTask> getScriptTasks() {
        return scriptTasks;
    }

    public void setScriptTasks(List<BaseTask> scriptTasks) {
        this.scriptTasks = scriptTasks;
    }

    public List<BaseTask> getUserTasks() {
        return userTasks;
    }

    public void setUserTasks(List<BaseTask> userTasks) {
        this.userTasks = userTasks;
    }

    public List<SequenceFlow> getSequenceFlows() {
        return sequenceFlows;
    }

    public void setSequenceFlows(List<SequenceFlow> sequenceFlows) {
        this.sequenceFlows = sequenceFlows;
    }

    public List<org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Property> getProperties() {
        String value = getProcessData().getProcessVariables();
        properties = new ArrayList<>();

        if (value == null || value.isEmpty()) {
            return properties;
        }

        // String format of process variables created by forms:
        // varName1:varType1:varTag1;varTag2,varName2:varType2:varTag3;varTag4
        String[] variables = value.split(",");
        for (String variable : variables) {
            if (variable.isEmpty()) {
                continue;
            }

            String[] parts = variable.split(":");
            String varName = (parts.length >= 1 ? parts[0] : "");
            String itemId = "_" + varName + "Item";
            String varType = (parts.length >= 2 ? parts[1] : "Object");
            String varTags = (parts.length >= 3 ? parts[2].replace(';', ',') : null);

            org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Property property = new org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Property(varName, varName, itemId);
            if (varTags != null && !varTags.isEmpty()) {
                ExtensionElements extensionElements = new ExtensionElements();
                extensionElements.addMetaData(new MetaData("customTags", varTags));
                property.setExtensionElements(extensionElements);
            }
            properties.add(property);
            property.setVariableType(varType);
        }

        return properties;
    }

    public void setProperties(List<org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Property> properties) {
        this.properties = properties;
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(processData),
                                         Objects.hashCode(caseManagementSet),
                                         Objects.hashCode(backgroundSet),
                                         Objects.hashCode(fontSet),
                                         Objects.hashCode(dimensionsSet),
                                         Objects.hashCode(advancedData),
                                         Objects.hashCode(startEvents),
                                         Objects.hashCode(endEvents),
                                         Objects.hashCode(tasks),
                                         Objects.hashCode(scriptTasks),
                                         Objects.hashCode(sequenceFlows),
                                         Objects.hashCode(properties));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Process) {
            Process other = (Process) o;
            return Objects.equals(processData, other.processData)
                    && Objects.equals(caseManagementSet, other.caseManagementSet)
                    && Objects.equals(backgroundSet, other.backgroundSet)
                    && Objects.equals(fontSet, other.fontSet)
                    && Objects.equals(dimensionsSet, other.dimensionsSet)
                    && Objects.equals(advancedData, other.advancedData)
                    && Objects.equals(startEvents, other.startEvents)
                    && Objects.equals(endEvents, other.endEvents)
                    && Objects.equals(tasks, other.tasks)
                    && Objects.equals(scriptTasks, other.scriptTasks)
                    && Objects.equals(sequenceFlows, other.sequenceFlows)
                    && Objects.equals(properties, other.properties);
        }
        return false;
    }
}
