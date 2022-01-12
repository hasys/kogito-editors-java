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

import java.util.HashMap;
import java.util.Map;

import org.kie.workbench.common.stunner.bpmn.definition.BPMNViewDefinition;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.BaseTask;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.EndEvent;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Lane;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.ScriptTask;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.StartEvent;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.StartMessageEvent;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.StartSignalEvent;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.UserTask;

public class IdGenerator {

    private static int startNodeCounter = 1;

    private static int endNodeCounter = 1;

    private static int taskCounter = 1;

    private static int userTaskCounter = 1;

    private static int scriptTaskCounter = 1;

    private static int messageCounter = 1;

    private static int signalCounter = 1;

    private static int laneCounter = 1;

    // key is old ID and value is new ID
    private static Map<String, String> oldNewId = new HashMap<>();

    public static void reset() {
        startNodeCounter = 1;
        endNodeCounter = 1;
        taskCounter = 1;
        userTaskCounter = 1;
        scriptTaskCounter = 1;
        messageCounter = 1;
        signalCounter = 1;
        laneCounter = 1;
        oldNewId = new HashMap<>();
    }

    public static String getNextIdFor(BPMNViewDefinition flowElement, String oldId) {
        if (oldNewId.containsKey(oldId)) {
            return oldNewId.get(oldId);
        }

        String newId = generateNewId(flowElement);
        oldNewId.put(oldId, newId);

        return newId;
    }

    private static String generateNewId(BPMNViewDefinition flowElement) {
        if (flowElement instanceof StartEvent) {
            return "StartEvent_" + startNodeCounter++;
        }

        if (flowElement instanceof EndEvent) {
            return "EndEvent_" + endNodeCounter++;
        }

        if (flowElement instanceof Lane) {
            return "Lane_" + laneCounter++;
        }

        if (flowElement instanceof BaseTask) {
            if (flowElement instanceof UserTask) {
                return "UserTask_" + userTaskCounter++;
            }
            if (flowElement instanceof ScriptTask) {
                return "ScriptTask_" + scriptTaskCounter++;
            }

            return "Task_" + taskCounter++;
        }
        return null;
    }

    public static String getTypeId(StartEvent event) {
        if (event instanceof StartMessageEvent) {
            return "Message_" + messageCounter++;
        }

        if (event instanceof StartSignalEvent) {
            return "Signal_" + signalCounter++;
        }

        return "";
    }
}
