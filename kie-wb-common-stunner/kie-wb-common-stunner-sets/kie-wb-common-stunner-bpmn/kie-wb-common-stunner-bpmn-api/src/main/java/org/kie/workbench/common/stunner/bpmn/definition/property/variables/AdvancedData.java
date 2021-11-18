/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.bpmn.definition.property.variables;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.validation.Valid;

import org.jboss.errai.common.client.api.annotations.MapsTo;
import org.jboss.errai.common.client.api.annotations.Portable;
import org.jboss.errai.databinding.client.api.Bindable;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormDefinition;
import org.kie.workbench.common.forms.adf.definitions.annotations.FormField;
import org.kie.workbench.common.forms.adf.definitions.annotations.metaModel.FieldValue;
import org.kie.workbench.common.stunner.bpmn.definition.models.drools.MetaData;
import org.kie.workbench.common.stunner.bpmn.forms.model.MetaDataEditorFieldType;
import org.kie.workbench.common.stunner.core.definition.annotation.Property;
import org.kie.workbench.common.stunner.core.definition.annotation.property.Value;
import org.kie.workbench.common.stunner.core.util.HashUtil;
import org.kie.workbench.common.stunner.core.util.StringUtils;

@Portable
@Bindable
@FormDefinition(startElement = "metaDataAttributes")
public class AdvancedData implements BaseAdvancedData {

    @Property
    @FormField(
            type = MetaDataEditorFieldType.class
    )
    @Valid
    @Value
    @FieldValue
    private String metaDataAttributes;

    public AdvancedData() {
        this("");
    }

    public AdvancedData(final @MapsTo("metaDataAttributes") String metaDataAttributes) {
        this.metaDataAttributes = metaDataAttributes;
    }

    @Override
    public String getMetaDataAttributes() {
        return metaDataAttributes;
    }

    public void setMetaDataAttributes(String metadataAttributes) {
        this.metaDataAttributes = metadataAttributes;
    }

    public List<MetaData> getAsMetaData() {
        List<MetaData> metaData = new ArrayList<>();

        if (StringUtils.nonEmpty(metaDataAttributes)) {
            String[] metaArray = metaDataAttributes.split("Ø");
            for (String md : metaArray) {
                String[] metaNV = md.split("ß");
                MetaData meta = new MetaData(metaNV[0], metaNV[1]);
                metaData.add(meta);
            }
        }

        return metaData;
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(metaDataAttributes));
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof AdvancedData) {
            AdvancedData other = (AdvancedData) o;
            return Objects.equals(metaDataAttributes, other.metaDataAttributes);
        }
        return false;
    }
}