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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.mwe.SimpleValue;
import org.eclipse.emf.mwe.di.xml.Xml2MweConverter;
import org.eclipse.emf.mwe.di.xml.conversion.AttributeBasedSelector;
import org.w3c.dom.Node;

/**
 * @author Patrick Schoenbach - Initial API and implementation
 * @version $Revision: 1.1 $
 */

public class ValueCreator extends AbstractValueCreatorModule {

	public ValueCreator() {
		super();
		selector =
				new AttributeBasedSelector(
						new String[] { Xml2MweConverter.VALUE });
	}

	/**
	 * This automatically generated method overrides the implementation of
	 * <code>createValue</code> inherited from the superclass.
	 * 
	 * @see org.eclipse.emf.mwe.di.xml.conversion.ICreator#create(Node)
	 */
	public EObject create(final Node item) {
		final SimpleValue s = FACTORY.createSimpleValue();
		s.setValue(getAttribute(item, Xml2MweConverter.VALUE));
		return s;
	}

}