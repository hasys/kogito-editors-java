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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.stream.XMLStreamException;

import org.kie.workbench.common.stunner.bpmn.BPMNDefinitionSet;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNViewDefinition;
import org.kie.workbench.common.stunner.bpmn.definition.FlowElement;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Definitions;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Definitions_XMLMapperImpl;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.EndEvent;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.ExtensionElements;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Process;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Relationship;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.StartEvent;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi.BpmnDiagram;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi.BpmnPlane;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi.BpmnShape;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.BPSimData;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.ElementParameters;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.Scenario;
import org.kie.workbench.common.stunner.bpmn.definition.models.dc.Bounds;
import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.graph.content.view.ViewImpl;
import org.kie.workbench.common.stunner.core.graph.impl.NodeImpl;

import static java.util.stream.StreamSupport.stream;

@ApplicationScoped
public class BPMNClientMarshalling {

    private static Definitions_XMLMapperImpl mapper = Definitions_XMLMapperImpl.INSTANCE;

    @Inject
    public BPMNClientMarshalling() {
    }

    @PostConstruct
    public void init() {
    }

    @SuppressWarnings("unchecked")
    public String marshall(final Diagram diagram) {
        Definitions definitions = createDefinitions(diagram.getGraph().nodes());

        try {
            return mapper.write(definitions);
        } catch (XMLStreamException e) {
            return "";
        }
    }

    Definitions createDefinitions(Iterable<NodeImpl<ViewImpl<BPMNViewDefinition>>> nodes) {
        Definitions definitions = new Definitions();
        BpmnDiagram bpmnDiagram = new BpmnDiagram();
        BpmnPlane plane = new BpmnPlane();
        List<ElementParameters> simulationElements = new ArrayList<>();

        bpmnDiagram.setBpmnPlane(plane);
        definitions.setBpmnDiagram(bpmnDiagram);

        Process process = (Process) stream(nodes.spliterator(), false)
                .map(node -> node.getContent().getDefinition())
                .filter(node -> node instanceof Process)
                .findFirst().orElse(new Process());

        definitions.setProcess(process);
        plane.setBpmnElement(process.getName());

        for (final NodeImpl<ViewImpl<BPMNViewDefinition>> node : nodes) {
            BPMNViewDefinition n = node.getContent().getDefinition();
            if (n instanceof FlowElement) {
                ((FlowElement) n).setId(IdGenerator.getNextIdFor(n));
            }

            if (n instanceof StartEvent) {
                StartEvent startEvent = (StartEvent) n;
                process.getStartEvents().add(startEvent);

                // Adding simulation properties
                simulationElements.add(startEvent.getElementParameters());
            }

            if (n instanceof EndEvent) {
                EndEvent endEvent = (EndEvent) n;
                process.getEndEvents().add(endEvent);

                // Adding simulation properties
                simulationElements.add(endEvent.getElementParameters());
            }

            // Adding Shape to Diagram
            plane.getBpmnShapes().add(
                    createShapeForBounds(node.getContent().getBounds(), n.getId())
            );
        }

        Scenario scenario = new Scenario();
        scenario.setElementParameters(simulationElements);

        BPSimData simData = new BPSimData();
        simData.setScenario(scenario);

        ExtensionElements extensionElements = new ExtensionElements();
        extensionElements.setBpSimData(simData);

        Relationship relationship = new Relationship();
        relationship.setTarget("BPSimData");
        relationship.setExtensionElements(extensionElements);
        relationship.setTarget(definitions.getId());
        relationship.setSource(definitions.getId());
        definitions.setRelationship(relationship);

        return definitions;
    }

    private BpmnShape createShapeForBounds(final org.kie.workbench.common.stunner.core.graph.content.Bounds bounds, final String id) {
        BpmnShape shape = new BpmnShape("shape_" + id, id);
        Bounds b = new Bounds(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        shape.setBounds(b);

        return shape;
    }

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
