package org.kie.workbench.common.stunner.bpmn.definition.models.bpsim;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;

public class NormalDistribution {

    @XmlAttribute
    private int mean;

    @XmlAttribute
    private int standardDeviation;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public int getMean() {
        return mean;
    }

    public void setMean(int mean) {
        this.mean = mean;
    }

    public int getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(int standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NormalDistribution)) {
            return false;
        }
        NormalDistribution that = (NormalDistribution) o;
        return getMean() == that.getMean() && getStandardDeviation() == that.getStandardDeviation();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMean(), getStandardDeviation());
    }
}
