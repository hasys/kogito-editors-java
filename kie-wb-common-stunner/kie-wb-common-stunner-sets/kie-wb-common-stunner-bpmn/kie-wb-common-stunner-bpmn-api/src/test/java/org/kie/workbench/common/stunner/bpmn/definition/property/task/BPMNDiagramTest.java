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

package org.kie.workbench.common.stunner.bpmn.definition.property.task;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Before;
import org.junit.Test;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.Process;
import org.kie.workbench.common.stunner.bpmn.definition.property.diagram.GlobalVariables;
import org.kie.workbench.common.stunner.bpmn.definition.property.diagram.MetaDataAttributes;
import org.kie.workbench.common.stunner.bpmn.definition.property.dimensions.RectangleDimensionsSet;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.ProcessData;
import org.kie.workbench.common.stunner.bpmn.definition.property.variables.RootProcessAdvancedData;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class BPMNDiagramTest {

    private Validator validator;

    private static final String NAME_VALID = "My New BP";
    private static final String NAME_INVALID = "";

    private static final String ID_VALID = "Project1.MyNewBP";
    private static final String ID_INVALID = "";

    private static final String PACKAGE_VALID = "myorg.project1";
    private static final String PACKAGE_INVALID = "";

    private static final String VERSION_VALID = "1.0";
    private static final String VERSION_INVALID = "";
    private final String GLOBAL_VARIABLES = "GV1:Boolean, GV2:Boolean, GV3:Integer";
    private final String METADATA = "securityRolesß<![CDATA[employees,managers]]>Ø securityRoles2ß<![CDATA[admin,managers]]>";

    @Before
    public void init() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        this.validator = vf.getValidator();
    }

    public Process createValidBpmnDiagram() {
        Process process = new Process();
        process.setName(NAME_VALID);
        process.setId(ID_VALID);
        process.setPackageName(PACKAGE_VALID);
        process.setVersion(VERSION_VALID);

        return process;
    }

    @Test
    public void testAllValid() {
        Process Process = createValidBpmnDiagram();
        Set<ConstraintViolation<Process>> violations = this.validator.validate(Process);
        assertTrue(violations.isEmpty());
    }

    @Test
    public void testNameInvalid() {
        Process Process = createValidBpmnDiagram();
        Process.setName(NAME_INVALID);
        Set<ConstraintViolation<Process>> violations = this.validator.validate(Process);
        assertEquals(1,
                     violations.size());
    }

    @Test
    public void testIDInvalid() {
        Process Process = createValidBpmnDiagram();
        Process.setId(ID_INVALID);
        Set<ConstraintViolation<Process>> violations = this.validator.validate(Process);
        assertEquals(1,
                     violations.size());
    }

    @Test
    public void testPackageInvalid() {
        Process Process = createValidBpmnDiagram();
        Process.setPackageName(PACKAGE_INVALID);
        Set<ConstraintViolation<Process>> violations = this.validator.validate(Process);
        assertEquals(1,
                     violations.size());
    }

    @Test
    public void testVersionInvalid() {
        Process Process = createValidBpmnDiagram();
        Process.setVersion(VERSION_INVALID);
        Set<ConstraintViolation<Process>> violations = this.validator.validate(Process);
        assertEquals(1,
                     violations.size());
    }

    @Test
    public void testSetGlobalVariables() {
        RootProcessAdvancedData advancedData = new RootProcessAdvancedData();
        assertEquals(advancedData.getGlobalVariables(), new GlobalVariables());

        advancedData.setGlobalVariables(new GlobalVariables(GLOBAL_VARIABLES));
        assertEquals(advancedData.getGlobalVariables(), new GlobalVariables(GLOBAL_VARIABLES));
    }

    @Test
    public void testSetMetaDataAttributes() {
        RootProcessAdvancedData advancedData = new RootProcessAdvancedData();
        assertEquals(advancedData.getMetaDataAttributes(), new MetaDataAttributes());

        advancedData.setMetaDataAttributes(new MetaDataAttributes(METADATA));
        assertEquals(advancedData.getMetaDataAttributes(), new MetaDataAttributes(METADATA));
    }

    @Test
    public void testSetAdvancedData() {
        Process Process = createValidBpmnDiagram();
        Process.setAdvancedData(new RootProcessAdvancedData(new GlobalVariables(GLOBAL_VARIABLES),
                                                            new MetaDataAttributes(METADATA)));
        RootProcessAdvancedData advancedData = new RootProcessAdvancedData(new GlobalVariables(GLOBAL_VARIABLES),
                                                                           new MetaDataAttributes(METADATA));

        assertEquals(advancedData, Process.getAdvancedData());
        assertEquals(advancedData.getGlobalVariables(), Process.getAdvancedData().getGlobalVariables());
        assertEquals(advancedData.getMetaDataAttributes(), Process.getAdvancedData().getMetaDataAttributes());
    }

    @Test
    public void testAdvancedDataConstructors() {
        RootProcessAdvancedData advancedData = new RootProcessAdvancedData(new GlobalVariables(GLOBAL_VARIABLES),
                                                                           new MetaDataAttributes(METADATA));
        RootProcessAdvancedData advancedData2 = new RootProcessAdvancedData(GLOBAL_VARIABLES, METADATA);

        assertEquals(advancedData, advancedData2);
    }

    @Test
    public void testNotAdvancedData() {
        ProcessData processData = new ProcessData(GLOBAL_VARIABLES);
        RootProcessAdvancedData advancedData = new RootProcessAdvancedData(new GlobalVariables(GLOBAL_VARIABLES),
                                                                           new MetaDataAttributes(METADATA));

        assertNotEquals(advancedData, processData);
    }

    @Test
    public void testNotEqualsAdvancedData() {
        Process Process = createValidBpmnDiagram();
        Process.setAdvancedData(new RootProcessAdvancedData(new GlobalVariables(GLOBAL_VARIABLES),
                                                            new MetaDataAttributes(METADATA)));
        RootProcessAdvancedData advancedData = new RootProcessAdvancedData(new GlobalVariables(), new MetaDataAttributes());

        assertNotEquals(advancedData, Process.getAdvancedData());

        assertNotEquals(advancedData.getGlobalVariables(), Process.getAdvancedData().getGlobalVariables());
        assertNotEquals(advancedData.getMetaDataAttributes(), Process.getAdvancedData().getMetaDataAttributes());
    }

    @Test
    public void testBPMNDiagramEquals() {
        Process Process = createValidBpmnDiagram();
        Process.setAdvancedData(new RootProcessAdvancedData(new GlobalVariables(GLOBAL_VARIABLES),
                                                            new MetaDataAttributes(METADATA)));
        Process Process2 = createValidBpmnDiagram();
        Process2.setAdvancedData(new RootProcessAdvancedData(new GlobalVariables(GLOBAL_VARIABLES),
                                                             new MetaDataAttributes(METADATA)));

        assertEquals(Process, Process2);

        Process.setAdvancedData(new RootProcessAdvancedData(new GlobalVariables("id:"),
                                                            new MetaDataAttributes("securityRoles3ß<![CDATA[employees,clients]]>")));
        assertNotEquals(Process, Process2);

        Process.setAdvancedData(new RootProcessAdvancedData(new GlobalVariables(GLOBAL_VARIABLES),
                                                            new MetaDataAttributes("securityRoles3ß<![CDATA[employees,clients]]>")));
        assertNotEquals(Process, Process2);

        Process.setAdvancedData(new RootProcessAdvancedData(new GlobalVariables("id:"),
                                                            new MetaDataAttributes(METADATA)));
        assertNotEquals(Process, Process2);

        Process.setAdvancedData(new RootProcessAdvancedData(new GlobalVariables(GLOBAL_VARIABLES),
                                                            new MetaDataAttributes(METADATA)));
        assertEquals(Process, Process2);

        Process.setDimensionsSet(new RectangleDimensionsSet(10d, 10d));
        Process2.setDimensionsSet(new RectangleDimensionsSet(20d, 20d));
        assertNotEquals(Process, Process2);

        Process2.setDimensionsSet(new RectangleDimensionsSet(10d, 10d));
        assertEquals(Process, Process2);
    }
}