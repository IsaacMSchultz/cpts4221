package main;

import java.util.*;

import com.puppycrawl.tools.checkstyle.api.*;

public class HalsteadMetricsCheck extends AbstractCheck {
	private int operatorsCount = 0;
	private int operandsCount = 0;
	private int uniqueOperators = 0;
	private int uniqueOperands = 0;
	private int cycTotal = 1;
	private List<String> globalVariables = new ArrayList<String>();
	private List<String> tempList = new ArrayList<String>();

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
		operandsCount = 0;
		uniqueOperators = 0;
		uniqueOperands = 0;
		cycTotal = 1;
		globalVariables.clear();
		tempList.clear();
	}

	@Override
	public void finishTree(DetailAST ast) {

		// Sum of total operators and operands
		int halsteadLength = operatorsCount + operandsCount;

		// Unique number of operators and operands
		int halsteadVocabulary = uniqueOperators + uniqueOperands;

		// Program length (N) times log_2 halsteadVocabulary
		double halsteadVolume = halsteadLength * (Math.log(halsteadVocabulary) / Math.log(2));

		// D = (uniqueOperators / 2) * (operandsCount / uniqueOperands)
		double halsteadDifficulty = (((double) uniqueOperators / 2)
				* (double) (operandsCount / (double) uniqueOperands));

		// halsteadVolume * halsteadDifficulty
		double halsteadEffort = halsteadDifficulty * halsteadVolume;

		// 171 - 52 * log_2(halsteadVolume) - 0.23 * (cycTotal) - 16.2 *
		// log_2(getLines().length)
		// Not doing comments lol f adding more
		double maintainabilityIndex = (171 - (5.2 * (Math.log(halsteadVolume) / Math.log(2)))) - (0.23 * cycTotal)
				- (16.2 * (Math.log(getLines().length) / Math.log(2)));
		
		log(ast.getLineNo(), "Number of Operators: " + operatorsCount);
		log(ast.getLineNo(), "Number of Operands: " + operandsCount);
		log(ast.getLineNo(), "Halstead Length: " + halsteadLength);
		log(ast.getLineNo(), "Halstead Vocabulary: " + halsteadVocabulary);
		log(ast.getLineNo(), "Halstead Difficulty: " + halsteadDifficulty);
		log(ast.getLineNo(), "Halstead Volume: " + halsteadVolume);
		log(ast.getLineNo(), "Halstead Effort: " + halsteadEffort);
		log(ast.getLineNo(), "Maintainability Index: " + maintainabilityIndex);
	}

	@Override
	public void visitToken(DetailAST ast) {

		DetailAST objBlock = ast.findFirstToken(TokenTypes.OBJBLOCK);

		for (int n : operatorTokens()) {
			if (objBlock.branchContains(n)) {
				uniqueOperators++;
			}
		}
		
		// Global operands
		DetailAST child = objBlock.getFirstChild();

		while (child != null) {
			if (child.getType() == TokenTypes.VARIABLE_DEF) {
				globalVariables.add(child.findFirstToken(TokenTypes.IDENT).getText());
				tempList.add(child.findFirstToken(TokenTypes.IDENT).getText());
				uniqueOperands++;
				operandsCount++;
				operandsCount += countOperands(child);
				operatorsCount += countOperators(child);
			}
			if (child.getType() == TokenTypes.METHOD_DEF) {
				// Add one for the open curly bracket for the statement list
				operatorsCount++;
				operandsCount += countOperands(child.findFirstToken(TokenTypes.SLIST));
				operatorsCount += countOperators(child.findFirstToken(TokenTypes.SLIST));
				tempList.clear();
			}
			child = child.getNextSibling();
		}

	}

	private int[] operatorTokens() {
		return new int[] { TokenTypes.ASSIGN, TokenTypes.PLUS, TokenTypes.MINUS, TokenTypes.DIV, TokenTypes.STAR,
				TokenTypes.MOD, TokenTypes.LCURLY, TokenTypes.RCURLY, TokenTypes.SLIST, TokenTypes.RPAREN,
				TokenTypes.LPAREN, TokenTypes.LITERAL_WHILE, TokenTypes.DO_WHILE, TokenTypes.LITERAL_FOR,
				TokenTypes.METHOD_CALL, TokenTypes.DOT, TokenTypes.LITERAL_SWITCH, TokenTypes.LITERAL_CASE,
				TokenTypes.LITERAL_IF, TokenTypes.LITERAL_ELSE, TokenTypes.LITERAL_RETURN, TokenTypes.LITERAL_BREAK,
				TokenTypes.COMMA, TokenTypes.RBRACK, TokenTypes.ARRAY_DECLARATOR, TokenTypes.ARRAY_INIT,
				TokenTypes.SEMI, TokenTypes.BOR, TokenTypes.BOR_ASSIGN, TokenTypes.BAND_ASSIGN, TokenTypes.BAND,
				TokenTypes.BSR_ASSIGN, TokenTypes.BSR, TokenTypes.BXOR_ASSIGN, TokenTypes.BXOR, TokenTypes.DIV_ASSIGN,
				TokenTypes.MINUS_ASSIGN, TokenTypes.MOD_ASSIGN, TokenTypes.PLUS_ASSIGN, TokenTypes.SL,
				TokenTypes.SL_ASSIGN, TokenTypes.BNOT, TokenTypes.SR_ASSIGN, TokenTypes.SR, TokenTypes.STAR_ASSIGN,
				TokenTypes.INC, TokenTypes.POST_INC, TokenTypes.DEC, TokenTypes.POST_DEC, TokenTypes.LITERAL_DEFAULT };
	}

	private int[] operandTokens() {
		return new int[] { TokenTypes.NUM_DOUBLE, TokenTypes.NUM_FLOAT, TokenTypes.NUM_INT, TokenTypes.NUM_LONG,
				TokenTypes.IDENT };
	}

	private int countOperators(DetailAST ast) {
		if (ast.getChildCount() > 0) {
			int count = 0;

			// Count default as normal pathing.
			if (ast.getType() == TokenTypes.LITERAL_IF || ast.getType() == TokenTypes.LITERAL_CASE) {
				cycTotal++;
			}

			for (int n : operatorTokens()) {
				count += ast.getChildCount(n);
			}

			// Find first child, assuming first method
			DetailAST child = ast.getFirstChild();

			// Keep checking each method until we have no more
			while (child != null) {
				count += countOperators(child);
				child = child.getNextSibling();
			}
			return count;

			// Means class/interface didn't have nothin
		} else {
			return 0;
		}
	}

	private int countOperands(DetailAST ast) {
		if (ast.getChildCount() > 0) {
			int count = 0;
			int temp = 0;

			// This gross thing cause duplicate global variables aren't unique
			for (int n : operandTokens()) {
				temp = ast.getChildCount(n);
				if (temp > 0) {
					DetailAST child = ast.getFirstChild();
					while (child != null) {
						if (child.getType() == TokenTypes.IDENT) {
							if (!globalVariables.contains(child.getText()) || !tempList.contains(child.getText())) {
								uniqueOperands++;
								tempList.add(child.getText());
							}
						}
						child = child.getNextSibling();
					}
					count += temp;
				}
			}

			DetailAST child = ast.getFirstChild();

			while (child != null) {
				count += countOperands(child);
				child = child.getNextSibling();
			}
			return count;
		} else {
			return 0;
		}
	}
}
