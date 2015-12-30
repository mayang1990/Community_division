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
	 * ����ȶ�����
	 */
	public void dVector() {
		dVector = new int[LEN];
		for (int i = 1; i < LEN; i++) {
			int k1 = 0;
			for (int j = 0; j < LEN; j++) {
				if (mMatrix[i][j] == 1)
					k1++;
			}
			dVector[i] = k1;
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
	 * �õ���ʼ����
	 * 
	 * @param k
	 * @param array
	 */
	@SuppressWarnings("unchecked")
	public void initalCom(int k, int[][] array) {
		for (int i = 1; i < LEN; i++) {
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

	@SuppressWarnings("rawtypes")
	List<List> Community = new ArrayList<List>();

}