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
package org.kie.workbench.common.stunner.bpmn.definition.models.di;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;

public class Waypoint {

    @XmlAttribute
    private double x;

    @XmlAttribute
    private double y;

    public Waypoint() {

    }

    public Waypoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

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
