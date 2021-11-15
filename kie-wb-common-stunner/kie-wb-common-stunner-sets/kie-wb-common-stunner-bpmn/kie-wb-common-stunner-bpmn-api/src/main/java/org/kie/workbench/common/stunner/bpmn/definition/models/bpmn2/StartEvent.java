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
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.validation.Valid;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlCData;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.forms.adf.definitions.annotations.metaModel.FieldValue;
import org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textArea.type.TextAreaFieldType;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNCategories;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNViewDefinition;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.ElementParameters;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.NormalDistribution;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.ProcessingTime;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.TimeParameters;
import org.kie.workbench.common.stunner.bpmn.definition.models.drools.MetaData;
import org.kie.workbench.common.stunner.bpmn.definition.property.dataio.DataIOModel;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.AdvancedData;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Category;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Labels;
import org.kie.workbench.common.stunner.core.definition.annotation.morph.MorphBase;
import org.kie.workbench.common.stunner.core.definition.annotation.property.Value;
import org.kie.workbench.common.stunner.core.definition.property.PropertyMetaTypes;
import org.kie.workbench.common.stunner.core.util.HashUtil;
import org.kie.workbench.common.stunner.core.util.StringUtils;

@MorphBase(defaultType = StartNoneEvent.class)
public abstract class StartEvent implements BPMNViewDefinition,
                                            DataIOModel {

    @Category
    @XmlTransient
    public static final transient String category = BPMNCategories.START_EVENTS;

    @Labels
    @XmlTransient
    protected final Set<String> labels = new HashSet<>();

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

    @Property
    @FormField(
            afterElement = "simulationSet"
    )
    @Valid
    @XmlTransient
    protected AdvancedData advancedData;

    /*
    Used only for marshalling/unmarshalling purposes. Shouldn't be handled in Equals/HashCode.
    This variable will be always null and getter/setter will return data from other Execution sets.
    Execution sets not removed due to how forms works now, should be refactored during the migration
    to the new forms.
     */
    @XmlElement
    private ExtensionElements extensionElements;

    /*
    Simulation parameters for the start event. Used in Simulation section during marshalling/unmarshalling
    This parameter currently not changed by Stunner but can preserve users values.
    Shouldn't be handled in Equals/HashCode.
     */
    @XmlTransient
    private ElementParameters elementParameters = new ElementParameters();

    public StartEvent() {
        initLabels();
    }

    public StartEvent(final String name,
                      final String documentation,
                      final AdvancedData advancedData) {
        this();
        this.name = name;
        this.documentation = documentation;
        this.advancedData = advancedData;
    }

    protected void initLabels() {
        labels.add("all");
        labels.add("lane_child");
        labels.add("Startevents_all");
        labels.add("Startevents_outgoing_all");
        labels.add("sequence_start");
        labels.add("choreography_sequence_start");
        labels.add("to_task_event");
        labels.add("from_task_event");
        labels.add("fromtoall");
        labels.add("StartEventsMorph");
        labels.add("cm_nop");
    }

    @Override
    public boolean hasInputVars() {
        return false;
    }

    @Override
    public boolean isSingleInputVar() {
        return false;
    }

    @Override
    public boolean hasOutputVars() {
        return false;
    }

    @Override
    public boolean isSingleOutputVar() {
        return false;
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

    public String getCategory() {
        return category;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public AdvancedData getAdvancedData() {
        return advancedData;
    }

    public void setAdvancedData(AdvancedData advancedData) {
        this.advancedData = advancedData;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
        this.elementParameters.setElementRef(id);
    }

    public ElementParameters getElementParameters() {
        if (elementParameters.getTimeParameters() == null) {
            elementParameters.setTimeParameters(
                    new TimeParameters(
                            new ProcessingTime(
                                    new NormalDistribution()
                            )
                    )
            );
        }
        return elementParameters;
    }

    public void setElementParameters(ElementParameters elementParameters) {
        this.elementParameters = elementParameters;
    }

    /*
        Used only for marshalling/unmarshalling purposes. Shouldn't be handled in Equals/HashCode.
        Execution sets not removed due to how forms works now, should be refactored during the migration
        to the new forms.
         */
    public ExtensionElements getExtensionElements() {
        ExtensionElements elements = new ExtensionElements();
        List<MetaData> metaData = new ArrayList<>();
        if (StringUtils.nonEmpty(this.getName())) {
            MetaData name = new MetaData("elementname", this.getName());
            metaData.add(name);
        }

        String mData = this.getAdvancedData().getMetaDataAttributes();
        if (StringUtils.nonEmpty(mData)) {
            String[] metaArray = mData.split("Ø");
            for (String md : metaArray) {
                String[] metaNV = md.split("ß");
                MetaData meta = new MetaData(metaNV[0], metaNV[1]);
                metaData.add(meta);
            }
        }

        elements.setMetaData(metaData);
        return elements;
    }

    /*
    Used only for marshalling/unmarshalling purposes. Shouldn't be handled in Equals/HashCode.
    Execution sets not removed due to how forms works now, should be refactored during the migration
    to the new forms.
     */
    public void setExtensionElements(ExtensionElements extensionElements) {
        this.extensionElements = extensionElements;
    }

    /*
    ID used during marshalling/unmarshalling only and not a part of the hashCode function
     */
    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(getClass()),
                                         Objects.hashCode(name),
                                         Objects.hashCode(documentation),
                                         Objects.hashCode(advancedData),
                                         Objects.hashCode(labels));
    }

    /*
    ID used during marshalling/unmarshalling only and not a part of the equals function
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof StartEvent) {
            StartEvent other = (StartEvent) o;
            return Objects.equals(name, other.name) &&
                    Objects.equals(documentation, other.documentation) &&
                    Objects.equals(advancedData, other.advancedData) &&
                    Objects.equals(labels, other.labels);
        }
        return false;
    }
}
