/*
 * Copyright (c) 2008 committers of openArchitectureWare and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    committers of openArchitectureWare - initial API and implementation
 */

package org.eclipse.emf.mwe.di.xml.conversion.impl;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Patrick Schoenbach - Initial API and implementation
 * @version $Revision: 1.1 $
 */

public class DefaultPropertyInjector extends AbstractElementInjectorModule {

	public DefaultPropertyInjector() {
		alwaysApplicable();
	}

	/**
	 * This automatically generated method overrides the implementation of
	 * <code>inject</code> inherited from the superclass.
	 * 
	 * @see org.eclipse.emf.mwe.di.xml.conversion.IInjector#inject(Node)
	 */
	public void inject(final Node item) {
		final NodeList childNodes = item.getChildNodes();
		processProperties(childNodes);
	}

	private void processProperties(final NodeList childNodes) {
		if (childNodes == null)
			throw new IllegalArgumentException();

		for (int i = 0; i < childNodes.getLength(); i++) {
			final Node child = childNodes.item(i);
			if (isRelevantNode(child)) {
				converter.inject(child);
			}
		}
	}

}