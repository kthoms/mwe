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

import org.eclipse.emf.mwe.PropertiesFileImport;
import org.eclipse.emf.mwe.di.xml.Xml2MweConverter;
import org.eclipse.emf.mwe.di.xml.conversion.NameAttributeBasedSelector;
import org.w3c.dom.Node;

/**
 * @author Patrick Schoenbach - Initial API and implementation
 * @version $Revision: 1.1 $
 */

public class PropertyFileImportInjector extends AbstractElementInjectorModule {

	private static final String[] NAMES = { Xml2MweConverter.PROPERTY };

	private static final String[] ATTRIBUTES = { Xml2MweConverter.FILE };

	public PropertyFileImportInjector() {
		super();
		selector = new NameAttributeBasedSelector(NAMES, ATTRIBUTES);
	}

	/**
	 * This automatically generated method overrides the implementation of
	 * <code>createValue</code> inherited from the superclass.
	 * 
	 * @see org.eclipse.emf.mwe.di.xml.conversion.IInjector#inject(Node)
	 */
	public void inject(final Node item) {
		final PropertiesFileImport imp = FACTORY.createPropertiesFileImport();
		imp.setFile(getAttribute(item, Xml2MweConverter.FILE));
		converter.addToCollection(imp);
	}
}