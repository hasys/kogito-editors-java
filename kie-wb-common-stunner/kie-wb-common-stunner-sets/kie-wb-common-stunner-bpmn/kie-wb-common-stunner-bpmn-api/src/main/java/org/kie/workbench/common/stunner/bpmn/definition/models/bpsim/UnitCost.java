package org.kie.workbench.common.stunner.bpmn.definition.models.bpsim;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public class UnitCost {

    @XmlElement(name = "FloatingParameter")
    private FloatingParameter floatingParameter;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public FloatingParameter getFloatingParameter() {
        return floatingParameter;
    }

    public void setFloatingParameter(FloatingParameter floatingParameter) {
        this.floatingParameter = floatingParameter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnitCost)) {
            return false;
        }
        UnitCost unitCost = (UnitCost) o;
        return Objects.equals(getFloatingParameter(), unitCost.getFloatingParameter());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFloatingParameter());
    }
}
