package org.kie.workbench.common.stunner.bpmn.definition.models.bpsim;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public class TimeParameters {

    @XmlElement(name = "ProcessingTime")
    private ProcessingTime processingTime;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public ProcessingTime getProcessingTime() {
        return processingTime;
    }

    public void setProcessingTime(ProcessingTime processingTime) {
        this.processingTime = processingTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TimeParameters)) {
            return false;
        }
        TimeParameters that = (TimeParameters) o;
        return Objects.equals(getProcessingTime(), that.getProcessingTime());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProcessingTime());
    }
}
