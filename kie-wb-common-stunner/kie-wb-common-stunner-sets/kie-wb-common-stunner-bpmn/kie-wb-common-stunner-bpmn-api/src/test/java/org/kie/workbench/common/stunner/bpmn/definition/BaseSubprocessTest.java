/*
 * Copyright 2018 Red Hat, Inc. and/or its affiliates.
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

package org.kie.workbench.common.stunner.bpmn.definition;

import java.util.Set;

import org.junit.Test;
import org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2.BaseSubprocess;
import org.kie.workbench.common.stunner.core.backend.definition.adapter.ReflectionAdapterUtils;
import org.kie.workbench.common.stunner.core.definition.annotation.definition.Labels;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class BaseSubprocessTest {

    @Test
    public void testBaseSubprocessCanBeContainedByALane() throws Exception {

        final FakeBaseSubprocess baseSubprocess = new FakeBaseSubprocess();
        final Set<String> labels = ReflectionAdapterUtils.getAnnotatedFieldValue(baseSubprocess, Labels.class);

        assertNotNull(labels);
        assertTrue(labels.contains("lane_child"));
    }

    private class FakeBaseSubprocess extends BaseSubprocess {

    }
}
