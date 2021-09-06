package org.kie.workbench.common.stunner.bpmn.definition.models.bpsim;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public class ProcessingTime {

    @XmlElement(name = "NormalDistribution")
    private NormalDistribution normalDistribution;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public NormalDistribution getNormalDistribution() {
        return normalDistribution;
    }

    public void setNormalDistribution(NormalDistribution normalDistribution) {
        this.normalDistribution = normalDistribution;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessingTime)) {
            return false;
        }
        ProcessingTime that = (ProcessingTime) o;
        return Objects.equals(getNormalDistribution(), that.getNormalDistribution());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNormalDistribution());
    }
}
