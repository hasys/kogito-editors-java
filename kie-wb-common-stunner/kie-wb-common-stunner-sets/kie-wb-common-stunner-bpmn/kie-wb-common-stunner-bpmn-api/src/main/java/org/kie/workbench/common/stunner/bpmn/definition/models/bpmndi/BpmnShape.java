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

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kie.workbench.common.stunner.bpmn.definition.models.dc.Bounds;

public class BpmnShape {

    @XmlAttribute
    private String id;

    @XmlAttribute
    private String bpmnElement;

    @XmlElement(name = "Bounds")
    private Bounds bounds;

    @XmlElement(name = "BPMNLabel")
    private BpmnLabel bpmnLabel;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBpmnElement() {
        return bpmnElement;
    }

    public void setBpmnElement(String bpmnElement) {
        this.bpmnElement = bpmnElement;
    }

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public BpmnLabel getBpmnLabel() {
        return bpmnLabel;
    }

    public void setBpmnLabel(BpmnLabel bpmnLabel) {
        this.bpmnLabel = bpmnLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BpmnShape)) {
            return false;
        }
        BpmnShape bpmnShape = (BpmnShape) o;
        return Objects.equals(getId(), bpmnShape.getId()) && Objects.equals(getBpmnElement(), bpmnShape.getBpmnElement()) && Objects.equals(getBounds(), bpmnShape.getBounds()) && Objects.equals(getBpmnLabel(), bpmnShape.getBpmnLabel());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getBpmnElement(), getBounds(), getBpmnLabel());
    }
}
