package org.Community_division.algorithm;

import java.util.ArrayList;
import java.util.List;

import org.Community_division.core.Matrix;

public class CommunityDivision {

	int[][] mMatrix;
	float[][] qMatrix;
	float[][] QMatrix;

	float[] memDegree;
	int[] dVector;

	float Q;
	float f1 = (float) 1.0 / 2; // �����ģ�������Ҫ����
	float f2 = (float) 1.0 / 3; // �����ģ�������Ҫ����

	@SuppressWarnings("unchecked")
	public CommunityDivision(int[][] matrix) {

		mMatrix = matrix;

		for (int i = 0; i < mMatrix.length; i++) {
			Community.add(i, new ArrayList<Integer>());
			Community.get(i).add(i);
			Result.add(i, new ArrayList<Integer>());

		}
		memDegree = new float[Community.size()];
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
		System.out.println("�ھ�����:");
		for (int i = 0; i < Community.size(); i++) {
			if (!Community.get(i).isEmpty() && !Community.get(i).contains(null)) {
				System.out.println(Community.get(i));
			}
		}
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
	 * @param memDegree1
	 *            ����������
	 */
	@SuppressWarnings("unchecked")
	public void initalCom_f1(int com, float[] memDegree1) {
		qMatrix = unsigned.qMatrix(mMatrix);

		for (int i = 0; i < Community.get(com).size(); i++) {
			int j = (Integer) Community.get(com).get(i);
			if (memDegree1[j] < f1) {
				System.out.printf("\n%d %f\n", j, memDegree1[j]);
				System.out.println(i);
				Community.get(com).set(i, null);
				System.out.print(Community);
				memDegree1[j] = 0; // ��û�кϲ�����������������
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
				System.out.println(Community.get(com).get(i));
				unsigned.reqMatrix(qMatrix,
						(Integer) Community.get(com).get(i), com);
				unsigned.printqMatris(qMatrix);
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
	public void extendCom_f2(int init, float[] memDegree2) {
		for (int i = 1; i < memDegree2.length; i++) {
			if (!Community.get(init).contains(i) && memDegree2[i] > f2) {
				System.out.printf("�ڵ�Ϊ��%d\n", i);

				// �ж�ģ��������Ƿ����0
				if (qMatrix[i][init] > 0) {
					Community.get(init).add(i);
					System.out.println("ģ�������������,�ϲ�:");
					unsigned.reqMatrix(qMatrix, i, init);
					unsigned.printqMatris(qMatrix);
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
		System.out.println("Community:");
		printCom(Community);
		System.out.println("Result:");
		printCom(Result);
	}

	/**
	 * �ϲ������õ�ȫ��ģ��Ⱦ���
	 */
	@SuppressWarnings("unchecked")
	public void mergeCom() {

		Q = modularity.unsignedGlobalModularity(mMatrix, Result);
		QMatrix = new float[mMatrix.length][mMatrix.length];

		for (int i = 1; i < Community.size(); i++) {
			if (!Community.get(i).isEmpty()) {
				for (int j = i + 1; j < Community.size(); j++) {
					if (!Community.get(j).isEmpty()) {

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
