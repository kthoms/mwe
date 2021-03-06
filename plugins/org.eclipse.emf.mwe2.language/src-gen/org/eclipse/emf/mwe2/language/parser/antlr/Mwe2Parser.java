/*
 * generated by Xtext
 */
package org.eclipse.emf.mwe2.language.parser.antlr;

import com.google.inject.Inject;

import org.eclipse.xtext.parser.antlr.XtextTokenStream;
import org.eclipse.emf.mwe2.language.services.Mwe2GrammarAccess;

public class Mwe2Parser extends org.eclipse.xtext.parser.antlr.AbstractAntlrParser {
	
	@Inject
	private Mwe2GrammarAccess grammarAccess;
	
	@Override
	protected void setInitialHiddenTokens(XtextTokenStream tokenStream) {
		tokenStream.setInitialHiddenTokens("RULE_WS", "RULE_ML_COMMENT", "RULE_SL_COMMENT");
	}
	
	@Override
	protected org.eclipse.emf.mwe2.language.parser.antlr.internal.InternalMwe2Parser createParser(XtextTokenStream stream) {
		return new org.eclipse.emf.mwe2.language.parser.antlr.internal.InternalMwe2Parser(stream, getGrammarAccess());
	}
	
	@Override 
	protected String getDefaultRuleName() {
		return "Module";
	}
	
	public Mwe2GrammarAccess getGrammarAccess() {
		return this.grammarAccess;
	}
	
	public void setGrammarAccess(Mwe2GrammarAccess grammarAccess) {
		this.grammarAccess = grammarAccess;
	}
	
}
