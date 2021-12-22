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
package org.kie.workbench.common.stunner.bpmn.definition.models.bpmn2;

import java.util.Objects;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.kie.workbench.common.stunner.core.util.HashUtil;

@XmlRootElement(name = "messageEventDefinition", namespace = "http://www.omg.org/spec/BPMN/20100524/MODEL")
public class MessageEventDefinition {

    @XmlAttribute(namespace = "http://www.jboss.org/drools")
    private String msgref;

    @XmlAttribute
    private String messageRef;

    public MessageEventDefinition() {

    }

    public MessageEventDefinition(String droolsMessageName, String messageId) {
        this.msgref = droolsMessageName;
        this.messageRef = messageId;
    }

    public String getMsgref() {
        return msgref;
    }

    public void setMsgref(String msgref) {
        this.msgref = msgref;
    }

    public String getMessageRef() {
        return messageRef;
    }

    public void setMessageRef(String messageRef) {
        this.messageRef = messageRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MessageEventDefinition)) {
            return false;
        }
        MessageEventDefinition that = (MessageEventDefinition) o;
        return Objects.equals(getMsgref(), that.getMsgref())
                && Objects.equals(getMessageRef(), that.getMessageRef());
    }

    @Override
    public int hashCode() {
        return HashUtil.combineHashCodes(Objects.hashCode(msgref),
                                         Objects.hashCode(messageRef));
    }
}
