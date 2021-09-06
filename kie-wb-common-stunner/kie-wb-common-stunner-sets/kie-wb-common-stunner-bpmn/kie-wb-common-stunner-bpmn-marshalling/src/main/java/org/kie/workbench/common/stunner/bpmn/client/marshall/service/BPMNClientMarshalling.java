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

import org.kie.workbench.common.stunner.bpmn.BPMNDefinitionSet;
import org.kie.workbench.common.stunner.bpmn.definition.BPMNDiagramImpl;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Definitions_XMLMapperImpl;
import org.kie.workbench.common.stunner.core.definition.adapter.binding.BindableAdapterUtils;
import org.kie.workbench.common.stunner.core.diagram.AbstractDiagram;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.diagram.DiagramImpl;
import org.kie.workbench.common.stunner.core.diagram.Metadata;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.definition.DefinitionSet;
import org.kie.workbench.common.stunner.core.graph.content.definition.DefinitionSetImpl;
import org.kie.workbench.common.stunner.core.graph.impl.GraphImpl;
import org.kie.workbench.common.stunner.core.graph.store.GraphNodeStoreImpl;
import org.kie.workbench.common.stunner.core.util.UUID;

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
        return "";
    }

    @SuppressWarnings("unchecked")
    public Graph<DefinitionSet, Node> unmarshall(final Metadata metadata,
                                                 final String raw) {
        org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Definitions definitions;
        try {
            definitions = mapper.read(raw);
        } catch (XMLStreamException e) {
            definitions = new org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Definitions();
        }

        metadata.setCanvasRootUUID(definitions.getId());
        metadata.setTitle(definitions.getProcess().getName());

        final GraphImpl graph = new GraphImpl<>(UUID.uuid(),
                                                new GraphNodeStoreImpl());
        final DefinitionSet content = new DefinitionSetImpl(definitions.getId());
        graph.setContent(content);
        graph.getLabels().add(definitions.getId());
        final AbstractDiagram<Graph, Metadata> diagram = new DiagramImpl(definitions.getProcess().getName(),
                                                                         metadata);
        diagram.setGraph(graph);

        return graph;
    }

    public static Class<?> getDiagramClass() {
        return BPMNDiagramImpl.class;
    }

    public static String getDefinitionSetId() {
        return BindableAdapterUtils.getDefinitionSetId(BPMNDefinitionSet.class);
    }
}
