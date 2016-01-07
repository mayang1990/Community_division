package Community_division;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Mayang
 * 
 */

public class unsignedNetExcel {

	private int[][] mMatrix; // �ڽӾ���
	static float[][] qMatrix; // ģ�����������
	int LEN; // ��ꇵĴ�С

	static int dVector[]; // ������
	static float[] memDegree;// ������
	float f1 = 1;
	float f2 = (float) 1.0 / 2;

	float Q;// ģ�K��
	int pNum = 0;// �����еı�����
	float pVector[];

	/**
	 * �����޷�������
	 * 
	 * @param array
	 */
	@SuppressWarnings("unchecked")
	public unsignedNetExcel(int[][] array) {
		mMatrix = array;
		LEN = mMatrix.length;

		for (int i = 0; i < readFileformExcel.matrix.length; i++) {
			Community.add(i, new ArrayList<Integer>());
			Community.get(i).add(i);
			Result.add(i, new ArrayList<Integer>());
		}
		memDegree = new float[Community.size()];
	}

	/**
	 * ��ӡ�������ͼ
	 */
	public void printMatrix() {
		System.out.printf("unsigned Martix Graph:\n");
		for (int i = 1; i < LEN; i++) {
			for (int j = 1; j < LEN; j++)
				System.out.printf("%3d", mMatrix[i][j]);
			System.out.printf("\n");
		}
	}

	/**
	 * ��ӡ��ʼģ�����������
	 */
	public void printqMatris() {
		System.out.printf("\nQ���� Martix Graph:\n");
		for (int i = 0; i < LEN; i++) {
			for (int j = 0; j < LEN; j++)
				System.out.printf("%10f", qMatrix[i][j]);
			System.out.printf("\n");
		}
	}

	/**
	 * ����ڵ���ǿ��
	 * 
	 * @param i
	 *            ���c
	 * @return
	 */
	public int pStrength(int i) {
		int ps = 0;
		for (int j = 0; j < LEN; j++) {
			if (mMatrix[i][j] == 1)
				ps++;
		}
		return ps;
	}

	/**
	 * ���������
	 */
	public void dVector() {
		dVector = new int[LEN];
		for (int i = 1; i < LEN; i++) {
			dVector[i] = pStrength(i);
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
		for (int i = 1; i < LEN; i++) {
			if (array[i] > max) {
				max = array[i];
				index = i;
			}
		}
		return index;
	}

	/**
	 * ����ģ��Ⱦ���
	 */
	public void qMatrix() {

		pVector = new float[LEN];
		qMatrix = new float[LEN][LEN];

		// �����������ߵĺ͡�
		for (int i = 0; i < LEN; i++) {
			for (int j = 0; j < LEN; j++) {
				if (mMatrix[i][j] == 1) {
					pNum++;
				}
			}
		}
		System.out.printf("������ = %d", pNum);

		// �õ���ǿ�����顣
		for (int i = 0; i < LEN; i++) {
			pVector[i] = (float) pStrength(i) / pNum;
		}
		for (int i = 0; i < LEN; i++) {
			System.out.printf("\n���� = %d ��ǿ�� = %f\n", pStrength(i), pVector[i]);
		}

		// �����ʼģ�����������
		for (int i = 0; i < LEN; i++) {
			for (int j = 0; j < LEN; j++) {
				if (mMatrix[i][j] == 1) {
					qMatrix[i][j] = (float) 1 / pNum - pVector[i] * pVector[j];
				}
			}
		}

		// �����ʼģ�����������
		printqMatris();

	}

	/**
	 * ����ģ�����������
	 * 
	 * @param array
	 *            ����
	 * @param cow
	 *            ������
	 * @param col
	 *            ������
	 */
	public void reqMatrix(float[][] array, int cow, int col) {
		for (int k = 0; k < array.length; k++) {
			if (array[cow][k] != 0 && array[col][k] != 0 && k != cow
					&& k != col) {

				array[col][k] = array[cow][k] + array[col][k];

			} else if (array[cow][k] == 0 && array[col][k] != 0 && k != cow
					&& k != col) {
				array[col][k] = array[col][k] - 2 * (pVector[cow] * pVector[k]);

			} else if (array[cow][k] != 0 && array[col][k] == 0 && k != cow
					&& k != col) {
				array[col][k] = array[cow][k] - 2 * (pVector[col] * pVector[k]);

			}
			array[k][col] = array[col][k];
			array[k][cow] = 0;
			array[cow][k] = 0;
		}
		pVector[col] = pVector[cow] + pVector[col];
		pVector[cow] = 0;

		for (int m = 0; m < mMatrix.length; m++) {

			System.out.printf("\npVecter[%d] = %f", m, pVector[m]);
			System.out.printf("\n");
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
					/ pStrength((Integer) Community.get(com).get(i));

			System.out.printf("%d %f %d %f\n", Community.get(com).get(i), d,
					pStrength((Integer) Community.get(com).get(i)),
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
				System.out.println(i);
				reqMatrix(qMatrix, (Integer) Community.get(com).get(i), com);
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
				printqMatris();

				// �ж�ģ��������Ƿ����0
				if (qMatrix[i][init] > 0) {
					Community.get(init).add(i);
					reqMatrix(qMatrix, i, init);
					printqMatris();
				}
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
		Community.get(init).clear();

		// ���Community��Result
		for (int i = 1; i < Community.size(); i++) {
			if (!Community.get(i).isEmpty() && !Community.get(i).contains(null)) {
				System.out.println(Community.get(i));
			}
		}
		for (int i = 1; i < Result.size(); i++) {
			if (!Result.get(i).isEmpty() && !Result.get(i).contains(null)) {
				System.out.println(Result.get(i));
			}
		}
	}

	@SuppressWarnings("rawtypes")
	List<List> Community = new ArrayList<List>();
	@SuppressWarnings("rawtypes")
	List<List> Result = new ArrayList<List>();

}
