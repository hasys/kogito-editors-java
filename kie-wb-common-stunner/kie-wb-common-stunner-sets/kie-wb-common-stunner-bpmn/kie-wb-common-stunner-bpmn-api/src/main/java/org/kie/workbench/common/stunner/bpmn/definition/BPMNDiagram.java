/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.bpmn.definition;

import org.kie.workbench.common.stunner.bpmn.definition.property.background.BackgroundSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.cm.CaseManagementSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.diagram.BaseDiagramSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.diagram.imports.Imports;
import org.kie.workbench.common.stunner.bpmn.definition.property.font.FontSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.BaseProcessData;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.BaseRootProcessAdvancedData;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.HasProcessData;

public interface BPMNDiagram<D extends BaseDiagramSet, P extends BaseProcessData, S extends BaseRootProcessAdvancedData> extends BPMNViewDefinition,
                                                                                                                                 HasProcessData<P> {

    String getName();

    void setName(final String name);

    String getDocumentation();

    void setDocumentation(final String documentation);

    String getId();

    void setId(final String id);

    String getPackageName();

    void setPackageName(final String packageName);

    String getProcessType();

    void setProcessType(final String processType);

    String getVersion();

    void setVersion(final String version);

    Boolean isAdHoc();

    void setAdHoc(final Boolean adHoc);

    String getProcessInstanceDescription();

    void setProcessInstanceDescription(final String processInstanceDescription);

    Imports getImports();

    void setImports(final Imports imports);

    Boolean isExecutable();

    void setExecutable(final Boolean executable);

    String getSlaDueDate();

    void setSlaDueDate(final String slaDueDate);

    P getProcessData();

    void setProcessData(final P processData);

    S getAdvancedData();

    void setAdvancedData(final S advancedData);

    BackgroundSet getBackgroundSet();

    void setBackgroundSet(final BackgroundSet backgroundSet);

    FontSet getFontSet();

    void setFontSet(final FontSet fontSet);

    CaseManagementSet getCaseManagementSet();

    void setCaseManagementSet(CaseManagementSet caseManagementSet);
}
