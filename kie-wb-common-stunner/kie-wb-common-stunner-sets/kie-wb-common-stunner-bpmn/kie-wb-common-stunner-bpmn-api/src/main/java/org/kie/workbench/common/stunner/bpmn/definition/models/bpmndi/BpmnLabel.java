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

import javax.xml.bind.annotation.XmlElement;

import org.kie.workbench.common.stunner.bpmn.definition.models.dc.Bounds;

public class BpmnLabel {

    @XmlElement(name = "Bounds")
    private Bounds bounds;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public Bounds getBounds() {
        return bounds;
    }

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BpmnLabel)) {
            return false;
        }
        BpmnLabel bpmnLabel = (BpmnLabel) o;
        return Objects.equals(getBounds(), bpmnLabel.getBounds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBounds());
    }
}
