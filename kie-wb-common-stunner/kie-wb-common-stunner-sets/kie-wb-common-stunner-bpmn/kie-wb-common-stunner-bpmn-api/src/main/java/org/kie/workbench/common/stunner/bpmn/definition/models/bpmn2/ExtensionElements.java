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

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.BPSimData;
import org.kie.workbench.common.stunner.bpmn.definition.models.drools.MetaData;
import org.kie.workbench.common.stunner.bpmn.definition.models.drools.OnEntryScript;
import org.kie.workbench.common.stunner.bpmn.definition.models.drools.OnExitScript;
import org.treblereel.gwt.xml.mapper.api.annotation.XmlUnwrappedCollection;

@XmlRootElement(name = "extensionElements", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class ExtensionElements {

    @XmlElement(name = "BPSimData")
    private BPSimData bpSimData;

    @XmlUnwrappedCollection
    private List<MetaData> metaData;

    @XmlElement(name = "onEntry-script")
    private OnEntryScript onEntryScript;

    @XmlElement(name = "onExit-script")
    private OnExitScript onExitScript;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public BPSimData getBpSimData() {
        return bpSimData;
    }

    public void setBpSimData(BPSimData bpSimData) {
        this.bpSimData = bpSimData;
    }

    public List<MetaData> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<MetaData> metaData) {
        this.metaData = metaData;
    }

    public OnEntryScript getOnEntryScript() {
        return onEntryScript;
    }

    public void setOnEntryScript(OnEntryScript onEntryScript) {
        this.onEntryScript = onEntryScript;
    }

    public OnExitScript getOnExitScript() {
        return onExitScript;
    }

    public void setOnExitScript(OnExitScript onExitScript) {
        this.onExitScript = onExitScript;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtensionElements)) {
            return false;
        }
        ExtensionElements that = (ExtensionElements) o;
        return Objects.equals(getBpSimData(), that.getBpSimData()) && Objects.equals(getMetaData(), that.getMetaData()) && Objects.equals(getOnEntryScript(), that.getOnEntryScript()) && Objects.equals(getOnExitScript(), that.getOnExitScript());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBpSimData(), getMetaData(), getOnEntryScript(), getOnExitScript());
    }
}
