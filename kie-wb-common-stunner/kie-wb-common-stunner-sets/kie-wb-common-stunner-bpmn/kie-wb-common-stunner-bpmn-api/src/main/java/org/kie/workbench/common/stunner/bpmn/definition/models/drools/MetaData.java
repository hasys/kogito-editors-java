package org.kie.workbench.common.stunner.bpmn.definition.models.drools;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlCData;
import javax.xml.bind.annotation.XmlElement;

public class MetaData {

    @XmlAttribute
    private String name;

    @XmlCData
    @XmlElement(name = "metaValue", namespace = "http://www.jboss.org/drools")
    private String metaValue;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMetaValue() {
        return metaValue;
    }

    public void setMetaValue(String metaValue) {
        this.metaValue = metaValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MetaData)) {
            return false;
        }
        MetaData metaData = (MetaData) o;
        return Objects.equals(getName(), metaData.getName())
                && Objects.equals(getMetaValue(), metaData.getMetaValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(),
                            getMetaValue());
    }
}
