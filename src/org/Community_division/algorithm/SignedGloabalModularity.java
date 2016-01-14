package org.Community_division.algorithm;

import org.Community_division.core.Matrix;

/**
 * 
 * The global modularity of the signed graph
 * 
 */
public class SignedGloabalModularity {

	/**
	 * the global modularity of the signed net
	 * 
	 * @param matrix
	 *            the adjacent matrix
	 * @param result
	 *            the result of the community division
	 * @return q
	 */
	public float signedGloabalModularity(int[][] matrix, int[][] list) {

		Matrix admatrix = new Matrix();
		admatrix.printMatrix(matrix);
		int M = admatrix.edgeNum(matrix);
		int M1 = admatrix.pedgeNum(matrix);
		int M2 = admatrix.nedgeNum(matrix);

		int A = 0;
		int kz = 0, kf = 0;
		int F = 0, N = 0, P = 0;

		// 打印List
		System.out.printf("List:\n");
		for (int i = 0; i < list.length; i++) {
			System.out.printf("C%d = ", i + 1);
			for (int j = 0; j < list[i].length; j++)
				System.out.printf("%d ", list[i][j]);
			System.out.printf("\n");
		}

		// 初始化"社团数组关系" i,j不在一个数组中
		for (int i1 = 0; i1 < list.length; i1++) {
			for (int i2 = i1 + 1; i2 < list.length; i2++) {
				for (int j1 = 0; j1 < list[i1].length; j1++) {
					for (int j2 = 0; j2 < list[i2].length; j2++) {

						int p = list[i1][j1] - 1; // 0不为节点
						int q = list[i2][j2] - 1;

						// int p = list[i1][j1];
						// int q = list[i2][j2];
						if (matrix[p][q] == 1) {
							P++;
						}

					}
				}
			}
		}

		// 初始化"社团关系数组" i,j在一个数组中
		for (int i = 0; i < list.length; i++) {
			for (int j1 = 0; j1 < list[i].length; j1++) {
				for (int j2 = j1 + 1; j2 < list[i].length; j2++) {

					int p = list[i][j1] - 1;
					int q = list[i][j2] - 1;

					if (matrix[p][q] == 1) {
						A = A + 2;
					} else if (matrix[p][q] == -1) {
						A = A - 2;
						N++;
					}

					int k1 = admatrix.pDegree(matrix, p); // p的正度
					int k2 = admatrix.nDegree(matrix, p); // p的负度

					int k3 = admatrix.pDegree(matrix, q); // q的正度
					int k4 = admatrix.nDegree(matrix, q); // q的负度

					kz += k1 * k3; // 正度相乘
					kf += k2 * k4; // 负度相乘

				}
			}
		}

		kz = 2 * kz;
		kf = 2 * kf;

		System.out.printf("顶点数 = %d\n", matrix.length);
		System.out.printf("边数 = %d\n", M / 2);

		F = N + P;

		float q1 = ((float) 1 / M) * A;
		float q2 = ((float) 1 / (M * M1)) * kz;
		float q3 = ((float) 1 / (M * M2)) * kf;
		float q = q1 - q2 + q3;

		System.out.printf("模块度Q = %f\n", q);
		System.out.printf("挫败值F = %d\n", F);

		return q;
	}
}
