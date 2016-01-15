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

			// ѡ����ʼ����
			com.neighourCom(initalNode, initalNode, matrix);

			// �������ʼ������ÿһ���ڵ�������ȣ����Ҵ洢������memDegree��
			com.memberDegree(initalNode, initalNode, matrix);

			// �����ʼ����
			com.initalCom_f1(initalNode, com.memDegree);

			// �������������
			// for (int i = 0; i < com.memDegree.length; i++) {
			// System.out.printf("%d = %f\n", i, com.memDegree[i]);
			// }

			// ѡ����չ����
			com.extendCom(initalNode, initalNode, matrix);

			// �������������
			// for (int i = 0; i < com.memDegree.length; i++) {
			// System.out.printf("%d = %f\n", i, com.memDegree[i]);
			// }

			// ������չ����
			com.extendCom_f2(initalNode, com.memDegree);

			// �������������
			// for (int i = 0; i < com.memDegree.length; i++) {
			// System.out.printf("%d = %f\n", i, com.memDegree[i]);
			// }

			initalNode = com.maxValue(com.dVector);

			// ���������
			// System.out.println("dVector ����:");
			// for (int i = 0; i < com.dVector.length; i++) {
			// System.out.printf("%3d", com.dVector[i]);
			// }
		}
		// �ϲ�С������������
		while (true) {

			// �ϲ������õ�ȫ��ģ��Ⱦ���
			com.mergeCom();

			// ��ϲ��������ȫ��ģ��Ⱦ���
			m.maxValueforMatrixFloat(com.QMatrix);
			float max = m.getMaxValueF();
			int cow = m.getCowF();
			int col = m.getColF();

			// ������ֵ����������
			System.out.printf("%f %d %d\n", max, cow, col);

			if (max > com.Q) {

				// ���Ӧ�úϲ���������ϲ�����
				com.Result.get(col).addAll(com.Result.get(cow));
				com.Result.get(cow).clear();
				com.Community.get(col).addAll(com.Community.get(cow));
				com.Community.get(cow).clear();
				com.Q = max;
			} else {

				// ���������
				System.out.printf("ģ��� Q = %f\n", com.Q);
				com.printCom(com.Result);
				break;
			}
		}
	}

}
