package org.Community_division.algorithm;

import org.Community_division.core.Matrix;
import org.Community_division.core.MergeCommunity;
import org.Communiyt_division.util.ReadFilefromExcel;
import org.Communiyt_division.util.ReadFilefromText;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class IncrementalOfModularityTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		float Q = 0;
		float[][] qMatrix;

		/**
		 * read from text
		 */
		// ReadFilefromText readtext = new ReadFilefromText();
		// int[][] edges = readtext.readLine("Gahu.txt");
		// int[][] matrix = readtext.adjMatrix(edges);

		/**
		 * read from excel
		 */
		ReadFilefromExcel readexcel = new ReadFilefromExcel();
		int[][] matrix = readexcel.getMatrix();

		MergeCommunity com = new MergeCommunity(matrix);

		IncrementalOfUnsigned pG = new IncrementalOfUnsigned();
		qMatrix = pG.qMatrix(matrix);
		pG.printqMatris(qMatrix);

		while (true) {
			Matrix m = new Matrix();
			m.maxValueforMatrixFloat(qMatrix);
			float max = m.getMaxValueF();
			int cow = m.getCowF();
			int col = m.getColF();
			System.out.printf("%f %d %d\n", max, cow, col);

			if (max > 0) {
				pG.reqMatrix(qMatrix, cow, col);
				com.result(cow, col);

				Q = Q + max;
				System.out.printf("%f", Q);
			} else {
				break;
			}
			pG.printqMatris(qMatrix);
			com.print_result();
			System.out.printf("%f", Q);
		}
	}

}
