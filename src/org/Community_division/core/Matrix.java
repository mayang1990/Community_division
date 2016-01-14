package org.Community_division.core;

/**
 * 
 * {@link pDegree}get the positive degree of the node i {@link nDegree}get the
 * negative degree of the node i {@link edgeNum} get the edge number of the
 * graph {@link pedgeNum}get the positive edge number of the graph
 * {@link nedgeNum}get the negative edge number of the graph
 * {@link maxValueforMatrix}get the max value of the matrix{@link printMatrix}
 * print the matrix
 * 
 * 
 */
public class Matrix {

	private int maxValue = 0; // 矩阵的最大值
	private int cow, col; // 矩阵最大值最大值对应的坐标
	private float maxValueF = 0;
	private int cowF, colF;

	public int getMaxValue() {
		return maxValue;
	}

	public int getCow() {
		return cow;
	}

	public int getCol() {
		return col;
	}

	public float getMaxValueF() {
		return maxValueF;
	}

	public int getCowF() {
		return cowF;
	}

	public int getColF() {
		return colF;
	}

	/**
	 * get the positive degree of the node i
	 * 
	 * @param matrix
	 *            the adjacent matrix
	 * @param i
	 *            the node i
	 * @return
	 */
	public int pDegree(int[][] matrix, int i) {
		int pDegree = 0; // 正度

		for (int j = 0; j < matrix.length; j++) {
			if (matrix[i][j] == 1)
				pDegree++;
		}
		return pDegree;
	}

	/**
	 * get the negative degree of the node i
	 * 
	 * @param matrix
	 *            the adjacent matrix
	 * @param i
	 *            the mode i
	 * @return
	 */
	public int nDegree(int[][] matrix, int i) {
		int nDegree = 0; // 负度

		for (int j = 0; j < matrix.length; j++) {
			if (matrix[i][j] == -1)
				nDegree++;
		}
		return nDegree;
	}

	/**
	 * get the edge number of the graph
	 * 
	 * @param matrix
	 *            the adjacent matrix
	 * @return
	 */
	public int edgeNum(int[][] matrix) {
		int edgeNum = 0; // 边数

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] != 0)
					edgeNum++;
			}
		}
		return edgeNum;
	}

	/**
	 * get the positive edge number of the graph
	 * 
	 * @param matrix
	 *            the adjacent matrix
	 * @return
	 */
	public int pedgeNum(int[][] matrix) {
		int pedgeNum = 0;

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] == 1)
					pedgeNum++;
			}
		}
		return pedgeNum;
	}

	/**
	 * get the negative edge number of the graph
	 * 
	 * @param matrix
	 *            the adjacent matrix
	 * @return
	 */
	public int nedgeNum(int[][] matrix) {
		int nedgeNum = 0;

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[i][j] == -1)
					nedgeNum++;
			}
		}
		return nedgeNum;
	}

	/**
	 * get the max value of the matrix
	 * 
	 * @param matrix
	 *            the adjacent matrix
	 */
	public void maxValueforMatrix(int[][] matrix) {

		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix.length; j++) {
				if (matrix[i][j] > maxValue) {
					maxValue = matrix[i][j];
					cow = i;
					col = j;
				}
			}
		}
	}

	/**
	 * get the max value of the float matrix
	 * 
	 * @param matrix
	 *            the float matrix
	 */
	public void maxValueforMatrixFloat(float[][] matrix) {

		for (int i = 0; i < matrix.length; i++) {
			for (int j = i + 1; j < matrix.length; j++) {
				if (matrix[i][j] > maxValueF) {
					maxValueF = matrix[i][j];
					cowF = i;
					colF = j;
				}
			}
		}
	}

	/**
	 * print the matrix
	 * 
	 * @param matrix
	 *            the adjacent matrix
	 */
	public void printMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.printf("%3d", matrix[i][j]);
			}
			System.out.println();
		}
	}

}
