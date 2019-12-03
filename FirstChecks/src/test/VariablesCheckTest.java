package test;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.*;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

import main.CastsCheck;
import main.HalsteadMetricsCheck;
import main.VariablesCheck;

class VariablesCheckTest extends AbstractModuleTestSupport {
	// Check if the elements in the arrays are ok
	@Test
	public void arraySizesTest() {
		VariablesCheck variablesCheck = new VariablesCheck();
		assertEquals(2, variablesCheck.getAcceptableTokens().length);
		assertEquals(0, variablesCheck.getRequiredTokens().length);
		assertEquals(2, variablesCheck.getDefaultTokens().length);
	}

	// Check if the beginTree is called properly
	@Test
	public void beginTreeCallTest() throws Exception {	
	    DetailAST ast = new DetailAST();
	    VariablesCheck variablesCheck = mock(VariablesCheck.class);
	    doNothing().when(variablesCheck).beginTree(isA(DetailAST.class));
	    variablesCheck.beginTree(ast);
	    Mockito.verify(variablesCheck, times(1)).beginTree(ast);
	}
	
	// Check if the visitToken is called properly
	@Test
	public void visitTokenCallTest() throws Exception {	
	    DetailAST ast = new DetailAST();
		VariablesCheck variablesCheck = mock(VariablesCheck.class);
	    doNothing().when(variablesCheck).visitToken(isA(DetailAST.class));
	    variablesCheck.visitToken(ast);
	    Mockito.verify(variablesCheck, times(1)).visitToken(ast);
	}
	
	// Check if the finishTree is called properly
	@Test
	public void finishTreeCallTest() throws Exception {	
	    DetailAST ast = new DetailAST();
	    VariablesCheck variablesCheck = mock(VariablesCheck.class);
	    doNothing().when(variablesCheck).finishTree(isA(DetailAST.class));
	    variablesCheck.finishTree(ast);
	    Mockito.verify(variablesCheck, times(1)).finishTree(ast);
	}

	// Test opening a file that does not exist
	@Test
	public void fileDoesNotExistTest() throws Exception {
		DefaultConfiguration dc = createModuleConfig(VariablesCheck.class);
		String fileToTest = getPackageLocation() + "FakeFile.java";
		String result = "1: Got an exception - " + fileToTest + " (No such file or directory)";
		verify(dc, fileToTest, result);
	}
	
	// Check an empty file
	@Test
	public void zeroCountTest() throws Exception {
		// Test the actual running of the check
		DefaultConfiguration dc = createModuleConfig(VariablesCheck.class);
		String fileToTest = getPackageLocation() + "EmptyClassTestCode.java";
		String result = "1: Number of Variables: 0";
		verify(dc, fileToTest, result);
	}
	
	// Actually test the check
	@Test
	public void variablesCountTest() throws Exception {

		// Test the actual running of the check
		DefaultConfiguration dc = createModuleConfig(VariablesCheck.class);
		String fileToTest = getPackageLocation() + "VariablesCheckTestCode.java";
		String result = "1: Number of Variables: 9";
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
