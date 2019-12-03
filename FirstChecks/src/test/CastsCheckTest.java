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

class CastsCheckTest extends AbstractModuleTestSupport {
	// Check if the elements in the arrays are ok
	@Test
	public void arraySizesTest() {
		CastsCheck castsCheck = new CastsCheck();
		assertEquals(2, castsCheck.getAcceptableTokens().length);
		assertEquals(0, castsCheck.getRequiredTokens().length);
		assertEquals(2, castsCheck.getDefaultTokens().length);
	}

	// Check if the beginTree is called properly
	@Test
	public void beginTreeCallTest() throws Exception {	
	    DetailAST ast = new DetailAST();
	    CastsCheck castsCheck = mock(CastsCheck.class);
	    doNothing().when(castsCheck).beginTree(isA(DetailAST.class));
	    castsCheck.beginTree(ast);
	    Mockito.verify(castsCheck, times(1)).beginTree(ast);
	}
	
	// Check if the visitToken is called properly
	@Test
	public void visitTokenCallTest() throws Exception {	
	    DetailAST ast = new DetailAST();
		CastsCheck castsCheck = mock(CastsCheck.class);
	    doNothing().when(castsCheck).visitToken(isA(DetailAST.class));
	    castsCheck.visitToken(ast);
	    Mockito.verify(castsCheck, times(1)).visitToken(ast);
	}
	
	// Check if the finishTree is called properly
	@Test
	public void finishTreeCallTest() throws Exception {	
	    DetailAST ast = new DetailAST();
	    CastsCheck castsCheck = mock(CastsCheck.class);
	    doNothing().when(castsCheck).finishTree(isA(DetailAST.class));
	    castsCheck.finishTree(ast);
	    Mockito.verify(castsCheck, times(1)).finishTree(ast);
	}

	// Test opening a file that does not exist
	@Test
	public void fileDoesNotExistTest() throws Exception {
		DefaultConfiguration dc = createModuleConfig(CastsCheck.class);
		String fileToTest = getPackageLocation() + "FakeFile.java";
		String result = "1: Got an exception - " + fileToTest + " (No such file or directory)";
		verify(dc, fileToTest, result);
	}
	
	// Check an empty file
	@Test
	public void zeroCountTest() throws Exception {
		// Test the actual running of the check
		DefaultConfiguration dc = createModuleConfig(CastsCheck.class);
		String fileToTest = getPackageLocation() + "EmptyClassTestCode.java";
		String result = "1: Number of casts: 0";
		verify(dc, fileToTest, result);
	}
	
	// Actually test the check
	@Test
	public void variablesCountTest() throws Exception {

		// Test the actual running of the check
		DefaultConfiguration dc = createModuleConfig(CastsCheck.class);
		String fileToTest = getPackageLocation() + "CastsCheckTestCode.java";
		String result = "1: Number of casts: 2";
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
