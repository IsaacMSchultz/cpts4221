package test;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.puppycrawl.tools.checkstyle.*;
import com.puppycrawl.tools.checkstyle.api.DetailAST;

import main.CastsCheck;
import main.HalsteadMetricsCheck;
import main.TotalCommentsCheck;

class TotalCommentsCheckTest extends AbstractModuleTestSupport {
	// Check if the elements in the arrays are ok
	@Test
	public void arraySizesTest() {
		TotalCommentsCheck totalCommentsCheck = new TotalCommentsCheck();
		assertEquals(1, totalCommentsCheck.getAcceptableTokens().length);
		assertEquals(0, totalCommentsCheck.getRequiredTokens().length);
		assertEquals(1, totalCommentsCheck.getDefaultTokens().length);
	}

	// Check if the beginTree is called properly
	@Test
	public void beginTreeCallTest() throws Exception {
		DetailAST ast = new DetailAST();
		TotalCommentsCheck totalCommentsCheck = mock(TotalCommentsCheck.class);
		doNothing().when(totalCommentsCheck).beginTree(isA(DetailAST.class));
		totalCommentsCheck.beginTree(ast);
		Mockito.verify(totalCommentsCheck, times(1)).beginTree(ast);
	}

	// Check if the visitToken is called properly
	@Test
	public void visitTokenCallTest() throws Exception {
		DetailAST ast = new DetailAST();
		TotalCommentsCheck totalCommentsCheck = mock(TotalCommentsCheck.class);
		doNothing().when(totalCommentsCheck).visitToken(isA(DetailAST.class));
		totalCommentsCheck.visitToken(ast);
		Mockito.verify(totalCommentsCheck, times(1)).visitToken(ast);
	}

	// Check if the finishTree is called properly
	@Test
	public void finishTreeCallTest() throws Exception {
		DetailAST ast = new DetailAST();
		TotalCommentsCheck totalCommentsCheck = mock(TotalCommentsCheck.class);
		doNothing().when(totalCommentsCheck).finishTree(isA(DetailAST.class));
		totalCommentsCheck.finishTree(ast);
		Mockito.verify(totalCommentsCheck, times(1)).finishTree(ast);
	}

	@Test
	public void isCommentNodesRequiredTest() {
		TotalCommentsCheck totalCommentsCheck = new TotalCommentsCheck();
		assertTrue(totalCommentsCheck.isCommentNodesRequired());
	}

	// Test opening a file that does not exist
	@Test
	public void fileDoesNotExistTest() throws Exception {
		DefaultConfiguration dc = createModuleConfig(TotalCommentsCheck.class);
		String fileToTest = getPackageLocation() + "FakeFile.java";
		String result = "1: Got an exception - " + fileToTest + " (No such file or directory)";
		verify(dc, fileToTest, result);
	}
	
	// Check an empty file
	@Test
	public void zeroCountTest() throws Exception {
		// Test the actual running of the check
		DefaultConfiguration dc = createModuleConfig(TotalCommentsCheck.class);
		String fileToTest = getPackageLocation() + "EmptyClassTestCode.java";
		String result = "1: Total Comments: 0";
		verify(dc, fileToTest, result);
	}
	
	// Actually test the check
	@Test
	public void externalMethodsCountTest() throws Exception {

		// Test the actual running of the check
		DefaultConfiguration dc = createModuleConfig(TotalCommentsCheck.class);
		String fileToTest = getPackageLocation() + "TotalCommentsCheckTestCode.java";
		String result = "1: Total Comments: 6";
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
