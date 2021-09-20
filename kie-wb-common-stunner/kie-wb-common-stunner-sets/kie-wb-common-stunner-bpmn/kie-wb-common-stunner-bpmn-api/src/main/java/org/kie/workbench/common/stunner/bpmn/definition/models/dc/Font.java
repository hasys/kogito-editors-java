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
package org.kie.workbench.common.stunner.bpmn.definition.models.dc;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;

public class Font {

    @XmlAttribute
    private String isBold;

    @XmlAttribute
    private String isItalic;

    @XmlAttribute
    private String isStrikeThrough;

    @XmlAttribute
    private String isUnderline;

    @XmlAttribute
    private String name;

    @XmlAttribute
    private double size;

    // All code behind this comment is auto generated.
    // Please regenerate it again if you added new property.

    public String getIsBold() {
        return isBold;
    }

    public void setIsBold(String isBold) {
        this.isBold = isBold;
    }

    public String getIsItalic() {
        return isItalic;
    }

    public void setIsItalic(String isItalic) {
        this.isItalic = isItalic;
    }

    public String getIsStrikeThrough() {
        return isStrikeThrough;
    }

    public void setIsStrikeThrough(String isStrikeThrough) {
        this.isStrikeThrough = isStrikeThrough;
    }

    public String getIsUnderline() {
        return isUnderline;
    }

    public void setIsUnderline(String isUnderline) {
        this.isUnderline = isUnderline;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Font)) {
            return false;
        }
        Font font = (Font) o;
        return Double.compare(font.getSize(), getSize()) == 0 && Objects.equals(getIsBold(), font.getIsBold()) && Objects.equals(getIsItalic(), font.getIsItalic()) && Objects.equals(getIsStrikeThrough(), font.getIsStrikeThrough()) && Objects.equals(getIsUnderline(), font.getIsUnderline()) && Objects.equals(getName(), font.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIsBold(), getIsItalic(), getIsStrikeThrough(), getIsUnderline(), getName(), getSize());
    }
}
