package org.Community_division.algorithm;

import org.Communiyt_division.util.ReadFilefromText;

/**
 * ��һ���㵽���е�����·��
 * @author Administrator
 *
 */
public class Dijkstra {
	static int M = 10000;// (��·��ͨ)

	public static void main(String[] args) {

		// TODO Auto-generated method stub
		/*
		 * һ������������ int[][] weight1 = {// �ڽӾ��� { 0, 1, 1, 1, M }, { 1, 0, 1, 1, M
		 * }, { M, 1, 0, 1, 1 }, { 1, 1, 1, 0, 1 }, { M, M, 1, 1, 0 } };
		 */
		ReadFilefromText readtext = new ReadFilefromText();
		int[][] edges = readtext.readLine("kong.txt");
		int[][] matrix = readtext.adjMatrix(edges);

		for (int i = 0; i < matrix.length; i++) {
			for (int j = i; j < matrix.length; j++) {
				if (matrix[i][j] == 0) {
					matrix[i][j] = M;
					matrix[j][i] = M;
				}
			}
		}

		int start = 1;
		int[] shortPath = Dijsktra(matrix, start);

		for (int i = 0; i < shortPath.length; i++)
			System.out.println("��" + start + "������" + i + "����̾���Ϊ��"
					+ shortPath[i]);

	}

	public static int[] Dijsktra(int[][] weight, int start) {
		// ����һ������ͼ��Ȩ�ؾ��󣬺�һ�������start����0��ţ�������������У�
		// ����һ��int[] ���飬��ʾ��start���������·������
		int n = weight.length; // �������
		int[] shortPath = new int[n]; // ��Ŵ�start��������������·��
		String[] path = new String[n]; // ��Ŵ�start��������������·�����ַ�����ʾ
		for (int i = 0; i < n; i++)
			path[i] = new String(start + "-->" + i);
		int[] visited = new int[n]; // ��ǵ�ǰ�ö�������·���Ƿ��Ѿ����,1��ʾ�����

		// ��ʼ������һ���������
		shortPath[start] = 0;
		visited[start] = 1;

		for (int count = 1; count <= n - 1; count++) // Ҫ����n-1������
		{

			int k = -1; // ѡ��һ�������ʼ����start�����δ��Ƕ���
			int dmin = Integer.MAX_VALUE;
			for (int i = 0; i < n; i++) {
				if (visited[i] == 0 && weight[start][i] < dmin) {
					dmin = weight[start][i];

					k = i;
				}

			}
			// System.out.println("k="+k);

			// ����ѡ���Ķ�����Ϊ��������·�����ҵ�start�����·������dmin
			shortPath[k] = dmin;

			visited[k] = 1;

			// ��kΪ�м�㣬������start��δ���ʸ���ľ���
			for (int i = 0; i < n; i++) {
				if (visited[i] == 0
						&& weight[start][k] + weight[k][i] < weight[start][i]) {
					weight[start][i] = weight[start][k] + weight[k][i];

					path[i] = path[k] + "-->" + i;

				}

			}

		}
		for (int i = 0; i < n; i++)
			System.out.println("��" + start + "������" + i + "�����·��Ϊ��" + path[i]);
		System.out.println("=====================================");

		return shortPath;
	}
}
