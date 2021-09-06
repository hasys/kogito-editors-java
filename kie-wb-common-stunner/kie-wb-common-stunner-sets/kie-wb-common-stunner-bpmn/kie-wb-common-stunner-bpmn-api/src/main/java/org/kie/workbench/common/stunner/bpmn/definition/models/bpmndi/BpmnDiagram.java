package org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

public class BpmnDiagram {

    @XmlElement(name = "BPMNPlane")
    private BpmnPlane bpmnPlane;

    @XmlElement(name = "BPMNLabelStyle")
    private BpmnLabelStyle bpmnLabelStyle;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public BpmnPlane getBpmnPlane() {
        return bpmnPlane;
    }

    public void setBpmnPlane(BpmnPlane bpmnPlane) {
        this.bpmnPlane = bpmnPlane;
    }

    public BpmnLabelStyle getBpmnLabelStyle() {
        return bpmnLabelStyle;
    }

    public void setBpmnLabelStyle(BpmnLabelStyle bpmnLabelStyle) {
        this.bpmnLabelStyle = bpmnLabelStyle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BpmnDiagram)) {
            return false;
        }
        BpmnDiagram that = (BpmnDiagram) o;
        return Objects.equals(getBpmnPlane(), that.getBpmnPlane()) && Objects.equals(getBpmnLabelStyle(), that.getBpmnLabelStyle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBpmnPlane(), getBpmnLabelStyle());
    }
}
