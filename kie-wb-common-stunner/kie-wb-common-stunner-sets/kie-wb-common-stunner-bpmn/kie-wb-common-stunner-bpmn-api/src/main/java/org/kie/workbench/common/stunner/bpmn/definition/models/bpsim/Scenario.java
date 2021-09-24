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
package org.kie.workbench.common.stunner.bpmn.definition.models.bpsim;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.treblereel.gwt.xml.mapper.api.annotation.XmlUnwrappedCollection;

public class Scenario {

    @XmlAttribute
    private String id = "default";

    @XmlAttribute
    private String name = "Simulationscenario";

    @XmlElement(name = "ScenarioParameters")
    private String scenarioParameters = "";

    @XmlElement(name = "ElementParameters")
    @XmlUnwrappedCollection
    private List<ElementParameters> ElementParameters;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScenarioParameters() {
        return scenarioParameters;
    }

    public void setScenarioParameters(String scenarioParameters) {
        this.scenarioParameters = scenarioParameters;
    }

    public List<org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.ElementParameters> getElementParameters() {
        return ElementParameters;
    }

    public void setElementParameters(List<org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.ElementParameters> elementParameters) {
        ElementParameters = elementParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Scenario)) {
            return false;
        }
        Scenario scenario = (Scenario) o;
        return Objects.equals(getId(), scenario.getId())
                && Objects.equals(getName(), scenario.getName())
                && Objects.equals(getScenarioParameters(), scenario.getScenarioParameters())
                && Objects.equals(getElementParameters(), scenario.getElementParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                            getName(),
                            getScenarioParameters(),
                            getElementParameters());
    }
}
