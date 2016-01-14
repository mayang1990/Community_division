package org.Community_division.algorithm;

import org.Communiyt_division.util.ReadFilefromText;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SignedGloabalModularityTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		int[][] list = { { 2, 3, 4, 1 }, { 5, 7, 6, 8, 9, 10, 11 },
				{ 13, 14, 12, 16, 15 } };
		ReadFilefromText readtext = new ReadFilefromText();
		int[][] edges = readtext.readLine("Gahu.txt");
		int[][] matrix = readtext.adjMatrix(edges);
		SignedGloabalModularity signed = new SignedGloabalModularity();
		signed.signedGloabalModularity(matrix, list);
	}

}
