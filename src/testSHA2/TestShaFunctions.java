package testSHA2;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import rawrv.sha2.SecureHashFuncrions;


public class TestShaFunctions {

	static SecureHashFuncrions testShaFunctions;
	@BeforeClass
	public static void testsetUp(){
		testShaFunctions = new SecureHashFuncrions();
	}
	@Test
	public void testconvertToList1() {
		List<Boolean> actual = testShaFunctions.convertToList("a");
		for (Boolean boolean1 : actual) {
//			System.out.println(boolean1);
		}
		Boolean[] expectedInArray = {false,true,true,false,false,false,false,true};
		List<Boolean> expected = new ArrayList(Arrays.asList(expectedInArray)); 
		assertEquals(expected, actual);
	}
	
	@Test
	public void testconvertToList2() {
		List<Boolean> actual = testShaFunctions.convertToList("ab");
		for (Boolean boolean1 : actual) {
			System.out.println(boolean1);
		}
		Boolean[] expectedInArray = {false,true,true,false,false,false,false,true,false,true,true,false,false,false,true,false};
		List<Boolean> expected = new ArrayList(Arrays.asList(expectedInArray)); 
		assertEquals(expected, actual);
	}
}
