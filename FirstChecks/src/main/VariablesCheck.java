package main;

import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.api.*;

// Implemented using the Boat Anchor AntiPattern
public class VariablesCheck extends AbstractCheck {
	private int variablesCount = 0;

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
		variablesCount = 0;
	}

	@Override
	public void finishTree(DetailAST ast) {
		
		String catchMsg = "Number of Variables: ";
		log(ast.getLineNo(), catchMsg + variablesCount);
	}

	@Override
	public void visitToken(DetailAST ast) {
		DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
		Arrays.stream(tokenTypes()).forEach(x -> variablesCount += countTokens(objBlock, x));
	}

	private int[] tokenTypes() {
		return new int[] { TokenTypes.VARIABLE_DEF };
	}

	private int countTokens(DetailAST ast, int tokenTypes) {
		// If the class has anything in it first
		if (ast.getChildCount() > 0) {
			int count = 0;
			count += ast.getChildCount(tokenTypes);

			// Find first child, assuming first method
			DetailAST child = ast.getFirstChild();

			// Keep checking each method until we have no more
			while (child != null) {
				count += countTokens(child, tokenTypes);
				child = child.getNextSibling();
			}
			return count;

			// Means class/interface didn't have any variable defs
		} else {
			return 0;
		}
	}
}
