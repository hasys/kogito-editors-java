/*
 * Copyright 2019 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.bpmn.client.marshall.service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.stream.XMLStreamException;

import elemental2.dom.DomGlobal;
import org.kie.workbench.common.stunner.bpmn.BPMNDefinitionSet;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Definitions;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Definitions_XMLMapperImpl;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.ExtensionElements;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Process;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Relationship;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi.BpmnDiagram;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi.BpmnPlane;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.BPSimData;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.Scenario;
import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.diagram.Metadata;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.content.view.ViewImpl;
import org.kie.workbench.common.stunner.core.graph.impl.NodeImpl;

@ApplicationScoped
public class BPMNClientMarshalling {

    private static Definitions_XMLMapperImpl mapper = Definitions_XMLMapperImpl.INSTANCE;

    @Inject
    public BPMNClientMarshalling() {}

    @PostConstruct
    public void init() {
    }

    @SuppressWarnings("unchecked")
    public String marshall(final Diagram<Graph, Metadata> diagram) {
        Definitions definitions = new Definitions();
        Process process = new Process();
        diagram.getGraph().nodes().forEach(o -> DomGlobal.console.info(o));
        for (final Object node : diagram.getGraph().nodes()) {
            ViewImpl view = (ViewImpl) ((NodeImpl) node).getContent();
            DomGlobal.console.info(view.getBounds());
            process = (Process)(view).getDefinition();
        }
        definitions.setProcess(process);

        BpmnDiagram bpmnDiagram = new BpmnDiagram();
        BpmnPlane plane = new BpmnPlane();
        plane.setBpmnElement(process.getName());
        bpmnDiagram.setBpmnPlane(plane);
        definitions.setBpmnDiagram(bpmnDiagram);

        Relationship relationship = new Relationship();
        relationship.setTarget("BPSimData");
        ExtensionElements extensionElements = new ExtensionElements();
        BPSimData simData = new BPSimData();
        Scenario scenario = new Scenario();
        scenario.setScenarioParameters("");
        simData.setScenario(scenario);
        extensionElements.setBpSimData(simData);
        relationship.setExtensionElements(extensionElements);
        relationship.setTarget(definitions.getId());
        relationship.setSource(definitions.getId());
        definitions.setRelationship(relationship);

        try {
            return mapper.write(definitions);
        } catch (XMLStreamException e) {
            return "";
        }
    }

    @SuppressWarnings("unchecked")
    public Definitions unmarshall(final String raw) {
        try {
            return mapper.read(raw);
        } catch (XMLStreamException e) {
            return new org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Definitions();
        }
    }

    public static String getDefinitionSetId() {
        return BindableAdapterUtils.getDefinitionSetId(BPMNDefinitionSet.class);
    }
}
