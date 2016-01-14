package org.Community_division.algorithm;

import org.Communiyt_division.util.ReadFilefromText;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UnsignedGlobalMoudarityTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		int[][] list = {
				{ 1, 5, 17, 7, 11, 12, 6, 2, 3, 4, 8, 13, 14, 18, 20, 22 },
				{ 9, 33, 31, 16, 10, 15, 19, 21, 23, 24, 29, 32, 34, 30, 27,
						28, 25, 26 } };
		ReadFilefromText readtext = new ReadFilefromText();
		int[][] edges = readtext.readLine("kong.txt");
		int[][] matrix = readtext.adjMatrix(edges);
		UnsignedGlobalModularity unsigned = new UnsignedGlobalModularity();
		unsigned.unsignedGlobalModularity(matrix, list);

	}

}
