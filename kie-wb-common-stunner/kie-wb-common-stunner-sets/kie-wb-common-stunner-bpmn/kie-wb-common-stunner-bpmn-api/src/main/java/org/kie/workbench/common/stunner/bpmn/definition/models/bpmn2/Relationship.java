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

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "relationship", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
@XmlType(propOrder = {"extensionElements", "source", "target"})
public class Relationship {

    @XmlAttribute
    private String type = "BPSimData";

    private String source;

    private String target;

    private ExtensionElements extensionElements;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public ExtensionElements getExtensionElements() {
        return extensionElements;
    }

    public void setExtensionElements(ExtensionElements extensionElements) {
        this.extensionElements = extensionElements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Relationship)) {
            return false;
        }
        Relationship that = (Relationship) o;
        return Objects.equals(getType(), that.getType())
                && Objects.equals(getSource(), that.getSource())
                && Objects.equals(getTarget(), that.getTarget())
                && Objects.equals(getExtensionElements(), that.getExtensionElements());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getType(),
                            getSource(),
                            getTarget(),
                            getExtensionElements());
    }
}
