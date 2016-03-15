package org.Community_division.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.Community_division.core.Matrix;
import org.Communiyt_division.util.ReadFilefromExcel;

/**
 * ������㷨����
 * 
 * @author Administrator
 * 
 */
public class CommunityDivision {

	int[][] mMatrix;
	double[][] qMatrix;
	double[][] QMatrix;

	double[] memDegree;
	int[] dVector;

	double Q;
	double f1 = (float) 1.0 / 2; // �����ģ�������Ҫ����
	double f2 = (float) 1.0 / 3; // �����ģ�������Ҫ����

	/**
	 * main for test
	 * 
	 * @param args
	 */
	@SuppressWarnings({ "unchecked", "static-access" })
	public static void main(String[] args) {
		int initalNode = 0;

		// ReadFilefromText readText = new ReadFilefromText();
		// int[][] edges = readText.readLine("kong.txt");
		// int[][] matrix = readText.adjMatrix(edges);

		ReadFilefromExcel readexcel = new ReadFilefromExcel();
		int[][] matrix = readexcel.getMatrix();

		CommunityDivision com = new CommunityDivision(matrix);
		Matrix m = new Matrix();
		m.printMatrix(matrix);

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

			// ����ѡ����ʼ�ڵ�
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
			double max = m.getMaxValueF();
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

	/**
	 * ���Ż��ֵ��㷨
	 * 
	 * @param matrix
	 */
	@SuppressWarnings("unchecked")
	public CommunityDivision(int[][] matrix) {

		mMatrix = matrix;

		for (int i = 0; i < mMatrix.length; i++) {
			Community.add(i, new ArrayList<Integer>());
			Community.get(i).add(i);
			Result.add(i, new ArrayList<Integer>());

		}
		memDegree = new double[Community.size()];
	}

	/**
	 * ���������
	 */
	public void dVector() {
		dVector = new int[mMatrix.length];
		for (int i = 1; i < mMatrix.length; i++) {
			dVector[i] = m.pDegree(mMatrix, i);
		}

	}

	/**
	 * �����������ֵ�Լ��������ֵ������
	 * 
	 * @param array
	 * @return
	 */
	public int maxValue(int[] array) {
		int max = 0, index = 0;
		for (int i = 1; i < mMatrix.length; i++) {
			if (array[i] > max) {
				max = array[i];
				index = i;
			}
		}
		return index;
	}

	/**
	 * ��ӡCommunity
	 * 
	 * @param com
	 */
	@SuppressWarnings("rawtypes")
	static public void printCom(List<List> com) {
		for (int i = 1; i < com.size(); i++) {
			if (!com.get(i).isEmpty() && !com.get(i).contains(null)) {
				System.out.println(com.get(i));
			}
		}
	}

	/**
	 * �ָ�Community
	 * 
	 * @param com
	 */
	@SuppressWarnings("unchecked")
	public void recoverCom(int com) {
		for (int i = 0; i < Result.get(com).size(); i++) {
			Community.get(com).add(i, Result.get(com).get(i));
		}
	}

	/**
	 * �õ���ʼ����init,�����ڵĽڵ���ھӽڵ㼯��
	 * 
	 * @param com
	 *            �ڵ�
	 * @param init
	 *            ��ʼ����
	 * @param array
	 */
	@SuppressWarnings("unchecked")
	public void neighourCom(int com, int init, int[][] array) {
		for (int i = 0; i < Community.size(); i++) {
			if (array[com][i] == 1 && !Community.get(init).contains(i)) {
				Community.get(com).add(i);// ֱ�Ӱѹ��c�����k����^����ԓ�����دB�ɡ�
			}
		}
		// ������
		// System.out.println("�ھ�����:");
		// for (int i = 0; i < Community.size(); i++) {
		// if (!Community.get(i).isEmpty() && !Community.get(i).contains(null))
		// {
		// System.out.println(Community.get(i));
		// }
		// }
	}

	/**
	 * ����^com�е�ÿһ���ڵ�ͳ�ʼ����init��������
	 * 
	 * @param com
	 *            ����
	 * @param init
	 *            ��ʼ����
	 * @param array
	 */
	public void memberDegree(int com, int init, int[][] array) {
		float d = 0;

		for (int i = 0; i < Community.get(com).size(); i++) {
			d = 0;
			for (int j = 0; j < Community.get(init).size(); j++) {
				if (array[(Integer) Community.get(com).get(i)][(Integer) Community
						.get(init).get(j)] == 1) {
					d++;
				}
			}

			memDegree[(Integer) Community.get(com).get(i)] = d
					/ m.pDegree(array, (Integer) Community.get(com).get(i));

			System.out.printf("%d %f %d %f\n", Community.get(com).get(i), d,
					m.pDegree(array, (Integer) Community.get(com).get(i)),
					memDegree[(Integer) Community.get(com).get(i)]);
		}
	}

	/**
	 * ������com���г�ʼ��ֵf1�ж�,�õ����յĳ�ʼ����,���Ҽ���ϲ���ʼ������ģ�������
	 * 
	 * @param com
	 *            ����
	 * @param memDegree2
	 *            ����������
	 */
	@SuppressWarnings("unchecked")
	public void initalCom_f1(int com, double[] memDegree2) {
		qMatrix = unsigned.qMatrix(mMatrix);

		for (int i = 0; i < Community.get(com).size(); i++) {
			int j = (Integer) Community.get(com).get(i);
			if (memDegree2[j] < f1) {
				System.out.printf("\n%d %f\n", j, memDegree2[j]);
				// System.out.println(i);
				Community.get(com).set(i, null);
				// System.out.print(Community);
				memDegree2[j] = 0; // ��û�кϲ�����������������
			}

		}

		// ��û�кϲ�������ɾ��
		for (int i = 0; i < Community.size(); i++) {
			if (!Community.get(i).isEmpty()) {
				for (int j = 0; j < Community.get(i).size();) {
					if (Community.get(i).get(j) == null) {
						Community.get(i).remove(j);
					} else {
						j++;
					}
				}
				// System.out.println(Community.get(i));
			}
		}

		// ����ϲ�������ģ�������
		for (int i = 0; i < Community.get(com).size(); i++) {
			if ((Integer) Community.get(com).get(i) != com) {
				// System.out.println(Community.get(com).get(i));
				unsigned.reqMatrix(qMatrix,
						(Integer) Community.get(com).get(i), com);
				// unsigned.printqMatris(qMatrix);
			}
		}

	}

	/**
	 * ��չ���������ʼ�����е�ÿһ���ڵ���ھӺ�ÿһ���ھӺͳ�ʼ����֮���������
	 * 
	 * @param extend
	 *            ��չ����
	 * @param init
	 *            ��ʼ����
	 * @param array
	 */
	public void extendCom(int extend, int init, int[][] array) {

		if (Community.get(extend).size() > 1) {
			for (int i = 1; i < Community.get(extend).size(); i++) {

				// �����ʼ������ÿһ���ڵ���ھӽڵ�
				neighourCom((Integer) Community.get(extend).get(i), init, array);

				// ������չ�����е�ÿһ���ڵ��������
				memberDegree((Integer) Community.get(extend).get(i), init,
						array);

			}
		}

	}

	/**
	 * ����չ�������г�ʼ��ֵf2�ж�,�õ����յ���չ����
	 * 
	 * @param init
	 *            ��ʼ����
	 * @param memDegree2
	 *            ����������
	 */
	@SuppressWarnings("unchecked")
	public void extendCom_f2(int init, double[] memDegree2) {
		for (int i = 1; i < memDegree2.length; i++) {
			if (!Community.get(init).contains(i) && memDegree2[i] > f2) {
				System.out.printf("�ڵ�Ϊ��%d\n", i);

				// �ж�ģ��������Ƿ����0
				if (qMatrix[i][init] > 0) {
					Community.get(init).add(i);
					System.out.println("ģ�������������,�ϲ�:");
					unsigned.reqMatrix(qMatrix, i, init);
					// unsigned.printqMatris(qMatrix);
				}
				System.out.println("ģ�������С����,���ϲ�.");
			}
		}

		// ������Ѿ������������Ľڵ�
		for (int i = 0; i < Community.get(init).size(); i++) {
			if ((Integer) Community.get(init).get(i) != init) {
				Community.get((Integer) Community.get(init).get(i)).clear();
				dVector[(Integer) Community.get(init).get(i)] = 0;
			}
		}

		// ������������顣
		for (int i = 1; i < memDegree2.length; i++) {
			if (i != init) {
				memDegree2[i] = 0;
			}
		}

		// �������ֵ��Result���ø�ֵΪ��ֵ��Ϊ���á�
		for (int i = 0; i < Community.get(init).size(); i++) {
			Result.get(init).add(i, Community.get(init).get(i));
		}

		// ���Community��Result
		// System.out.println("Community:");
		// printCom(Community);
		// System.out.println("Result:");
		// printCom(Result);
	}

	/**
	 * �ϲ������õ�ȫ��ģ��Ⱦ���
	 */
	@SuppressWarnings("unchecked")
	public void mergeCom() {

		Q = modularity.unsignedGlobalModularity(mMatrix, Result);
		QMatrix = new double[mMatrix.length][mMatrix.length];

		for (int i = 1; i < Result.size(); i++) {
			if (!Result.get(i).isEmpty()) {
				for (int j = i + 1; j < Result.size(); j++) {
					if (!Result.get(j).isEmpty()) {

						System.out.printf("\ni = %d j = %d\n", i, j);
						// ��Ci�ӵ�Cj��
						Community.get(j).addAll(Community.get(i));

						// ���Ci
						Community.get(i).clear();

						// ����ϵ�ģ���
						QMatrix[i][j] = modularity.unsignedGlobalModularity(
								mMatrix, Community);
						// ���Cj
						Community.get(j).clear();

						// �ָ�Community
						recoverCom(i);
						recoverCom(j);
					}
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	List<List> Community = new ArrayList<List>();
	@SuppressWarnings("rawtypes")
	static List<List> Result = new ArrayList<List>();
	IncrementalOfUnsigned unsigned = new IncrementalOfUnsigned();
	UnsignedGlobalModularity modularity = new UnsignedGlobalModularity();
	Matrix m = new Matrix();
}
