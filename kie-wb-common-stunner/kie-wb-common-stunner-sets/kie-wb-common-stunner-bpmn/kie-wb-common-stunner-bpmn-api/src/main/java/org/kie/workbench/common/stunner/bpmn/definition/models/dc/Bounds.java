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

    public Bounds() {}

    public Bounds(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

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
