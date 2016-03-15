package org.Community_division.algorithm;

import org.Community_division.core.Matrix;
import org.Community_division.core.MergeCommunity;
import org.Communiyt_division.util.ReadFilefromExcel;
import org.Communiyt_division.util.ReadFilefromText;

/**
 * 利用模块度增量矩阵求一个符号网络的划分
 * 
 * @author Administrator
 * 
 */
public class IncrementalOfSigned {

	Matrix admatrix = new Matrix();

	float pRatio, nRatio;// 矩阵中正边和负边分别占得比例。
	float pVector[], nVector[];

	/**
	 * main for test
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		double Q = 0.0;
		double[][] qMatrix;

		/**
		 * read from text
		 */
		ReadFilefromText readtext = new ReadFilefromText();
		int[][] edges = readtext.readLine("Gahu.txt");
		int[][] matrix = readtext.adjMatrix(edges);

		/**
		 * read from excel
		 */
		// ReadFilefromExcel readexcel = new ReadFilefromExcel();
		// int[][] matrix = readexcel.getMatrix();

		MergeCommunity com = new MergeCommunity(matrix);

		// signed network
		IncrementalOfSigned pG = new IncrementalOfSigned();

		qMatrix = pG.qMatrix(matrix);
		pG.printqMatris(qMatrix);

		while (true) {
			Matrix m = new Matrix();
			m.maxValueforMatrixFloat(qMatrix);
			double max = m.getMaxValueF();
			int cow = m.getCowF();
			int col = m.getColF();
			System.out.printf("\n%f %d %d\n", max, cow, col);

			if (max > 0.0) {
				pG.reqMatrix(qMatrix, cow, col);
				com.result(cow, col);

				System.out.printf("\nmax = %f\n", max);
				Q = Q + max;

				System.out.printf("Q = %f\n", Q);
			} else {
				break;
			}
			pG.printqMatris(qMatrix);
			com.print_result();

			System.out.printf("%10f", Q);
		}
	}

	/**
	 * get the initial Modularity matrix
	 * 
	 * @param matrix
	 *            the adjacent matrix
	 * @return
	 */
	public double[][] qMatrix(int[][] matrix) {

		admatrix.printMatrix(matrix);

		int totalNum = admatrix.edgeNum(matrix);
		int pNum = admatrix.pedgeNum(matrix);
		int nNum = admatrix.nedgeNum(matrix);

		pVector = new float[matrix.length];
		nVector = new float[matrix.length];
		double[][] qmatrix = new double[matrix.length][matrix.length];

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

		// 得到初始模块度矩阵
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
	 * @param qMatrix
	 *            modularity matrix
	 * @param cow
	 *            the cow of the max value
	 * @param col
	 *            the col of the max value
	 */
	public void reqMatrix(double[][] qMatrix, int cow, int col) {
		for (int k = 0; k < qMatrix.length; k++) {
			if (qMatrix[cow][k] != 0 && qMatrix[col][k] != 0 && k != cow
					&& k != col) {

				qMatrix[col][k] = qMatrix[cow][k] + qMatrix[col][k];

			} else if (qMatrix[cow][k] == 0 && qMatrix[col][k] != 0 && k != cow
					&& k != col) {
				qMatrix[col][k] = qMatrix[col][k]
						- 2
						* (pRatio * pVector[cow] * pVector[k] - nRatio
								* nVector[cow] * nVector[k]);

			} else if (qMatrix[cow][k] != 0 && qMatrix[col][k] == 0 && k != cow
					&& k != col) {
				qMatrix[col][k] = qMatrix[cow][k]
						- 2
						* (pRatio * pVector[col] * pVector[k] - nRatio
								* nVector[col] * nVector[k]);

			}
			qMatrix[k][col] = qMatrix[col][k];
			qMatrix[k][cow] = 0;
			qMatrix[cow][k] = 0;
		}
		pVector[col] = pVector[cow] + pVector[col];
		nVector[col] = nVector[cow] + nVector[col];
		pVector[cow] = 0;
		nVector[cow] = 0;

		for (int m = 0; m < qMatrix.length; m++) {

			System.out.printf("%f", pVector[m]);
			System.out.printf(" %f", nVector[m]);
			System.out.printf("\n");
		}

	}

	/**
	 * 打印模块度增量矩阵
	 */
	public void printqMatris(double[][] qMatrix) {

		for (int i = 0; i < qMatrix.length; i++) {
			for (int j = 0; j < qMatrix.length; j++)
				System.out.printf("%10f", qMatrix[i][j]);
			System.out.printf("\n");
		}
	}
}
