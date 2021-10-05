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
package org.kie.workbench.common.stunner.bpmn.marshaller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.kie.workbench.common.stunner.bpmn.client.marshall.service.BPMNClientMarshalling;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Definitions;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Process;
import org.kie.workbench.common.stunner.core.definition.service.DiagramMarshaller;
import org.kie.workbench.common.stunner.core.definition.service.DiagramMetadataMarshaller;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.diagram.Metadata;
import org.kie.workbench.common.stunner.core.graph.Edge;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.kie.workbench.common.stunner.core.graph.content.definition.DefinitionSet;
import org.kie.workbench.common.stunner.core.graph.content.definition.DefinitionSetImpl;
import org.kie.workbench.common.stunner.core.graph.impl.EdgeImpl;
import org.kie.workbench.common.stunner.core.graph.impl.GraphImpl;
import org.kie.workbench.common.stunner.core.graph.store.GraphNodeStoreImpl;

public class Marshaller implements DiagramMarshaller {

    private BPMNClientMarshalling bpmnClientMarshalling = new BPMNClientMarshalling();

    @Override
    public Graph unmarshall(Metadata metadata, InputStream input) {
        String text = new BufferedReader(
                new InputStreamReader(input, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n")
                );

        Definitions definitions = bpmnClientMarshalling.unmarshall(text);

        final GraphImpl graph = new GraphImpl<>(definitions.getId(),
                                                new GraphNodeStoreImpl());
        final DefinitionSet content = new DefinitionSetImpl(definitions.getId());
        graph.setContent(content);
        graph.getLabels().add(definitions.getId());
        Node<Process, EdgeImpl> node = new Node() {
            private Process process;

            @Override
            public List getInEdges() {
                return null;
            }

            @Override
            public List getOutEdges() {
                return null;
            }

            @Override
            public String getUUID() {
                return process.getId();
            }

            @Override
            public Set<String> getLabels() {
                return null;
            }

            @Override
            public Object getContent() {
                return process;
            }

            @Override
            public void setContent(Object content) {
                process = (Process) content;
            }

            @Override
            public Node asNode() {
                return null;
            }

            @Override
            public Edge asEdge() {
                return null;
            }
        };
        node.setContent(definitions.getProcess());
        graph.addNode(node);

        return graph;
    }

    @Override
    public String marshall(Diagram diagram) {
        return null;
    }

    @Override
    public DiagramMetadataMarshaller getMetadataMarshaller() {
        return null;
    }
}
