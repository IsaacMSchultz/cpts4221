package main;

import com.puppycrawl.tools.checkstyle.api.*;

public class ExternalMethodsCheck extends AbstractCheck {
	private int externalMethods = 0;

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
		externalMethods = 0;
	}

	@Override
	public void finishTree(DetailAST ast) {
		String catchMsg = "Number of external method references: ";
		log(ast.getLineNo(), catchMsg + externalMethods);
	}

	@Override
	public void visitToken(DetailAST ast) {
		while (ast != null) {
			if (ast.getChildCount() > 0) {
				// Global operands
				DetailAST child = ast.getFirstChild();
				while (child != null) {
					countTokens(child);
					child = child.getNextSibling();
				}
			}
			ast = ast.getNextSibling();
		}
	}

	private void countTokens(DetailAST ast) {
		// If the class has anything in it first
		if (ast.getChildCount() > 0) {
			if (ast.getType() == TokenTypes.METHOD_CALL) {
				if (!(ast.findFirstToken(TokenTypes.DOT).branchContains(TokenTypes.LITERAL_THIS))) {
					externalMethods++;
				}
			}

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
