package Community_division;

/**
 * 
 * @author Mayang
 * 
 */

public class unsignedNetExcel {

	private int[][] mMatrix; // 邻接矩阵
	static float[][] qMatrix; // 模块度增量矩阵
	int LEN; // 矩陣的大小

	static int dVector[]; // 度向量

	public unsignedNetExcel(int[][] array) {
		mMatrix = array;
		LEN = mMatrix.length;
	}

	/**
	 * 打印矩阵队列图
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
	 * 计算度度向量
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
	 * 求向量的最大值以及返回最大值的坐标
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

}
