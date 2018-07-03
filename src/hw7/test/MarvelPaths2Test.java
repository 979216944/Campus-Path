package hw7.test;

import org.junit.Test;

import hw6.MarvelPaths;

public class MarvelPaths2Test {

	// test exceptions when filename is null
	@Test(expected = IllegalArgumentException.class)
	public void testBuilGraphWithFilenameNull() throws Exception {
		MarvelPaths.buildGraph(null);
	}
	
}
