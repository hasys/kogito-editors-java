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
package org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.treblereel.gwt.xml.mapper.api.annotation.XmlUnwrappedCollection;

public class BpmnPlane {

    @XmlAttribute
    private String bpmnElement;

    @XmlElement(name = "BPMNShape")
    @XmlUnwrappedCollection
    private List<BpmnShape> bpmnShapes = new ArrayList<>();

    @XmlElement(name = "BPMNEdge")
    @XmlUnwrappedCollection
    private List<BpmnEdge> bpmnEdges;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public String getBpmnElement() {
        return bpmnElement;
    }

    public void setBpmnElement(String bpmnElement) {
        this.bpmnElement = bpmnElement;
    }

    public List<BpmnShape> getBpmnShapes() {
        return bpmnShapes;
    }

    public void setBpmnShapes(List<BpmnShape> bpmnShapes) {
        this.bpmnShapes = bpmnShapes;
    }

    public List<BpmnEdge> getBpmnEdges() {
        return bpmnEdges;
    }

    public void setBpmnEdges(List<BpmnEdge> bpmnEdges) {
        this.bpmnEdges = bpmnEdges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BpmnPlane)) {
            return false;
        }
        BpmnPlane plane = (BpmnPlane) o;
        return Objects.equals(getBpmnElement(), plane.getBpmnElement())
                && Objects.equals(getBpmnShapes(), plane.getBpmnShapes())
                && Objects.equals(getBpmnEdges(), plane.getBpmnEdges());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBpmnElement(),
                            getBpmnShapes(),
                            getBpmnEdges());
    }
}
