package org.kie.workbench.common.stunner.bpmn.definition.models.bpsim;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import org.treblereel.gwt.xml.mapper.api.annotation.XmlUnwrappedCollection;

public class Scenario {

    @XmlElement(name = "ScenarioParameters")
    private String scenarioParameters;

    @XmlElement(name = "ElementParameters")
    @XmlUnwrappedCollection
    private List<ElementParameters> ElementParameters;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public String getScenarioParameters() {
        return scenarioParameters;
    }

    public void setScenarioParameters(String scenarioParameters) {
        this.scenarioParameters = scenarioParameters;
    }

    public List<ElementParameters> getElementParameters() {
        return ElementParameters;
    }

    public void setElementParameters(List<ElementParameters> elementParameters) {
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
        return Objects.equals(getScenarioParameters(), scenario.getScenarioParameters()) && Objects.equals(getElementParameters(), scenario.getElementParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScenarioParameters(), getElementParameters());
    }
}
