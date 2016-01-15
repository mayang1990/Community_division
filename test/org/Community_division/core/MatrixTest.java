package org.Community_division.core;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MatrixTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		int[][] matrix = { { 1, 0, 0, 0 }, { 0, 1, 0, 0 }, { 0, 2, 0, 0 },
				{ 0, 1, 0, 0 } };
		int[][] matrix1 = { { 1, 0, 0, 0 }, { 0, 1, 2, 0 }, { 0, 2, 0, 1 },
				{ 0, 1, 0, 0 } };
		Matrix m = new Matrix();
		System.out.println(m.edgeNum(matrix));
		m.maxValueforMatrix(matrix1);
		System.out.println(m.getMaxValue());
		System.out.println(m.getCow());
		System.out.println(m.getCol());

	}

}
