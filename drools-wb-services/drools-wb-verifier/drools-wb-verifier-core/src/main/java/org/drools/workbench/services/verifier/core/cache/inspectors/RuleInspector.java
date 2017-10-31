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

package org.drools.workbench.services.verifier.core.cache.inspectors;

import java.util.Collection;
import java.util.Set;

import org.drools.workbench.services.verifier.api.client.configuration.AnalyzerConfiguration;
import org.drools.workbench.services.verifier.api.client.index.Action;
import org.drools.workbench.services.verifier.api.client.index.ActionSuperType;
import org.drools.workbench.services.verifier.api.client.index.BRLAction;
import org.drools.workbench.services.verifier.api.client.index.BRLCondition;
import org.drools.workbench.services.verifier.api.client.index.Condition;
import org.drools.workbench.services.verifier.api.client.index.ConditionSuperType;
import org.drools.workbench.services.verifier.api.client.index.Conditions;
import org.drools.workbench.services.verifier.api.client.index.Field;
import org.drools.workbench.services.verifier.api.client.index.FieldCondition;
import org.drools.workbench.services.verifier.api.client.index.Pattern;
import org.drools.workbench.services.verifier.api.client.index.Rule;
import org.drools.workbench.services.verifier.api.client.index.keys.Key;
import org.drools.workbench.services.verifier.api.client.index.keys.UUIDKey;
import org.drools.workbench.services.verifier.api.client.index.select.AllListener;
import org.drools.workbench.services.verifier.api.client.maps.InspectorList;
import org.drools.workbench.services.verifier.api.client.maps.util.HasKeys;
import org.drools.workbench.services.verifier.api.client.relations.HumanReadable;
import org.drools.workbench.services.verifier.api.client.relations.IsConflicting;
import org.drools.workbench.services.verifier.api.client.relations.IsDeficient;
import org.drools.workbench.services.verifier.api.client.relations.IsRedundant;
import org.drools.workbench.services.verifier.api.client.relations.IsSubsuming;
import org.drools.workbench.services.verifier.core.cache.RuleInspectorCache;
import org.drools.workbench.services.verifier.core.cache.inspectors.action.ActionInspector;
import org.drools.workbench.services.verifier.core.cache.inspectors.action.ActionsInspectorMultiMap;
import org.drools.workbench.services.verifier.core.cache.inspectors.action.BRLActionInspector;
import org.drools.workbench.services.verifier.core.cache.inspectors.condition.BRLConditionInspector;
import org.drools.workbench.services.verifier.core.cache.inspectors.condition.ConditionInspector;
import org.drools.workbench.services.verifier.core.cache.inspectors.condition.ConditionsInspectorMultiMap;
import org.drools.workbench.services.verifier.core.checks.base.Check;
import org.drools.workbench.services.verifier.core.checks.base.CheckStorage;
import org.kie.soup.commons.validation.PortablePreconditions;

