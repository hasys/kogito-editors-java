/*
 * Copyright 2021 Red Hat, Inc. and/or its affiliates.
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
import javax.xml.bind.annotation.XmlTransient;

import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.stunner.bpmn.definition.FlowElement;
import org.kie.workbench.common.stunner.bpmn.definition.models.drools.MetaData;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.AdvancedData;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.util.HashUtil;
import org.kie.workbench.common.stunner.core.util.StringUtils;

/**
 * FlowNode is a basic node for all Nodes and Artifacts, contains basic MetaDataExtensions logic.
 */
public abstract class FlowNode extends FlowElement {

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

    public FlowNode() {
    }

    public FlowNode(final String name, final String documentation, final AdvancedData advancedData) {
        super(name, documentation);
        this.advancedData = advancedData;
    }

    public AdvancedData getAdvancedData() {
        return advancedData;
    }

    public void setAdvancedData(AdvancedData advancedData) {
        this.advancedData = advancedData;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FlowNode)) {
            return false;
        }
        FlowNode flowNode = (FlowNode) o;
        return super.equals(flowNode)
                && Objects.equals(advancedData, flowNode.advancedData);
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(getClass()),
                                         super.hashCode(),
                                         Objects.hashCode(advancedData));
    }
}
