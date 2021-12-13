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
import java.util.Objects;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.stream.XMLStreamException;

import elemental2.dom.DomGlobal;
import org.kie.workbench.common.stunner.bpmn.BPMNDefinitionSet;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNViewDefinition;
import org.kie.workbench.common.stunner.bpmn.definition.FlowElement;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.BaseTask;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Definitions;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Definitions_XMLMapperImpl;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.EndEvent;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.ExtensionElements;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Process;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Relationship;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.ScriptTask;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.SequenceFlow;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.StartEvent;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi.BpmnDiagram;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi.BpmnEdge;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi.BpmnPlane;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmndi.BpmnShape;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.BPSimData;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.ElementParameters;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpsim.Scenario;
import org.kie.workbench.common.stunner.bpmn.definition.models.dc.Bounds;
import org.kie.workbench.common.stunner.bpmn.definition.models.di.Waypoint;
import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.content.view.ControlPoint;
import org.kie.workbench.common.stunner.core.graph.content.view.ViewConnector;
import org.kie.workbench.common.stunner.core.graph.content.view.ViewConnectorImpl;
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
        IdGenerator.reset();
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

        nodes.forEach(DomGlobal.console::info);
        DomGlobal.console.warn("Finished");

        for (final NodeImpl<ViewImpl<BPMNViewDefinition>> node : nodes) {
            BPMNViewDefinition n = node.getContent().getDefinition();
            if (n instanceof Process) {
                continue;
            }

            DomGlobal.console.info(n);
            DomGlobal.console.info(node.getOutEdges());
            DomGlobal.console.info(node.getInEdges());
            DomGlobal.console.warn("Finished again");
            if (n instanceof FlowElement) {
                ((FlowElement) n).setId(IdGenerator.getNextIdFor(n));
            }

            if (n instanceof StartEvent) {
                StartEvent startEvent = (StartEvent) n;
                process.getStartEvents().add(startEvent);

                // Adding simulation properties
                simulationElements.add(startEvent.getElementParameters());

                List<String> outgoing = new ArrayList<>();
                node.getOutEdges().forEach(edge -> {
                    if (edge.getContent() instanceof ViewConnectorImpl) {
                        ViewConnector connector = (ViewConnector) edge.getContent();
                        if (connector.getDefinition() instanceof SequenceFlow) {
                            SequenceFlow flow = (SequenceFlow) connector.getDefinition();
                            process.getSequenceFlows().add(flow);
                            flow.setSourceRef(startEvent.getId());
                            outgoing.add(flow.getId());
                        }
                    }
                });

                //startEvent.setOutgoing(outgoing);
            }

            if (n instanceof EndEvent) {
                EndEvent endEvent = (EndEvent) n;
                process.getEndEvents().add(endEvent);

                // Adding simulation properties
                simulationElements.add(endEvent.getElementParameters());

                List<String> incoming = new ArrayList<>();
                node.getInEdges().forEach(edge -> {
                    for (SequenceFlow flow : process.getSequenceFlows()) {
                        if (Objects.equals(flow.getId(), getId(edge))) {
                            flow.setTargetRef(endEvent.getId());
                            incoming.add(flow.getId());
                        }
                    }
                });

                //endEvent.setIncoming(incoming);
            }

            if (n instanceof BaseTask) {
                BaseTask task = (BaseTask) n;
                if (n instanceof ScriptTask) {
                    process.getScriptTasks().add(task);
                } else {
                    process.getTasks().add(task);
                }
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

    private BpmnEdge createBpmnEdge(SequenceFlow flow, ControlPoint[] controlPoints) {
        BpmnEdge edge = new BpmnEdge();
        edge.setId("edge_shape_" + flow.getSourceRef() + "_to_shape_" + flow.getTargetRef());
        edge.setBpmnElement(flow.getId());

        List<Waypoint> waypoints = new ArrayList<>();
        for (ControlPoint point : controlPoints) {
            Waypoint waypoint = new Waypoint();
            waypoint.setX(point.getLocation().getX());
            waypoint.setY(point.getLocation().getY());
            waypoints.add(waypoint);
        }
        edge.setWaypoint(waypoints);
        return edge;
    }

    private String getId(Edge edge) {
        if (edge.getContent() instanceof ViewConnectorImpl) {
            ViewConnector connector = (ViewConnector) edge.getContent();
            if (connector.getDefinition() instanceof SequenceFlow) {
                SequenceFlow flow = (SequenceFlow) connector.getDefinition();
                return flow.getId();
            }
        }

        return null;
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
