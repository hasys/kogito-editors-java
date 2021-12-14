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
package org.kie.workbench.common.stunner.bpmn.definition.models.drools;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlCData;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "onEntry-script", namespace = "http://www.jboss.org/drools")
public class OnEntryScript {

    @XmlAttribute
    private String scriptFormat;

    @XmlElement(name = "script", namespace = "http://www.jboss.org/drools")
    @XmlCData
    private String script;

    public OnEntryScript() {

    }

    public OnEntryScript(String scriptFormat, String script) {
        this.scriptFormat = scriptFormat;
        this.script = script;
    }

    public String getScriptFormat() {
        return scriptFormat;
    }

    public void setScriptFormat(String scriptFormat) {
        this.scriptFormat = scriptFormat;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OnEntryScript)) {
            return false;
        }
        OnEntryScript that = (OnEntryScript) o;
        return Objects.equals(getScriptFormat(), that.getScriptFormat()) && Objects.equals(getScript(), that.getScript());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScriptFormat(), getScript());
    }
}
