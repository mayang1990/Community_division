package org.Community_division.algorithm;

import org.Community_division.core.Matrix;

public class IncrementalOfUnsigned {

	Matrix admatrix = new Matrix();

	float pRatio;// 矩阵中正边和负边分别占得比例。
	float pVector[];

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

		pVector = new float[matrix.length];

		float[][] qmatrix = new float[matrix.length][matrix.length];

		pRatio = (float) pNum / totalNum;

		System.out.printf("%d", pNum);

		// 得到正强度数组和负强度数组。
		for (int i = 0; i < matrix.length; i++) {
			pVector[i] = (float) admatrix.pDegree(matrix, i) / pNum;

		}

		for (int i = 0; i < matrix.length; i++) {
			System.out.printf("\n%f", pVector[i]);

			System.out.printf("\n");
		}

		// 得到初始模块度矩阵
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] == 1) {
					qmatrix[i][j] = (float) 1 / pNum - pVector[i] * pVector[j];
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
				matrix[col][k] = matrix[col][k] - 2
						* (pVector[cow] * pVector[k]);

			} else if (matrix[cow][k] != 0 && matrix[col][k] == 0 && k != cow
					&& k != col) {
				matrix[col][k] = matrix[cow][k] - 2
						* (pVector[col] * pVector[k]);

			}
			matrix[k][col] = matrix[col][k];
			matrix[k][cow] = 0;
			matrix[cow][k] = 0;
		}
		pVector[col] = pVector[cow] + pVector[col];
		pVector[cow] = 0;

		for (int m = 0; m < matrix.length; m++) {

			System.out.printf("\npVecter[%d] = %f", m, pVector[m]);
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
