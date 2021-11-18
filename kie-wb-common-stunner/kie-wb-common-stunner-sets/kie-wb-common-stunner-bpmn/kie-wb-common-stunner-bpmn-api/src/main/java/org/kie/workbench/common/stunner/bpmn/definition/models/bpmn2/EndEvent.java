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

import java.util.Objects;
import java.util.Set;

import javax.xml.bind.annotation.XmlTransient;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.kie.soup.commons.util.Sets;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNCategories;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNViewDefinition;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.ElementParameters;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.NormalDistribution;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.ProcessingTime;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.TimeParameters;
import org.kie.workbench.common.stunner.bpmn.definition.property.dataio.DataIOModel;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.AdvancedData;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Category;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Labels;
import org.kie.workbench.common.stunner.core.definition.annotation.morph.MorphBase;
import org.kie.workbench.common.stunner.core.util.HashUtil;

@MorphBase(defaultType = EndNoneEvent.class)
public abstract class EndEvent extends FlowNode implements BPMNViewDefinition,
                                                           DataIOModel {

    @Category
    @XmlTransient
    public static final transient String category = BPMNCategories.END_EVENTS;
    @Labels
    @XmlTransient
    protected final Set<String> labels = new Sets.Builder<String>()
            .add("all")
            .add("lane_child")
            .add("sequence_end")
            .add("to_task_event")
            .add("from_task_event")
            .add("fromtoall")
            .add("choreography_sequence_end")
            .add("Endevents_all")
            .add("EndEventsMorph")
            .add("cm_nop")
            .build();

    /*
    Simulation parameters for the start event. Used in Simulation section during marshalling/unmarshalling
    This parameter currently not changed by Stunner but can preserve users values.
    Shouldn't be handled in Equals/HashCode.
     */
    @XmlTransient
    private ElementParameters elementParameters = new ElementParameters();

    public EndEvent() {}

    public EndEvent(final @MapsTo("name") String name,
                    final @MapsTo("documentation") String documentation,
                    final AdvancedData advancedData) {
        super(name, documentation, advancedData);
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

    public String getCategory() {
        return category;
    }

    public Set<String> getLabels() {
        return labels;
    }

    //TODO maybe we don't need simulation data for End Events at all, need to check BPMN Spec
    /*
    We should keep ID updated for simulation data since during marshalling
    simulation section will be moved to separate part of XML.
     */
    public void setId(String id) {
        super.setId(id);
        getElementParameters().setElementRef(id);
    }

    //TODO maybe we don't need simulation data for End Events at all, need to check BPMN Spec
    /*
    For compatibility reasons if simulation data is missing we need to generate default one.
     */
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

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(getClass()),
                                         super.hashCode(),
                                         Objects.hashCode(labels),
                                         Objects.hashCode(elementParameters));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof EndEvent) {
            EndEvent other = (EndEvent) o;
            return super.equals(other)
                    && Objects.equals(labels, other.labels)
                    && Objects.equals(elementParameters, other.elementParameters);
        }
        return false;
    }
}
