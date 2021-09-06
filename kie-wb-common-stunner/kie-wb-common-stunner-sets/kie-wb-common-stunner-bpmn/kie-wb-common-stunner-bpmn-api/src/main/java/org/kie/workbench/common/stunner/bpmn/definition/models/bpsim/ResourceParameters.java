package org.kie.workbench.common.stunner.bpmn.definition.models.bpsim;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public class ResourceParameters {

    @XmlElement(name = "Availability")
    private Availability availability;

    @XmlElement(name = "Quantity")
    private Quantity quantity;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public Availability getAvailability() {
        return availability;
    }

    public void setAvailability(Availability availability) {
        this.availability = availability;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public void setQuantity(Quantity quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ResourceParameters)) {
            return false;
        }
        ResourceParameters that = (ResourceParameters) o;
        return Objects.equals(getAvailability(), that.getAvailability()) && Objects.equals(getQuantity(), that.getQuantity());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAvailability(), getQuantity());
    }
}