public class RuleInspector
        implements IsRedundant,
                   IsSubsuming,
                   IsConflicting,
                   IsDeficient<RuleInspector>,
                   HumanReadable,
                   HasKeys {

    private final Rule rule;

    private final CheckStorage checkStorage;
    private final RuleInspectorCache cache;
    private final AnalyzerConfiguration configuration;

    private final UUIDKey uuidKey;

    private final InspectorList<PatternInspector> patternInspectorList;
    private final InspectorList<ConditionInspector> brlConditionsInspectors;
    private final InspectorList<ActionInspector> brlActionInspectors;
    private InspectorList<ActionsInspectorMultiMap> actionsInspectors = null;
    private InspectorList<ConditionsInspectorMultiMap> conditionsInspectors = null;

    public RuleInspector(final Rule rule,
                         final CheckStorage checkStorage,
                         final RuleInspectorCache cache,
                         final AnalyzerConfiguration configuration) {
        this.rule = PortablePreconditions.checkNotNull("rule",
                                                       rule);
        this.checkStorage = PortablePreconditions.checkNotNull("checkStorage",
                                                               checkStorage);
        this.cache = PortablePreconditions.checkNotNull("cache",
                                                        cache);
        this.configuration = PortablePreconditions.checkNotNull("configuration",
                                                                configuration);

        uuidKey = configuration.getUUID(this);
        patternInspectorList = new InspectorList<>(configuration);
        brlConditionsInspectors = new InspectorList<>(true,
                                                      configuration);
        brlActionInspectors = new InspectorList<>(true,
                                                  configuration);

        makePatternsInspectors();
        makeBRLActionInspectors();
        makeBRLConditionInspectors();

        makeChecks();
    }

    private void makeConditionsInspectors() {
        conditionsInspectors = new InspectorList<>(true,
                                                   configuration);

        for (final PatternInspector patternInspector : patternInspectorList) {
            conditionsInspectors.add(patternInspector.getConditionsInspector());
        }
    }

    private void makeActionsInspectors() {
        actionsInspectors = new InspectorList<>(true,
                                                configuration);

        for (final PatternInspector patternInspector : patternInspectorList) {
            actionsInspectors.add(patternInspector.getActionsInspector());
        }
    }

    private void makeBRLConditionInspectors() {
        updateBRLConditionInspectors(rule.getConditions()
                                             .where(Condition.superType()
                                                            .is(ConditionSuperType.BRL_CONDITION))
                                             .select()
                                             .all());
        rule.getConditions()
                .where(Condition.superType()
                               .is(ConditionSuperType.BRL_CONDITION))
                .listen()
                .all(new AllListener<Condition>() {
                    @Override
                    public void onAllChanged(final Collection<Condition> all) {
                        updateBRLConditionInspectors(all);
                    }
                });
    }

    private void makeBRLActionInspectors() {
        updateBRLActionInspectors(rule.getActions()
                                          .where(Action.superType()
                                                         .is(ActionSuperType.BRL_ACTION))
                                          .select()
                                          .all());
        rule.getActions()
                .where(Action.superType()
                               .is(ActionSuperType.BRL_ACTION))
                .listen()
                .all(new AllListener<Action>() {
                    @Override
                    public void onAllChanged(final Collection<Action> all) {
                        updateBRLActionInspectors(all);
                    }
                });
    }

    private void makePatternsInspectors() {
        for (final Pattern pattern : rule.getPatterns()
                .where(Pattern.uuid()
                               .any())
                .select()
                .all()) {
            final PatternInspector patternInspector = new PatternInspector(pattern,
                                                                           new RuleInspectorUpdater() {

                                                                               @Override
                                                                               public void resetActionsInspectors() {
                                                                                   actionsInspectors = null;
                                                                               }

                                                                               @Override
                                                                               public void resetConditionsInspectors() {
                                                                                   conditionsInspectors = null;
                                                                               }
                                                                           },
                                                                           configuration);

            patternInspectorList.add(patternInspector);
        }
    }

    private void updateBRLConditionInspectors(final Collection<Condition> conditions) {
        this.brlConditionsInspectors.clear();
        for (final Condition condition : conditions) {
            this.brlConditionsInspectors.add(new BRLConditionInspector((BRLCondition) condition,
                                                                       configuration));
        }
    }

    private void updateBRLActionInspectors(final Collection<Action> actions) {
        this.brlActionInspectors.clear();
        for (final Action action : actions) {
            this.brlActionInspectors.add(new BRLActionInspector((BRLAction) action,
                                                                configuration));
        }
    }

    public InspectorList<ConditionsInspectorMultiMap> getConditionsInspectors() {
        if (conditionsInspectors == null) {
            makeConditionsInspectors();
        }
        return conditionsInspectors;
    }

    public InspectorList<ActionsInspectorMultiMap> getActionsInspectors() {
        if (actionsInspectors == null) {
            makeActionsInspectors();
        }
        return actionsInspectors;
    }

    public InspectorList<PatternInspector> getPatternsInspector() {
        return patternInspectorList;
    }

    public int getRowIndex() {
        return rule.getRowNumber();
    }

    public RuleInspectorCache getCache() {
        return cache;
    }

    @Override
    public boolean isRedundant(final Object other) {
        return other instanceof RuleInspector
                && brlConditionsInspectors.isRedundant(((RuleInspector) other).brlConditionsInspectors)
                && brlActionInspectors.isRedundant(((RuleInspector) other).brlActionInspectors)
                && getActionsInspectors().isRedundant(((RuleInspector) other).getActionsInspectors())
                && getConditionsInspectors().isRedundant(((RuleInspector) other).getConditionsInspectors());
    }

    @Override
    public boolean subsumes(final Object other) {
        return other instanceof RuleInspector
                && brlActionInspectors.subsumes(((RuleInspector) other).brlActionInspectors)
                && brlConditionsInspectors.subsumes(((RuleInspector) other).brlConditionsInspectors)
                && getActionsInspectors().subsumes(((RuleInspector) other).getActionsInspectors())
                && getConditionsInspectors().subsumes(((RuleInspector) other).getConditionsInspectors());
    }

    @Override
    public boolean conflicts(final Object other) {
        if (other instanceof RuleInspector) {
            if (getActionsInspectors().conflicts(((RuleInspector) other).getActionsInspectors())) {
                if (getConditionsInspectors().subsumes(((RuleInspector) other).getConditionsInspectors())
                        && getBrlConditionsInspectors().subsumes(((RuleInspector) other).getBrlConditionsInspectors())) {
                    return true;
                }
            }
        }
        return false;
    }

    public Rule getRule() {
        return rule;
    }

    @Override
    public boolean isDeficient(final RuleInspector other) {

        if (other.atLeastOneActionHasAValue() && !getActionsInspectors().conflicts(other.getActionsInspectors())) {
            return false;
        }

        final Collection<Condition> allConditionsFromTheOtherRule = other.rule.getConditions()
                .where(Condition.value()
                               .any())
                .select()
                .all();

        if (allConditionsFromTheOtherRule.isEmpty()) {
            return true;
        } else {

            for (final Condition condition : allConditionsFromTheOtherRule) {

                if (condition.getValues() == null) {
                    continue;
                }

                if (condition instanceof FieldCondition) {
                    final FieldCondition fieldCondition = (FieldCondition) condition;
                    final Conditions conditions = rule.getPatterns()
                            .where(Pattern.name()
                                           .is(fieldCondition.getField()
                                                       .getFactType()))
                            .select()
                            .fields()
                            .where(Field.name()
                                           .is(fieldCondition.getField()
                                                       .getName()))
                            .select()
                            .conditions();
                    if (conditions
                            .where(Condition.value()
                                           .any())
                            .select()
                            .exists()) {
                        return false;
                    }
                }
            }

            return true;
        }
    }

    public boolean isEmpty() {
        return !atLeastOneConditionHasAValue() && !atLeastOneActionHasAValue();
    }

    public boolean atLeastOneActionHasAValue() {
        final int amountOfActions = rule.getActions()
                .where(Action.value()
                               .any())
                .select()
                .all()
                .size();
        return amountOfActions > 0;
    }

    public boolean atLeastOneConditionHasAValue() {
        final int amountOfConditions = rule.getConditions()
                .where(Condition.value()
                               .any())
                .select()
                .all()
                .size();
        return amountOfConditions > 0;
    }

    @Override
    public String toHumanReadableString() {
        return rule.getRowNumber()
                .toString();
    }

    public InspectorList<ConditionInspector> getBrlConditionsInspectors() {
        return brlConditionsInspectors;
    }

    public InspectorList<ActionInspector> getBrlActionInspectors() {
        return brlActionInspectors;
    }

    @Override
    public UUIDKey getUuidKey() {
        return uuidKey;
    }

    @Override
    public Key[] keys() {
        return new Key[]{
                uuidKey
        };
    }

    public Set<Check> getChecks() {
        return checkStorage.getChecks(this);
    }

    private void makeChecks() {
        checkStorage.makeChecks(this);
    }

    public Set<Check> clearChecks() {
        return checkStorage.remove(this);
    }
}
