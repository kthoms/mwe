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

package org.eclipse.emf.mwe.ui.internal.editor.tests.parser;

import org.eclipse.emf.mwe.ui.internal.editor.elements.IWorkflowElement;
import org.eclipse.emf.mwe.ui.internal.editor.tests.base.ParserTestBase;
import org.xml.sax.SAXException;

public class PropertyTest extends ParserTestBase {

	private static final String WORKFLOW1 = "<workflow>\n" + "</workflow>";

	private static final String WORKFLOW2 =
			"<workflow>\n" + "    <property name=\"foo\" value=\"bar\"/>\n"
					+ "</workflow>";

	private static final String WORKFLOW3 =
			"<workflow>\n" + "    <property file=\"foo\"/>\n" + "</workflow>";

	public void testEmptyWorkflow() throws SAXException {
		setUpDocument(WORKFLOW1);
		parser.parse(WORKFLOW1);
		final IWorkflowElement root = parser.getRootElement();
		final IWorkflowElement workflow = root.getChild(0);
		assertTrue(root.isWorkflowFile());
		assertTrue(workflow.isWorkflow());
	}

	public void testFileProperty() throws SAXException {
		setUpDocument(WORKFLOW3);
		parser.parse(WORKFLOW3);
		final IWorkflowElement root = parser.getRootElement();
		assertEquals(1, root.getChildrenCount());
		final IWorkflowElement workflow = root.getChild(0);
		assertEquals(1, workflow.getChildrenCount());
		final IWorkflowElement property = workflow.getChild(0);
		assertEquals(1, property.getAttributeCount());
		assertEquals("foo", property.getAttributeValue("file"));
	}

	public void testParserSetup() {
		assertNotNull(parser);
	}

	public void testSimpleProperty() throws SAXException {
		setUpDocument(WORKFLOW2);
		parser.parse(WORKFLOW2);
		final IWorkflowElement root = parser.getRootElement();
		assertEquals(1, root.getChildrenCount());
		final IWorkflowElement workflow = root.getChild(0);
		assertEquals(1, workflow.getChildrenCount());
		final IWorkflowElement property = workflow.getChild(0);
		assertEquals(2, property.getAttributeCount());
		assertEquals("foo", property.getAttributeValue("name"));
		assertEquals("bar", property.getAttributeValue("value"));
	}
}