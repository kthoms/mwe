/*
 * Copyright (c) 2008 itemis AG and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 */

package org.eclipse.emf.mwe.di.ui.analyze.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.mwe.LocalVariable;
import org.eclipse.emf.mwe.Value;
import org.eclipse.emf.mwe.di.types.StaticType;

public class VariableRegistry implements IMergeable {

	private EObject context;
	private final Map<String, LocalVariableDefinition> variables = new HashMap<String, LocalVariableDefinition>();

	public static boolean isReference(final String text) {
		final Matcher m = LocalVariableDefinition.REFERENCE_PATTERN.matcher(text);
		return m.matches() && m.start() == 0 && m.end() == text.length();
	}

	public static final String referenceName(final String text) {
		if (!isReference(text))
			return null;

		final Matcher m = LocalVariableDefinition.REFERENCE_PATTERN.matcher(text);
		m.find();
		return m.group(1);
	}

	public void addVariable(final LocalVariable variable, final boolean imported) {
		if (variable == null)
			throw new IllegalArgumentException();
		else if (getContext() == null)
			throw new IllegalStateException("No context set");

		if (variables.containsKey(variable.getName()))
			throw new DuplicateElementException("Variable '" + variable.getName() + "' is already defined", variable);

		final int definitionPosition = size();
		final LocalVariableDefinition def = new LocalVariableDefinition(variable, definitionPosition, imported, context);
		variables.put(def.getName(), def);
	}

	public void clear() {
		variables.clear();
	}

	public EObject getContext() {
		return context;
	}

	public Set<String> getDeclarations() {
		final Set<String> result = new HashSet<String>();
		final Set<String> names = getVariableNames(true);
		for (final String n : names) {
			if (isDeclarationOnly(n)) {
				result.add(n);
			}
		}
		return result;
	}

	public String getIdRef(final String name) {
		final LocalVariableDefinition def = getDefinition(name);
		if (isIdRef(name))
			return def.getId();

		return null;
	}

	public String getSimpleValue(final String name) {
		final LocalVariableDefinition def = getDefinition(name);
		if (isSimpleValue(name))
			return def.getSimpleValue();

		return null;
	}

	public StaticType getType(final String name) {
		if (isBean(name)) {
			final LocalVariableDefinition def = getDefinition(name);
			return def.getType();
		}

		return null;
	}

	public List<String> getUnresolvedReferences(final String name, final boolean includeUndefinedReferences) {
		final List<String> result = new ArrayList<String>();
		final LocalVariableDefinition secondDef = getDefinition(name);
		if (secondDef != null) {
			final List<String> references = secondDef.getReferences();
			for (final String r : references) {
				final LocalVariableDefinition firstDef = getDefinition(r);
				if (firstDef == null || !isDefinedBefore(firstDef, secondDef)) {
					result.add(r);
				}
			}
		}
		else if (includeUndefinedReferences) {
			result.add(name);
		}

		return result;
	}

	public Value getValue(final String name) {
		final LocalVariableDefinition def = getDefinition(name);
		return def != null ? def.getValue() : null;
	}

	public Set<String> getVariableNames(final boolean excludeImported) {
		if (excludeImported) {
			final Set<String> result = new HashSet<String>();
			final Set<String> keys = variables.keySet();
			for (final String k : keys) {
				final LocalVariableDefinition def = variables.get(k);
				if (!def.isImported()) {
					result.add(def.getName());
				}
			}
			return result;
		}

		return variables.keySet();
	}

	public boolean hasVariable(final String name) {
		if (name == null)
			return false;

		return variables.containsKey(name);
	}

	public boolean isBean(final String name) {
		if (name == null)
			return false;

		final LocalVariableDefinition def = getDefinition(name);
		return def != null && def.isBean();
	}

	public boolean isDeclarationOnly(final String name) {
		final LocalVariableDefinition def = getDefinition(name);
		return def != null ? def.isDeclarationOnly() : false;
	}

	public boolean isDefinedBefore(final String name, final int definitionPosition) {
		if (!hasVariable(name) || definitionPosition < 0)
			return false;

		final LocalVariableDefinition def = getDefinition(name);
		return def.getDefinitionPosition() < definitionPosition;
	}

	public boolean isEmpty() {
		return variables.isEmpty();
	}

	public boolean isIdRef(final String name) {
		final LocalVariableDefinition def = getDefinition(name);
		return def != null ? def.isIdRef() : false;
	}

	public boolean isSimpleValue(final String name) {
		final LocalVariableDefinition def = getDefinition(name);
		return def != null ? def.isSimpleValue() : false;
	}

	public void merge(final IMergeable other) {
		if (other instanceof VariableRegistry) {
			final VariableRegistry o = (VariableRegistry) other;
			variables.putAll(o.getVariables());
		}
	}

	public void setContext(final EObject context) {
		if (context == null)
			throw new IllegalArgumentException();

		this.context = context;
	}

	public void setType(final String name, final StaticType type) {
		if (!hasVariable(name))
			throw new IllegalArgumentException("No such variable");

		final LocalVariableDefinition def = getDefinition(name);
		def.setType(type);
	}

	public int size() {
		return variables.size();
	}

	protected Map<String, LocalVariableDefinition> getVariables() {
		return variables;
	}

	private LocalVariableDefinition getDefinition(final String name) {
		if (name == null)
			return null;

		return variables.get(name);
	}

	private boolean isDefinedBefore(final LocalVariableDefinition firstDef, final LocalVariableDefinition secondDef) {
		if (firstDef == null || secondDef == null)
			return false;

		return firstDef.getDefinitionPosition() < secondDef.getDefinitionPosition();
	}
}