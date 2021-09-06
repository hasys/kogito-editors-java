package org.kie.workbench.common.stunner.bpmn.definition.models.bpsim;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public class CostParameters {

    @XmlElement(name = "UnitCost")
    private UnitCost unitCost;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public UnitCost getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(UnitCost unitCost) {
        this.unitCost = unitCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CostParameters)) {
            return false;
        }
        CostParameters that = (CostParameters) o;
        return Objects.equals(getUnitCost(), that.getUnitCost());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUnitCost());
    }
}
