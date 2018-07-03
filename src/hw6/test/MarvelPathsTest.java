package hw6.test;

import org.junit.Test;

import hw6.MarvelPaths;

public class MarvelPathsTest {

	// test exceptions when filename is null
	@Test(expected = IllegalArgumentException.class)
	public void testBuilGraphWithFilenameNull() throws Exception {
		MarvelPaths.buildGraph(null);
	}
	
}
