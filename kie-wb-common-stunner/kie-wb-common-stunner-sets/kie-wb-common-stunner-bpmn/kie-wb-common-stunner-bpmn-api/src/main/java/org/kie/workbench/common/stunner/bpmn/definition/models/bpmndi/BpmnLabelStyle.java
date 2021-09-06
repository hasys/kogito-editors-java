package org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import org.kie.workbench.common.stunner.bpmn.definition.models.dc.Font;

public class BpmnLabelStyle {

    @XmlAttribute
    private String id;

    @XmlElement(name = "Font")
    private Font font;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BpmnLabelStyle)) {
            return false;
        }
        BpmnLabelStyle that = (BpmnLabelStyle) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getFont(), that.getFont());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFont());
    }
}
