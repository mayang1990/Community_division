package org.Community_division.algorithm;

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
	public float unsignedGlobalModularity(int[][] matrix, int[][] result) {

		Matrix admatrix = new Matrix();

		int A = 0;
		int k = 0;
		int M = admatrix.edgeNum(matrix);

		// ��ӡmatrix
		admatrix.printMatrix(matrix);

		// ��ӡList
		System.out.printf("List:\n");
		for (int i = 0; i < result.length; i++) {
			System.out.printf("C%d = ", i + 1);
			for (int j = 0; j < result[i].length; j++)
				System.out.printf("%d ", result[i][j]);
			System.out.printf("\n");
		}

		// ��ʼ��"���Ź�ϵ����"
		for (int i = 0; i < result.length; i++) {
			for (int j1 = 0; j1 < result[i].length; j1++) {
				for (int j2 = j1 + 1; j2 < result[i].length; j2++) {

					int p = result[i][j1] - 1;
					int q = result[i][j2] - 1;

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

		System.out.printf("������ = %d\n", matrix.length);
		System.out.printf("���� = %d\n", M / 2);
		System.out.printf("ģ���Q = %f\n", q);
		return q;
	}
}
