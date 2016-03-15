package org.Community_division.algorithm;

import org.Community_division.core.Matrix;
import org.Community_division.core.MergeCommunity;
import org.Communiyt_division.util.ReadFilefromExcel;

/**
 * 利用模块度增量矩阵求一个无符号网络的划分
 * 
 * @author Administrator
 * 
 */
public class IncrementalOfUnsigned {

	Matrix admatrix = new Matrix();

	double pRatio;// 矩阵中正边和负边分别占得比例。
	double pVector[];

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
		// ReadFilefromText readtext = new ReadFilefromText();
		// int[][] edges = readtext.readLine("kong.txt");
		// int[][] matrix = readtext.adjMatrix(edges);

		/**
		 * read from excel
		 */
		ReadFilefromExcel readexcel = new ReadFilefromExcel();
		int[][] matrix = readexcel.getMatrix();

		MergeCommunity com = new MergeCommunity(matrix);

		// unsigned network
		IncrementalOfUnsigned pG = new IncrementalOfUnsigned();

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

		pVector = new double[matrix.length];

		double[][] qmatrix = new double[matrix.length][matrix.length];

		System.out.printf("%d", totalNum);

		double e = 1 / totalNum;
		;
		System.out.printf("\n e = %f", e);

		// 得到强度数组。
		for (int i = 0; i < matrix.length; i++) {
			pVector[i] = (double) admatrix.pDegree(matrix, i) / totalNum;

		}

		for (int i = 0; i < matrix.length; i++) {
			System.out.printf("\n度 = %d，度强度 = %f", admatrix.pDegree(matrix, i),
					pVector[i]);
			System.out.printf("\n");
		}

		// 得到初始模块度矩阵
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] == 1) {
					qmatrix[i][j] = (1.0 / totalNum)
							- (pVector[i] * pVector[j]);

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
				qMatrix[col][k] = qMatrix[col][k] - 2
						* (pVector[cow] * pVector[k]);

			} else if (qMatrix[cow][k] != 0 && qMatrix[col][k] == 0 && k != cow
					&& k != col) {
				qMatrix[col][k] = qMatrix[cow][k] - 2
						* (pVector[col] * pVector[k]);

			}
			qMatrix[k][col] = qMatrix[col][k];
			qMatrix[k][cow] = 0;
			qMatrix[cow][k] = 0;
		}
		pVector[col] = pVector[cow] + pVector[col];
		pVector[cow] = 0;

		for (int m = 0; m < qMatrix.length; m++) {

			System.out.printf("\npVecter[%d] = %f", m, pVector[m]);
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
