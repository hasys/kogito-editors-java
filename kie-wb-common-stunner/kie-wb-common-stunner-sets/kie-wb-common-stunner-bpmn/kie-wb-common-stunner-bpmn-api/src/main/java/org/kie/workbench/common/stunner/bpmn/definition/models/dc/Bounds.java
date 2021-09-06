package org.kie.workbench.common.stunner.bpmn.definition.models.dc;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;

public class Bounds {

    @XmlAttribute
    private double height;

    @XmlAttribute
    private double width;

    @XmlAttribute
    private double x;

    @XmlAttribute
    private double y;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

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
        if (!(o instanceof Bounds)) {
            return false;
        }
        Bounds bounds = (Bounds) o;
        return Double.compare(bounds.getHeight(), getHeight()) == 0 && Double.compare(bounds.getWidth(), getWidth()) == 0 && Double.compare(bounds.getX(), getX()) == 0 && Double.compare(bounds.getY(), getY()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHeight(), getWidth(), getX(), getY());
    }
}
