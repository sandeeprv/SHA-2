package testSHA2;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;

import rawrv.sha2.SHA2;


public class TestShaFunctions {

	@Test
	public void testconvertToList1() {
		ArrayList<Boolean> actual = SHA2.convertToList("a");
		for (Boolean boolean1 : actual) {
//			System.out.println(boolean1);
		}
		Boolean[] expectedInArray = {false,true,true,false,false,false,false,true};
		ArrayList<Boolean> expected = new ArrayList(Arrays.asList(expectedInArray)); 
		assertEquals(expected, actual);
	}
	
	@Test
	public void testconvertToList2() {
		ArrayList<Boolean> actual = SHA2.convertToList("ab");
		for (Boolean boolean1 : actual) {
			System.out.println(boolean1);
		}
		Boolean[] expectedInArray = {false,true,true,false,false,false,false,true,false,true,true,false,false,false,true,false};
		ArrayList<Boolean> expected = new ArrayList(Arrays.asList(expectedInArray)); 
		assertEquals(expected, actual);
	}
}
