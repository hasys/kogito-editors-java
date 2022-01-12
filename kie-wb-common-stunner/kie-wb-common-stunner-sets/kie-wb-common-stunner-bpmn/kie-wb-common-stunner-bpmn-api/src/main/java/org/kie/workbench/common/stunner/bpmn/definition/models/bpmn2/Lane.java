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
import org.kie.workbench.common.forms.adf.definitions.annotations.metaModel.FieldValue;
import org.kie.workbench.common.forms.adf.definitions.settings.FieldPolicy;
import org.kie.workbench.common.forms.fields.shared.fieldTypes.basic.textArea.type.TextAreaFieldType;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNCategories;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNViewDefinition;
import org.kie.workbench.common.stunner.bpmn.definition.models.drools.MetaData;
import org.kie.workbench.common.stunner.bpmn.definition.property.background.BackgroundSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.dimensions.RectangleDimensionsSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.font.FontSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.AdvancedData;
import org.kie.workbench.common.stunner.core.definition.annotation.Definition;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Category;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Labels;
import org.kie.workbench.common.stunner.core.definition.annotation.property.Value;
import org.kie.workbench.common.stunner.core.rule.annotation.CanContain;
import org.kie.workbench.common.stunner.core.util.HashUtil;
import org.kie.workbench.common.stunner.core.util.StringUtils;
import org.treblereel.gwt.xml.mapper.api.annotation.XmlUnwrappedCollection;

import static org.kie.workbench.common.forms.adf.engine.shared.formGeneration.processing.fields.fieldInitializers.nestedForms.SubFormFieldInitializer.COLLAPSIBLE_CONTAINER;
import static org.kie.workbench.common.forms.adf.engine.shared.formGeneration.processing.fields.fieldInitializers.nestedForms.SubFormFieldInitializer.FIELD_CONTAINER_PARAM;

@Portable
@Bindable
@Definition
@CanContain(roles = {"lane_child"})
@FormDefinition(
        startElement = "name",
        policy = FieldPolicy.ONLY_MARKED,
        defaultFieldSettings = {@FieldParam(name = FIELD_CONTAINER_PARAM, value = COLLAPSIBLE_CONTAINER)}
)
@XmlRootElement(name = "lane", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class Lane implements BPMNViewDefinition {

    @Category
    public static final transient String category = BPMNCategories.CONTAINERS;

    @Valid
    @Property
    @Value
    @FieldValue
    @NotNull
    @NotEmpty
    @XmlAttribute
    @FormField(type = TextAreaFieldType.class)
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
    @XmlAttribute(namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
    private String documentation;

    @XmlAttribute
    private String id;

    @Property
    @Valid
    @XmlTransient
    protected BackgroundSet backgroundSet;

    @Property
    @XmlTransient
    private FontSet fontSet;

    @Property
    @XmlTransient
    protected RectangleDimensionsSet dimensionsSet;

    @Property
    @FormField(
            afterElement = "dimensionSet"
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

    @XmlUnwrappedCollection
    @XmlElement(name = "flowNodeRef")
    private List<FlowNodeRef> flowNodeRef = new ArrayList<>();

    @Labels
    private final Set<String> labels = new Sets.Builder<String>()
            .add("all")
            .add("PoolChild")
            .add("fromtoall")
            .add("canContainArtifacts")
            .add("cm_nop")
            .build();

    public Lane() {
        this("Lane",
             "",
             new BackgroundSet(),
             new FontSet(),
             new RectangleDimensionsSet(),
             new AdvancedData());
    }

    public Lane(final @MapsTo("name") String name,
                final @MapsTo("documentation") String documentation,
                final @MapsTo("backgroundSet") BackgroundSet backgroundSet,
                final @MapsTo("fontSet") FontSet fontSet,
                final @MapsTo("dimensionsSet") RectangleDimensionsSet dimensionsSet,
                final @MapsTo("advancedData") AdvancedData advancedData) {
        this.name = name;
        this.documentation = documentation;
        this.backgroundSet = backgroundSet;
        this.fontSet = fontSet;
        this.dimensionsSet = dimensionsSet;
        this.advancedData = advancedData;
    }

    public String getCategory() {
        return category;
    }

    public Set<String> getLabels() {
        return labels;
    }

    public BackgroundSet getBackgroundSet() {
        return backgroundSet;
    }

    public FontSet getFontSet() {
        return fontSet;
    }

    public void setFontSet(final FontSet fontSet) {
        this.fontSet = fontSet;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDocumentation() {
        return documentation;
    }

    public void setDocumentation(String documentation) {
        this.documentation = documentation;
    }

    public void setBackgroundSet(final BackgroundSet backgroundSet) {
        this.backgroundSet = backgroundSet;
    }

    public RectangleDimensionsSet getDimensionsSet() {
        return dimensionsSet;
    }

    public void setDimensionsSet(final RectangleDimensionsSet dimensionsSet) {
        this.dimensionsSet = dimensionsSet;
    }

    public AdvancedData getAdvancedData() {
        return advancedData;
    }

    public void setAdvancedData(AdvancedData advancedData) {
        this.advancedData = advancedData;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    /*
Used only for marshalling/unmarshalling purposes. Shouldn't be handled in Equals/HashCode.
Execution sets not removed due to how forms works now, should be refactored during the migration
to the new forms.
 */
    public ExtensionElements getExtensionElements() {
        ExtensionElements elements = new ExtensionElements();
        List<MetaData> metaData = new ArrayList<>();
        elements.setMetaData(metaData);

        if (StringUtils.nonEmpty(this.getName())) {
            MetaData name = new MetaData("elementname", this.getName());
            metaData.add(name);
        }

        metaData.addAll(this.getAdvancedData().getAsMetaData());

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

    public List<FlowNodeRef> getFlowNodeRef() {
        return flowNodeRef;
    }

    public void setFlowNodeRef(List<FlowNodeRef> flowNodeRef) {
        this.flowNodeRef = flowNodeRef;
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(name.hashCode(),
                                         documentation.hashCode(),
                                         backgroundSet.hashCode(),
                                         fontSet.hashCode(),
                                         dimensionsSet.hashCode(),
                                         advancedData.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Lane) {
            Lane other = (Lane) o;
            return Objects.equals(name, other.name) &&
                    Objects.equals(documentation, other.documentation) &&
                    Objects.equals(backgroundSet, other.backgroundSet) &&
                    Objects.equals(fontSet, other.fontSet) &&
                    Objects.equals(dimensionsSet, other.dimensionsSet) &&
                    Objects.equals(advancedData, other.advancedData);
        }
        return false;
    }
}
