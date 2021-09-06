package org.kie.workbench.common.stunner.bpmn.definition.models.di;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;

public class Waypoint {

    @XmlAttribute
    private double x;

    @XmlAttribute
    private double y;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Waypoint)) {
            return false;
        }
        Waypoint waypoint = (Waypoint) o;
        return Double.compare(waypoint.getX(), getX()) == 0 && Double.compare(waypoint.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX(), getY());
    }
}
