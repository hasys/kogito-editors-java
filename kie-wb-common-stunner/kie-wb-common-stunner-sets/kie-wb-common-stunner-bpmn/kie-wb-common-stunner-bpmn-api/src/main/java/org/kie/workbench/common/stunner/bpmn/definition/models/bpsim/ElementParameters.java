package org.kie.workbench.common.stunner.bpmn.definition.models.bpsim;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class ElementParameters {

    @XmlAttribute
    private String elementRef;

    @XmlElement(name = "TimeParameters")
    private TimeParameters timeParameters;

    @XmlElement(name = "ResourceParameters")
    private ResourceParameters resourceParameters;

    @XmlElement(name = "CostParameters")
    private CostParameters costParameters;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public String getElementRef() {
        return elementRef;
    }

    public void setElementRef(String elementRef) {
        this.elementRef = elementRef;
    }

    public TimeParameters getTimeParameters() {
        return timeParameters;
    }

    public void setTimeParameters(TimeParameters timeParameters) {
        this.timeParameters = timeParameters;
    }

    public ResourceParameters getResourceParameters() {
        return resourceParameters;
    }

    public void setResourceParameters(ResourceParameters resourceParameters) {
        this.resourceParameters = resourceParameters;
    }

    public CostParameters getCostParameters() {
        return costParameters;
    }

    public void setCostParameters(CostParameters costParameters) {
        this.costParameters = costParameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ElementParameters)) {
            return false;
        }
        ElementParameters that = (ElementParameters) o;
        return Objects.equals(getElementRef(), that.getElementRef()) && Objects.equals(getTimeParameters(), that.getTimeParameters()) && Objects.equals(getResourceParameters(), that.getResourceParameters()) && Objects.equals(getCostParameters(), that.getCostParameters());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getElementRef(), getTimeParameters(), getResourceParameters(), getCostParameters());
    }
}
