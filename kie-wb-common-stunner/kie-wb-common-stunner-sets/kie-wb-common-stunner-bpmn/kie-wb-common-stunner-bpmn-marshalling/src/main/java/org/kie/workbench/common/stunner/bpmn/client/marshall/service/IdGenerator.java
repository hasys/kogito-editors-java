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
package org.kie.workbench.common.stunner.bpmn.client.marshall.service;

import org.kie.workbench.common.stunner.bpmn.definition.BPMNViewDefinition;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.BaseTask;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.EndEvent;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.StartEvent;

public class IdGenerator {

    private static int startNodeCounter = 1;

    private static int endNodeCounter = 1;

    private static int taskCounter = 1;

    public static void reset() {
        startNodeCounter = 1;
        endNodeCounter = 1;
        taskCounter = 1;
    }

    public static String getNextIdFor(BPMNViewDefinition flowElement) {
        if (flowElement instanceof StartEvent) {
            return "StartEvent_" + startNodeCounter++;
        }

        if (flowElement instanceof EndEvent) {
            return "EndEvent_" + endNodeCounter++;
        }

        if (flowElement instanceof BaseTask) {
            return "Task_" + taskCounter++;
        }

        return null;
    }
}
