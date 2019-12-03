package main;

import java.util.Arrays;

import com.puppycrawl.tools.checkstyle.api.*;

public class LoopsCheck extends AbstractCheck {
	private int loops = 0;

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
		loops = 0;
	}

	@Override
	public void finishTree(DetailAST ast) {
		// Edit what you want the warning area to display
		
		String catchMsg = "Number of Loops: ";
		log(ast.getLineNo(), catchMsg + loops);
	}

	@Override
	public void visitToken(DetailAST ast) {
		DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);
		Arrays.stream(tokenTypes()).forEach(x -> loops += countTokens(objBlock, x));
	}

	private int[] tokenTypes() {
		return new int[] { TokenTypes.LITERAL_FOR, TokenTypes.LITERAL_WHILE, TokenTypes.LITERAL_DO};
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
