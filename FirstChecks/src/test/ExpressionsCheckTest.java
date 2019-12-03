package test;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.*;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

import main.CastsCheck;
import main.ExpressionsCheck;
import main.HalsteadMetricsCheck;

class ExpressionsCheckTest extends AbstractModuleTestSupport {
	// Check if the elements in the arrays are ok
	@Test
	public void ExpressionsCheckArraySizesTest() {
		ExpressionsCheck expressionsCheck = new ExpressionsCheck();
		assertEquals(2, expressionsCheck.getAcceptableTokens().length);
		assertEquals(0, expressionsCheck.getRequiredTokens().length);
		assertEquals(2, expressionsCheck.getDefaultTokens().length);
	}

	// Check if the beginTree is called properly
	@Test
	public void beginTreeCallTest() throws Exception {
		DetailAST ast = new DetailAST();
		ExpressionsCheck expressionsCheck = mock(ExpressionsCheck.class);
		doNothing().when(expressionsCheck).beginTree(isA(DetailAST.class));
		expressionsCheck.beginTree(ast);
		Mockito.verify(expressionsCheck, times(1)).beginTree(ast);
	}

	// Check if the visitToken is called properly
	@Test
	public void visitTokenCallTest() throws Exception {
		DetailAST ast = new DetailAST();
		ExpressionsCheck expressionsCheck = mock(ExpressionsCheck.class);
		doNothing().when(expressionsCheck).visitToken(isA(DetailAST.class));
		expressionsCheck.visitToken(ast);
		Mockito.verify(expressionsCheck, times(1)).visitToken(ast);
	}

	// Check if the finishTree is called properly
	@Test
	public void finishTreeCallTest() throws Exception {
		DetailAST ast = new DetailAST();
		ExpressionsCheck expressionsCheck = mock(ExpressionsCheck.class);
		doNothing().when(expressionsCheck).finishTree(isA(DetailAST.class));
		expressionsCheck.finishTree(ast);
		Mockito.verify(expressionsCheck, times(1)).finishTree(ast);
	}

	// Test opening a file that does not exist
	@Test
	public void fileDoesNotExistTest() throws Exception {
		DefaultConfiguration dc = createModuleConfig(ExpressionsCheck.class);
		String fileToTest = getPackageLocation() + "FakeFile.java";
		String result = "1: Got an exception - " + fileToTest + " (No such file or directory)";
		verify(dc, fileToTest, result);
	}

	// Check an empty file
	@Test
	public void zeroCountTest() throws Exception {
		// Test the actual running of the check
		DefaultConfiguration dc = createModuleConfig(ExpressionsCheck.class);
		String fileToTest = getPackageLocation() + "EmptyClassTestCode.java";
		String result = "1: Number of Expressions: 0";
		verify(dc, fileToTest, result);
	}

	// Actually test the check
	@Test
	public void expressionsCountTest() throws Exception {

		// Test the actual running of the check
		DefaultConfiguration dc = createModuleConfig(ExpressionsCheck.class);
		String fileToTest = getPackageLocation() + "ExpressionsCheckTestCode.java";
		String result = "1: Number of Expressions: 4";
		verify(dc, fileToTest, result);
	}

	@Override
	protected String getPackageLocation() {

		// Replace TestCode with the directory or package the code to test is at
		String packageAndFileLocation = "\\src\\TestCode\\";
		try {
			String filePath = new java.io.File(".").getCanonicalPath() + packageAndFileLocation;
			return filePath;
		} catch (IOException e) {
			return null;
		}
	}
}
