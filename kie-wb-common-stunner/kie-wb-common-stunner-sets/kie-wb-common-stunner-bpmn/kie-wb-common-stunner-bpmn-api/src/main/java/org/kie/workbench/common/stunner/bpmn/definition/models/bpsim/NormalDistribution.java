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
