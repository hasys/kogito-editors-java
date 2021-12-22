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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi.BpmnDiagram;
import org.kie.workbench.common.stunner.core.util.HashUtil;
import org.treblereel.gwt.xml.mapper.api.annotation.XMLMapper;
import org.treblereel.gwt.xml.mapper.api.annotation.XmlUnwrappedCollection;

@XMLMapper
@XmlType(propOrder = {"itemDefinitions", "process", "bpmnDiagram", "relationship"})
@XmlRootElement(name = "definitions", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class Definitions {

    @XmlAttribute
    private String id = "Definition_1";

    @XmlAttribute
    private String exporter = "jBPM Process Modeler";

    @XmlAttribute
    private String exporterVersion = "2.0";

    @XmlElement(name = "BPMNDiagram")
    private BpmnDiagram bpmnDiagram;

    private Relationship relationship;

    private Process process;

    @XmlElement(name = "itemDefinition")
    @XmlUnwrappedCollection
    private List<ItemDefinition> itemDefinitions = new ArrayList<>();

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getExporter() {
        return exporter;
    }

    public void setExporter(String exporter) {
        this.exporter = exporter;
    }

    public String getExporterVersion() {
        return exporterVersion;
    }

    public void setExporterVersion(String exporterVersion) {
        this.exporterVersion = exporterVersion;
    }

    public BpmnDiagram getBpmnDiagram() {
        return bpmnDiagram;
    }

    public void setBpmnDiagram(BpmnDiagram bpmnDiagram) {
        this.bpmnDiagram = bpmnDiagram;
    }

    public Relationship getRelationship() {
        return relationship;
    }

    public void setRelationship(Relationship relationship) {
        this.relationship = relationship;
    }

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public List<ItemDefinition> getItemDefinitions() {
        return itemDefinitions;
    }

    public void setItemDefinitions(List<ItemDefinition> itemDefinitions) {
        this.itemDefinitions = itemDefinitions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Definitions)) {
            return false;
        }
        Definitions that = (Definitions) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(getExporter(), that.getExporter())
                && Objects.equals(getExporterVersion(), that.getExporterVersion())
                && Objects.equals(getBpmnDiagram(), that.getBpmnDiagram())
                && Objects.equals(getRelationship(), that.getRelationship())
                && Objects.equals(getProcess(), that.getProcess())
                && Objects.equals(getItemDefinitions(), that.getItemDefinitions());
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(id),
                                         Objects.hashCode(exporter),
                                         Objects.hashCode(exporterVersion),
                                         Objects.hashCode(bpmnDiagram),
                                         Objects.hashCode(relationship),
                                         Objects.hashCode(process),
                                         Objects.hashCode(itemDefinitions));
    }
}
