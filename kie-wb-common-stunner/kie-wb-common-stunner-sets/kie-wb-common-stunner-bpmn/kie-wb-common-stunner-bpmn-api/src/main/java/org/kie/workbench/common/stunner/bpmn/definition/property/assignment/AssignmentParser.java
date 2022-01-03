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
package org.kie.workbench.common.stunner.bpmn.definition.property.assignment;

import java.util.ArrayList;
import java.util.List;

import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.DataInput;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.DataInputAssociation;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.DataOutput;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.DataOutputAssociation;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.DataOutputRefs;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.From;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.OutputSet;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.SourceRef;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.TargetRef;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.To;

/*
 Example string for parsing:
    |varInTask:custom.data.Type:,varInConstantInt:Integer:,varInConstantString:String:,varInExpression:String:||varOutTask:String:,varOutExpression:String:|[din]varIn->varInTask,[din]varInConstantInt=123,[din]varInConstantString=constant+string,[din]varInExpression=%23%7Bexpression.in%7D,[dout]varOutTask->varOut,[dout]%23%7Bexpression.out%7D=varOutExpression
 [0]|[1]|[2]|[3]|[4]
 [0] - single data input (like EndEvent)
         - Example: TaskVarIn:custom.data.Type:
 [1] - multiple data input (like Task)
         - Example: TaskVarIn:custom.data.Type:,TaskConstantIn:Integer:
 [2] - single data output (like StartEvent)
         - Example: TaskVarOut:String:
 [3] - multiple data output (like Task)
         - Example: TaskVarOut:String:,ProcessVarOut:String:
 [4] - assignments
         - Example: [din]ProcessVarIn->TaskVarIn,[din]TaskConstantIn=123,[dout]TaskVarOut->ProcessVarOut,[dout]%23%7Bexpression.out%7D=TaskExpressionOut
 */
public class AssignmentParser {

    public static List<DataInputAssociation> parseDataInputAssociation(String id, String data) {
        List<DataInputAssociation> associations = new ArrayList<>();

        String assignmentsString = getAssignmentsAssignmentVariables(data);
        if (assignmentsString != null && !assignmentsString.isEmpty()) {
            String[] assignments = assignmentsString.split(",");
            for (String assignment : assignments) {
                if (assignment.startsWith("[din]")) {
                    assignment = assignment.substring("[din]".length());
                    DataInputAssociation inputAssociation = new DataInputAssociation();
                    if (assignment.contains("=")) {
                        String[] parts = assignment.split("=");
                        inputAssociation.setTargetRef(new TargetRef(id + "_" + parts[0] + "InputX"));
                        inputAssociation.getAssignment().setFrom(new From(parts[1]));
                        inputAssociation.getAssignment().setTo(new To(inputAssociation.getTargetRef().getValue()));
                    } else {
                        String[] parts = assignment.split("->");
                        inputAssociation = new DataInputAssociation(id + "_" + parts[1] + "InputX", parts[0]);
                    }
                    associations.add(inputAssociation);
                }
            }
        }

        return associations;
    }

    public static List<DataOutputAssociation> parseDataOutputAssociation(String id, String data) {
        List<DataOutputAssociation> associations = new ArrayList<>();

        String assignmentsString = getAssignmentsAssignmentVariables(data);
        if (assignmentsString != null && !assignmentsString.isEmpty()) {
            String[] assignments = assignmentsString.split(",");
            for (String assignment : assignments) {
                if (assignment.startsWith("[dout]")) {
                    assignment = assignment.substring("[dout]".length());
                    DataOutputAssociation outputAssociation = new DataOutputAssociation();
                    if (assignment.contains("=")) {
                        String[] parts = assignment.split("=");
                        outputAssociation.setSourceRef(new SourceRef(id + "_" + parts[1] + "OutputX"));
                        outputAssociation.getAssignment().setFrom(new From(outputAssociation.getSourceRef().getValue()));
                        outputAssociation.getAssignment().setTo(new To(parts[0]));
                    } else {
                        String[] parts = assignment.split("->");
                        outputAssociation = new DataOutputAssociation(id + "_" + parts[0] + "OutputX", parts[1]);
                    }
                    associations.add(outputAssociation);
                }
            }
        }

        return associations;
    }

    public static List<DataInput> parseDataInputs(String assignments, String nodeId) {
        List<DataInput> dataInput = new ArrayList<>();
        String inputVariables = getInputAssignmentVariables(assignments);
        String[] inVars = inputVariables.split(",");
        for (String inVar : inVars) {
            String[] var = inVar.split(":");
            String name = var[0];
            String type = (var.length > 1 && var[1] != null && !var[1].isEmpty()) ? var[1] : "Object";
            dataInput.add(createDataInput(name, type, nodeId));
        }

        return dataInput;
    }

    public static List<DataOutput> parseDataOutputs(String assignments, String nodeId) {
        List<DataOutput> dataOutput = new ArrayList<>();
        String outputVariables = getOutputAssignmentVariables(assignments);
        String[] outVars = outputVariables.split(",");
        for (String outVar : outVars) {
            String[] var = outVar.split(":");
            String name = var[0];
            String type = (var.length > 1 && var[1] != null && !var[1].isEmpty()) ? var[1] : "Object";
            dataOutput.add(createDataOutput(name, type, nodeId));
        }

        return dataOutput;
    }

    public static List<OutputSet> getOutputSet(String dataIO, String nodeId) {
        List<OutputSet> outputSets = new ArrayList<>();
        List<DataOutput> outputs = AssignmentParser.parseDataOutputs(dataIO, nodeId);
        if (!outputs.isEmpty()) {
            OutputSet os = new OutputSet();
            os.getDataOutputRefs().add(new DataOutputRefs(outputs.get(0).getId()));
            outputSets.add(os);
        }
        return outputSets;
    }

    private static DataInput createDataInput(String name, String type, String nodeId) {
        DataInput dataInput = new DataInput();
        dataInput.setDtype(type);
        dataInput.setName(name);
        dataInput.setId(nodeId + "_" + name + "InputX");
        dataInput.setItemSubjectRef("_" + dataInput.getId() + "Item");

        return dataInput;
    }

    private static DataOutput createDataOutput(String name, String type, String nodeId) {
        DataOutput dataOutput = new DataOutput();
        dataOutput.setDtype(type);
        dataOutput.setName(name);
        dataOutput.setId(nodeId + "_" + name + "OutputX");
        dataOutput.setItemSubjectRef("_" + dataOutput.getId() + "Item");
        return dataOutput;
    }

    private static String getAssignmentsAssignmentVariables(String assignments) {
        if (assignments.isEmpty()) {
            return "";
        }

        String[] parts = assignments.split("\\|");

        if (parts.length >= 5 && !parts[4].isEmpty()) {
            return parts[4];
        }

        return "";
    }

    public static String getInputAssignmentVariables(String assignments) {
        if (assignments.isEmpty()) {
            return "";
        }

        String[] parts = assignments.split("\\|");

        if (!parts[0].isEmpty()) {
            return parts[0];
        }

        if (parts.length > 1 && !parts[1].isEmpty()) {
            return parts[1];
        }

        return "";
    }

    public static String getOutputAssignmentVariables(String assignments) {
        if (assignments.isEmpty()) {
            return "";
        }

        String[] parts = assignments.split("\\|");

        if (parts.length > 3 && !parts[2].isEmpty()) {
            return parts[2];
        }

        if (parts.length > 4 && !parts[3].isEmpty()) {
            return parts[3];
        }

        return "";
    }
}
