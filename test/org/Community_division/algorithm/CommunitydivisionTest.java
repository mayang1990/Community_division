package org.Community_division.algorithm;

import org.Community_division.core.Matrix;
import org.Communiyt_division.util.ReadFilefromExcel;
import org.Communiyt_division.util.ReadFilefromText;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CommunitydivisionTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@SuppressWarnings({ "static-access", "unchecked" })
	@Test
	public void test() {
		int initalNode = 0;

		ReadFilefromText readText = new ReadFilefromText();
		int[][] edges = readText.readLine("dolphins.txt");
		int[][] matrix = readText.adjMatrix(edges);

		ReadFilefromExcel readexcel = new ReadFilefromExcel();
		// int[][] matrix = readexcel.getMatrix();

		CommunityDivision com = new CommunityDivision(matrix);
		Matrix m = new Matrix();
		//m.printMatrix(matrix);

		com.dVector();
		initalNode = com.maxValue(com.dVector);

		while (initalNode > 0) {
			com.dVector[initalNode] = 0;
			System.out.printf("\n");
			System.out.printf("initalNode = %d\n", initalNode);

			// 选出初始社区
			com.neighourCom(initalNode, initalNode, matrix);

			// 计算出初始社区中每一个节点的隶属度，并且存储在数组memDegree中
			com.memberDegree(initalNode, initalNode, matrix);

			// 处理初始社区
			com.initalCom_f1(initalNode, com.memDegree);

			// 输出隶属度数组
			// for (int i = 0; i < com.memDegree.length; i++) {
			// System.out.printf("%d = %f\n", i, com.memDegree[i]);
			// }

			// 选出扩展社区
			com.extendCom(initalNode, initalNode, matrix);

			// 输出隶属度数组
			// for (int i = 0; i < com.memDegree.length; i++) {
			// System.out.printf("%d = %f\n", i, com.memDegree[i]);
			// }

			// 处理扩展社区
			com.extendCom_f2(initalNode, com.memDegree);

			// 输出隶属度数组
			// for (int i = 0; i < com.memDegree.length; i++) {
			// System.out.printf("%d = %f\n", i, com.memDegree[i]);
			// }

			initalNode = com.maxValue(com.dVector);

			// 输出度向量
			// System.out.println("dVector 数组:");
			// for (int i = 0; i < com.dVector.length; i++) {
			// System.out.printf("%3d", com.dVector[i]);
			// }
		}
		// 合并小社区并输出结果
		while (true) {

			// 合并社区得到全局模块度矩阵
			com.mergeCom();

			// 求合并社区后的全局模块度矩阵
			m.maxValueforMatrixFloat(com.QMatrix);
			float max = m.getMaxValueF();
			int cow = m.getCowF();
			int col = m.getColF();

			// 输出最大值和它的坐标
			System.out.printf("%f %d %d\n", max, cow, col);

			if (max > com.Q) {

				// 如果应该合并社区，则合并社区
				com.Result.get(col).addAll(com.Result.get(cow));
				com.Result.get(cow).clear();
				com.Community.get(col).addAll(com.Community.get(cow));
				com.Community.get(cow).clear();
				com.Q = max;
			} else {

				// 最后输出结果
				System.out.printf("模块度 Q = %f\n", com.Q);
				com.printCom(com.Result);
				break;
			}
		}
	}

}
