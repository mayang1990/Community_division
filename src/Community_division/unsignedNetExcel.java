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
	float f1 = 2;

	/**
	 * �����޷�������
	 * 
	 * @param array
	 */
	public unsignedNetExcel(int[][] array) {
		mMatrix = array;
		LEN = mMatrix.length;

		for (int i = 0; i < readFileExcel.matrix.length; i++) {
			Community.add(i, new ArrayList<Integer>());
		}
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
	 * �����
	 * 
	 * @param i
	 * @return
	 */
	public int cal_k(int i) {
		int k1 = 0;
		for (int j = 0; j < LEN; j++) {
			if (mMatrix[i][j] == 1)
				k1++;
		}
		return k1;
	}

	/**
	 * ����ȶ�����
	 */
	public void dVector() {
		dVector = new int[LEN];
		for (int i = 1; i < LEN; i++) {
			dVector[i] = cal_k(i);
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
	 * �õ���ʼ����k
	 * 
	 * @param k
	 * @param array
	 */
	@SuppressWarnings("unchecked")
	public void initalCom(int k, int[][] array) {
		for (int i = 0; i < Community.size(); i++) {
			if (array[k][i] == 1) {
				Community.get(k).add(i);// ֱ�Ӱѹ��c�����k����^����ԓ�����دB�ɡ�
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
	 * ������k��ÿһ���ڵ��������
	 * 
	 * @param k
	 * @param array
	 */
	public void memberDegree(int k, int[][] array) {
		float d = 0;
		memDegree = new float[Community.get(k).size()];
		for (int i = 0; i < Community.get(k).size(); i++) {
			for (int j = 0; j < Community.get(k).size(); j++) {

				if (array[i][j] == 1) {
					d++;
				}
			}
			memDegree[i] = d / cal_k(k);
			System.out.printf("%d %f\n", Community.get(k).get(i), memDegree[i]);
		}
	}

	/**
	 * ������k���г�ʼ��ֵf1�ж�,�ĵ����յĳ�ʼ����
	 * 
	 * @param k
	 * @param array
	 */
	@SuppressWarnings("unchecked")
	public void initalCom_f1(int k, float[] array) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] < f1) {
				System.out.printf("%d %f\n", Community.get(k).get(i),
						memDegree[i]);
				Community.get(k).set(i, null);

			}
		}

		for (int i = 0; i < Community.size(); i++) {
			if (!Community.get(i).isEmpty()) {
				for (int j = 0; j < Community.get(i).size();) {
					if (Community.get(i).get(j) == null) {
						Community.get(i).remove(j);
					} else {
						j++;
					}
				}
				System.out.println(Community.get(i));
			}
		}

	}

	@SuppressWarnings("rawtypes")
	List<List> Community = new ArrayList<List>();
}
