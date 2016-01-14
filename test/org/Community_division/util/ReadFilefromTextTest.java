package org.Community_division.util;

import org.Community_division.core.Matrix;
import org.Communiyt_division.util.ReadFilefromText;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReadFilefromTextTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ReadFilefromText readText = new ReadFilefromText();
		int[][] edges = readText.readLine("kong.txt");
		int[][] matrix = readText.adjMatrix(edges);
		Matrix m = new Matrix();
		m.printMatrix(matrix);
	}

}
