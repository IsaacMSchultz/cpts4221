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

class HalsteadMetricsCheckTest extends AbstractModuleTestSupport {
	// Check if the elements in the arrays are ok
	@Test
	public void arraySizesTest() {
		HalsteadMetricsCheck halsteadMetricsCheck = new HalsteadMetricsCheck();
		assertEquals(2, halsteadMetricsCheck.getAcceptableTokens().length);
		assertEquals(0, halsteadMetricsCheck.getRequiredTokens().length);
		assertEquals(2, halsteadMetricsCheck.getDefaultTokens().length);
	}

	// Check if the beginTree is called properly
	@Test
	public void beginTreeCallTest() throws Exception {
		DetailAST ast = new DetailAST();
		HalsteadMetricsCheck halsteadMetricsCheck = mock(HalsteadMetricsCheck.class);
		doNothing().when(halsteadMetricsCheck).beginTree(isA(DetailAST.class));
		halsteadMetricsCheck.beginTree(ast);
		Mockito.verify(halsteadMetricsCheck, times(1)).beginTree(ast);
	}

	// Check if the visitToken is called properly
	@Test
	public void visitTokenCallTest() throws Exception {
		DetailAST ast = new DetailAST();
		HalsteadMetricsCheck halsteadMetricsCheck = mock(HalsteadMetricsCheck.class);
		doNothing().when(halsteadMetricsCheck).visitToken(isA(DetailAST.class));
		halsteadMetricsCheck.visitToken(ast);
		Mockito.verify(halsteadMetricsCheck, times(1)).visitToken(ast);
	}

	// Check if the finishTree is called properly
	@Test
	public void finishTreeCallTest() throws Exception {
		DetailAST ast = new DetailAST();
		HalsteadMetricsCheck halsteadMetricsCheck = mock(HalsteadMetricsCheck.class);
		doNothing().when(halsteadMetricsCheck).finishTree(isA(DetailAST.class));
		halsteadMetricsCheck.finishTree(ast);
		Mockito.verify(halsteadMetricsCheck, times(1)).finishTree(ast);
	}
	
	// Test opening a file that does not exist
	@Test
	public void fileDoesNotExistTest() throws Exception {
		DefaultConfiguration dc = createModuleConfig(HalsteadMetricsCheck.class);
		String fileToTest = getPackageLocation() + "FakeFile.java";
		String result = "1: Got an exception - " + fileToTest + " (No such file or directory)";
		verify(dc, fileToTest, result);
	}

	// Check an empty file
	@Test
	public void zeroCountTest() throws Exception {
		// Test the actual running of the check
		DefaultConfiguration dc = createModuleConfig(HalsteadMetricsCheck.class);
		String fileToTest = getPackageLocation() + "EmptyClassTestCode.java";
		String[] results = { 
				"1: Halstead Difficulty: NaN",
				"1: Halstead Effort: NaN",
				"1: Halstead Length: 0",
				"1: Halstead Vocabulary: 0",
				"1: Halstead Volume: NaN",
				"1: Maintainability Index: NaN",
				"1: Number of Operands: 0",
				"1: Number of Operators: 0"
				};
		verify(dc, fileToTest, results);
	}
	
	// Actually test the check
	@Test
	public void halsteadMetricsTest() throws Exception {

		// Test the actual running of the check
		DefaultConfiguration dc = createModuleConfig(HalsteadMetricsCheck.class);
		String fileToTest = getPackageLocation() + "HalsteadMetricsCheckTestCode.java";
		String[] results = { 
				"1: Halstead Difficulty: 17.4",
				"1: Halstead Effort: 42171.83350649007",
				"1: Halstead Length: 343",
				"1: Halstead Vocabulary: 134",
				"1: Halstead Volume: 2423.6685923270156",
				"1: Maintainability Index: 0.7652124861030103",
				"1: Number of Operands: 126",
				"1: Number of Operators: 217"
				};
		verify(dc, fileToTest, results);
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
