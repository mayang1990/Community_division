package org.Community_division.algorithm;

import java.util.List;

import org.Community_division.core.Matrix;

/**
 * 
 * The global modularity of the unsigned graph
 * 
 */
public class UnsignedGlobalModularity {

	/**
	 * the global modularity of the unsigned net
	 * 
	 * @param matrix
	 *            the adjacent matrix
	 * @param result
	 *            the result of the community division
	 * @return q
	 */
	public float unsignedGlobalModularity(int[][] matrix, List<List> result) {

		Matrix admatrix = new Matrix();

		int A = 0;
		int k = 0;
		int M = admatrix.edgeNum(matrix);

		// 打印matrix
		admatrix.printMatrix(matrix);

		// 打印List
		System.out.printf("List:\n");
		for (int i = 0; i < result.size(); i++) {
			System.out.printf("C%d = ", i + 1);
			for (int j = 0; j < result.get(i).size(); j++)
				System.out.printf("%d ", result.get(i).get(j));
			System.out.printf("\n");
		}

		// 初始化"社团关系数组"
		for (int i = 0; i < result.size(); i++) {
			for (int j1 = 0; j1 < result.get(i).size(); j1++) {
				for (int j2 = j1 + 1; j2 < result.get(i).size(); j2++) {

					int p = (Integer) result.get(i).get(j1);
					int q = (Integer) result.get(i).get(j2);

					if (matrix[p][q] == 1)
						A = A + 2;

					int k1 = admatrix.pDegree(matrix, p);
					int k2 = admatrix.pDegree(matrix, q);
					k += k1 * k2;
				}
			}
		}

		k = 2 * k;

		float q1 = ((float) 1 / M) * A;
		float q2 = ((float) 1 / (M * M)) * k;
		float q = q1 - q2;

		System.out.printf("顶点数 = %d\n", matrix.length);
		System.out.printf("边数 = %d\n", M / 2);
		System.out.printf("模块度Q = %f\n", q);
		return q;
	}
}
