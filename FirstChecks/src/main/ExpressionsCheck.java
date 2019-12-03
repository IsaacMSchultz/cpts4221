package main;

import com.puppycrawl.tools.checkstyle.api.*;

public class ExpressionsCheck extends AbstractCheck {
	private int expressions = 0;
	private String logMsg = "Number of Expressions: ";

	@Override
	public int[] getRequiredTokens() {
		 return new int[0];
	}

	@Override
	public int[] getAcceptableTokens() {
		return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
	}

	@Override
	public int[] getDefaultTokens() {
		return new int[] { TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF };
	}

	@Override
	public void beginTree(DetailAST ast) {
		expressions = 0;
	}

	@Override
	public void finishTree(DetailAST ast) {
		log(ast.getLineNo(), logMsg + expressions);
	}

	@Override
	public void visitToken(DetailAST ast) {
		countTokens(ast);
	}

	private void countTokens(DetailAST ast) {
		// If the class has anything in it first
		if (ast.getChildCount() > 0) {

			expressions += ast.getChildCount(TokenTypes.EXPR);

			// Find first child, assuming first method
			DetailAST child = ast.getFirstChild();

			// Keep checking each method until we have no more
			while (child != null) {
				countTokens(child);
				child = child.getNextSibling();
			}
		}
	}
}
