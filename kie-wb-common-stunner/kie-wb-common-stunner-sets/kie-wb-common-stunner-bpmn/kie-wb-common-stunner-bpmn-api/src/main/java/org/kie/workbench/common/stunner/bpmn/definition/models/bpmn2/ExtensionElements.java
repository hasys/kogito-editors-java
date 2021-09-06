package org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2;

import java.util.List;
import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.BPSimData;
import org.kie.workbench.common.stunner.bpmn.definition.models.drools.MetaData;
import org.treblereel.gwt.xml.mapper.api.annotation.XmlUnwrappedCollection;

@XmlRootElement(name = "extensionElements", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class ExtensionElements {

    @XmlElement(name = "BPSimData")
    private BPSimData bpSimData;

    @XmlUnwrappedCollection
    private List<MetaData> metaData;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public BPSimData getBpSimData() {
        return bpSimData;
    }

    public void setBpSimData(BPSimData bpSimData) {
        this.bpSimData = bpSimData;
    }

    public List<MetaData> getMetaData() {
        return metaData;
    }

    public void setMetaData(List<MetaData> metaData) {
        this.metaData = metaData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtensionElements)) {
            return false;
        }
        ExtensionElements that = (ExtensionElements) o;
        return Objects.equals(getBpSimData(), that.getBpSimData())
                && Objects.equals(getMetaData(), that.getMetaData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBpSimData(),
                            getMetaData());
    }
}
