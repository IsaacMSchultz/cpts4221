package main;

import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.api.*;

public class OperatorsCheck extends AbstractCheck {
	private int operatorsCount = 0;

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
		operatorsCount = 0;
	}

	@Override
	public void finishTree(DetailAST ast) {
		String catchMsg = "Number of Operators: ";
		log(ast.getLineNo(), catchMsg + operatorsCount);
	}

	@Override
	public void visitToken(DetailAST ast) {
		DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
		Arrays.stream(tokenTypes()).forEach(x -> operatorsCount += countTokens(objBlock, x));
	}

	// I consider operators as =, +, -, /, *, and %
	private int[] tokenTypes() {
		return new int[] { TokenTypes.ASSIGN, TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.DIV, TokenTypes.STAR,
				TokenTypes.MOD };
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

			// Means class/interface didn't have nothin
		} else {
			return 0;
		}
	}
}
