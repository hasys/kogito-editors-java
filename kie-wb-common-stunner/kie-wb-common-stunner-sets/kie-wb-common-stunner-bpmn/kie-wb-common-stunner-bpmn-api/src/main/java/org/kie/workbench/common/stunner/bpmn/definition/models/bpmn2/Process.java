package org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "process", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class Process {

    @XmlAttribute
    private String id;

    @XmlAttribute(namespace = "http://www.jboss.org/drools")
    private String packageName;

    @XmlAttribute(namespace = "http://www.jboss.org/drools")
    private String version;

    @XmlAttribute(namespace = "http://www.jboss.org/drools")
    private boolean adHoc;

    @XmlAttribute
    private String name;

    @XmlAttribute(name = "isExecutable")
    private boolean executable;

    @XmlAttribute
    private String processType;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public boolean isAdHoc() {
        return adHoc;
    }

    public void setAdHoc(boolean adHoc) {
        this.adHoc = adHoc;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExecutable() {
        return executable;
    }

    public void setExecutable(boolean executable) {
        this.executable = executable;
    }

    public String getProcessType() {
        return processType;
    }

    public void setProcessType(String processType) {
        this.processType = processType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Process)) {
            return false;
        }
        Process process = (Process) o;
        return isAdHoc() == process.isAdHoc() && isExecutable() == process.isExecutable() && Objects.equals(getId(), process.getId()) && Objects.equals(getPackageName(), process.getPackageName()) && Objects.equals(getVersion(), process.getVersion()) && Objects.equals(getName(), process.getName()) && Objects.equals(getProcessType(), process.getProcessType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPackageName(), getVersion(), isAdHoc(), getName(), isExecutable(), getProcessType());
    }
}
