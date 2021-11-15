/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.bpmn.definition.property.simulation;

import java.util.Objects;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.adf.definitions.annotations.metaModel.FieldValue;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNPropertySet;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.property.Value;
import org.kie.workbench.common.stunner.core.util.HashUtil;

@Portable
@Bindable
public class SimulationAttributeSet implements BPMNPropertySet {

    @Property
    @Value
    private Double min;

    @Property
    @Value
    private Double max;

    @Property
    @Value
    private Double mean;

    @Property
    @Value
    @FieldValue
    private String timeUnit;

    @Property
    @Value
    @FieldValue
    private Double standardDeviation;

    @Property
    @Value
    @FieldValue
    private String distributionType;

    public SimulationAttributeSet() {
        this(0d,
             0d,
             0d,
             "ms",
             0d,
             "normal");
    }

    public SimulationAttributeSet(final @MapsTo("min") Double min,
                                  final @MapsTo("max") Double max,
                                  final @MapsTo("mean") Double mean,
                                  final @MapsTo("timeUnit") String timeUnit,
                                  final @MapsTo("standardDeviation") Double standardDeviation,
                                  final @MapsTo("distributionType") String distributionType) {
        this.min = min;
        this.max = max;
        this.mean = mean;
        this.timeUnit = timeUnit;
        this.standardDeviation = standardDeviation;
        this.distributionType = distributionType;
    }

    public Double getMin() {
        return min;
    }

    public Double getMax() {
        return max;
    }

    public Double getMean() {
        return mean;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public Double getStandardDeviation() {
        return standardDeviation;
    }

    public String getDistributionType() {
        return distributionType;
    }

    public void setMin(final Double min) {
        this.min = min;
    }

    public void setMax(final Double max) {
        this.max = max;
    }

    public void setMean(final Double mean) {
        this.mean = mean;
    }

    public void setTimeUnit(final String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void setStandardDeviation(final Double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public void setDistributionType(final String distributionType) {
        this.distributionType = distributionType;
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(min),
                                         Objects.hashCode(max),
                                         Objects.hashCode(mean),
                                         Objects.hashCode(timeUnit),
                                         Objects.hashCode(standardDeviation),
                                         Objects.hashCode(distributionType));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof SimulationAttributeSet) {
            SimulationAttributeSet other = (SimulationAttributeSet) o;
            return Objects.equals(min, other.min) &&
                    Objects.equals(max, other.max) &&
                    Objects.equals(mean, other.mean) &&
                    Objects.equals(timeUnit, other.timeUnit) &&
                    Objects.equals(standardDeviation, other.standardDeviation) &&
                    Objects.equals(distributionType, other.distributionType);
        }
        return false;
    }
}
