package org.kie.workbench.common.stunner.bpmn.definition.models.bpsim;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public class BPSimData {

    @XmlElement(name = "Scenario")
    private Scenario scenario;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BPSimData)) {
            return false;
        }
        BPSimData bpSimData = (BPSimData) o;
        return Objects.equals(getScenario(), bpSimData.getScenario());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getScenario());
    }
}
