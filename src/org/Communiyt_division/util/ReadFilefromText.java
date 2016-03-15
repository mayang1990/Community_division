package org.Communiyt_division.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.Community_division.core.Matrix;

/**
 * 
 * Read the adjacent matrix from text
 * 
 */
public class ReadFilefromText {

	private int[] mVexs;
	private int[][] mMatrix; // �ڽӾ���

	/**
	 * main for test
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		ReadFilefromText readText = new ReadFilefromText();
		int[][] edges = readText.readLine("kong.txt");
		int[][] matrix = readText.adjMatrix(edges);
		Matrix m = new Matrix();
		m.printMatrix(matrix);
	}

	public int[][] readLine(String path) {

		BufferedReader br = null;
		int[][] array = null;

		try {

			String line;
			int count = 0;
			int cows = 0;
			int cols = 0;

			br = new BufferedReader(new FileReader(path));

			// ȥ����β����ո�
			line = br.readLine();
			String[] eline = line.split(" ");

			// �����һ�е�����
			// �����һ�е�һ�����ݣ�Ϊ�����������ɶ�������
			int vexnum = Integer.parseInt(eline[0]);
			mVexs = new int[vexnum];
			for (int i = 0; i < vexnum; i++) {
				mVexs[i] = i;
			}

			// �����һ��ʣ�µ���������
			cows = Integer.parseInt(eline[1]);
			cols = Integer.parseInt(eline[2]);
			array = new int[cows][cols];

			// �����һ��֮�������
			while ((line = br.readLine()) != null) {

				eline = line.split(" ");
				array[count][0] = Integer.parseInt(eline[0]);
				array[count][1] = Integer.parseInt(eline[1]);
				array[count][2] = Integer.parseInt(eline[2]);
				count++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return array;
	}

	/**
	 * get the adjacent matrix of the graph
	 * 
	 * @param mVexs
	 * @param edges
	 * @return
	 */
	public int[][] adjMatrix(int[][] edges) {

		// ��ʼ��"������"��"����"
		int vlen = mVexs.length;
		int elen = edges.length;

		// ��ʼ��"����"
		mVexs = new int[vlen];
		for (int i = 0; i < vlen; i++)
			mVexs[i] = i;

		// ��ʼ��"��"
		mMatrix = new int[vlen][vlen];
		for (int i = 0; i < elen; i++) {
			// ��ȡ�ߵ���ʼ����ͽ�������

			int p1 = edges[i][0];
			int p2 = edges[i][1];
			// System.out.print(p1);
			// System.out.print(p2);

			mMatrix[p1][p2] = edges[i][2];
			mMatrix[p2][p1] = edges[i][2];
		}
		return mMatrix;
	}
}
