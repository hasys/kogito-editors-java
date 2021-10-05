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
package org.kie.workbench.common.stunner.bpmn.marshaller.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Process;
import org.kie.workbench.common.stunner.bpmn.marshaller.Marshaller;
import org.kie.workbench.common.stunner.bpmn.marshaller.Unmarshalling;
import org.kie.workbench.common.stunner.core.diagram.Diagram;
import org.kie.workbench.common.stunner.core.diagram.Metadata;
import org.kie.workbench.common.stunner.core.graph.Graph;
import org.kie.workbench.common.stunner.core.graph.Node;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class ProcessMarshallingTest {

    private final Marshaller marshaller = new Marshaller();

    private final String PATH = "org/kie/workbench/common/stunner/bpmn/marshaller/model/";

    private final String FILE_NAME = PATH + "emptyProcess.bpmn";

    private Diagram<Graph, Metadata> diagram;

    private Process process;


    @Before
    public void init() throws Exception {
        diagram = Unmarshalling.unmarshall(marshaller, FILE_NAME);
        process = getProcess(diagram, "processID");
    }

    @Test
    public void testProcessUnmarshalled() {
        assertEquals(1, getNodes(diagram).size());
    }

    @Test
    public void testProcessNameIsValid() {
        assertEquals("somePathProcessName", process.getName());
    }

    @Test
    public void testProcessPackageName() {
        assertEquals("com.example.name.package", process.getPackageName());
    }

    @Test
    public void testProcessProcessType() {
        assertEquals("Private", process.getProcessType());
    }

    @Test
    public void testProcessVersion() {
        assertEquals("6.5.7.9", process.getVersion());
    }

    @Test
    public void testProcessIsAdHoc() {
        assertEquals(false, process.isAdHoc());
    }

    @Test
    public void testProcessIsExecutable() {
        assertEquals(true, process.isExecutable());
    }

    @SuppressWarnings("unchecked")
    private List<Node<Process, ?>> getNodes(Diagram<Graph, Metadata> diagram) {
        Graph<?, Node<Process, ?>> graph = diagram.getGraph();
        assertNotNull(graph);
        Iterator<Node<Process, ?>> nodesIterable = graph.nodes().iterator();
        List<Node<Process, ?>> nodes = new ArrayList<>();
        nodesIterable.forEachRemaining(nodes::add);
        return nodes;
    }

    @SuppressWarnings("unchecked")
    private Process getProcess(Diagram<Graph, Metadata> diagram, String id) {
        Node<? extends Process, ?> node = getNodeById(diagram, id);
        return node.getContent();
    }

    @SuppressWarnings("unchecked")
    private Node<? extends Process, ?> getNodeById(Diagram<Graph, Metadata> diagram, String id) {
        Node<? extends Process, ?> node = diagram.getGraph().getNode(id);
        assertThat(node).isNotNull();
        return node;
    }

}
