package org.Community_division.algorithm;

import org.Community_division.core.Matrix;

public class IncrementalOfModularity {

	Matrix admatrix = new Matrix();

	float pRatio, nRatio;// 矩阵中正边和负边分别占得比例。
	float pVector[], nVector[];

	/**
	 * get the initial Modularity matrix
	 * 
	 * @param matrix
	 *            the adjacent matrix
	 * @return
	 */
	public float[][] qMatrix(int[][] matrix) {

		admatrix.printMatrix(matrix);

		int totalNum = admatrix.edgeNum(matrix);
		int pNum = admatrix.pedgeNum(matrix);
		int nNum = admatrix.nedgeNum(matrix);

		pVector = new float[matrix.length];
		nVector = new float[matrix.length];
		float[][] qmatrix = new float[matrix.length][matrix.length];

		pRatio = (float) pNum / totalNum;
		nRatio = (float) nNum / totalNum;
		System.out.printf("%d %d", pNum, nNum);
		System.out.printf(" %f %f", pRatio, nRatio);

		// 得到正强度数组和负强度数组。
		for (int i = 0; i < matrix.length; i++) {
			pVector[i] = (float) admatrix.pDegree(matrix, i) / pNum;
			nVector[i] = (float) admatrix.nDegree(matrix, i) / nNum;
		}

		for (int i = 0; i < matrix.length; i++) {
			System.out.printf("\n%f", pVector[i]);
			System.out.printf(" %f", nVector[i]);
			System.out.printf("\n");
		}

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] == 1) {
					qmatrix[i][j] = pRatio
							* ((float) 1 / pNum - pVector[i] * pVector[j])
							+ nRatio * nVector[i] * nVector[j];
				} else if (matrix[i][j] == -1) {
					qmatrix[i][j] = -pRatio * pVector[i] * pVector[j] - nRatio
							* ((float) 1 / nNum - nVector[i] * nVector[j]);
				}
			}
		}
		return qmatrix;
	}

	/**
	 * update the Modularity matrix
	 * 
	 * @param matrix
	 *            modularity matrix
	 * @param cow
	 *            the cow of the max value
	 * @param col
	 *            the col of the max value
	 */
	public void reqMatrix(float[][] matrix, int cow, int col) {
		for (int k = 0; k < matrix.length; k++) {
			if (matrix[cow][k] != 0 && matrix[col][k] != 0 && k != cow
					&& k != col) {

				matrix[col][k] = matrix[cow][k] + matrix[col][k];

			} else if (matrix[cow][k] == 0 && matrix[col][k] != 0 && k != cow
					&& k != col) {
				matrix[col][k] = matrix[col][k]
						- 2
						* (pRatio * pVector[cow] * pVector[k] - nRatio
								* nVector[cow] * nVector[k]);

			} else if (matrix[cow][k] != 0 && matrix[col][k] == 0 && k != cow
					&& k != col) {
				matrix[col][k] = matrix[cow][k]
						- 2
						* (pRatio * pVector[col] * pVector[k] - nRatio
								* nVector[col] * nVector[k]);

			}
			matrix[k][col] = matrix[col][k];
			matrix[k][cow] = 0;
			matrix[cow][k] = 0;
		}
		pVector[col] = pVector[cow] + pVector[col];
		nVector[col] = nVector[cow] + nVector[col];
		pVector[cow] = 0;
		nVector[cow] = 0;

		for (int m = 0; m < matrix.length; m++) {

			System.out.printf("%f", pVector[m]);
			System.out.printf("%f", nVector[m]);
			System.out.printf("\n");
		}

	}

	/**
	 * 打印模块度增量矩阵
	 */
	public void printqMatris(float[][] qmatrix) {

		for (int i = 0; i < qmatrix.length; i++) {
			for (int j = 0; j < qmatrix.length; j++)
				System.out.printf("%10f", qmatrix[i][j]);
			System.out.printf("\n");
		}
	}
}
