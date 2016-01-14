package Community_division;

/**
 * 
 * @author Mayang
 * 
 */

public class MaxValueforMatrix {
	public float getMaxValue() {
		return maxValue;
	}

	public int getCow() {
		return cow;
	}

	public int getCol() {
		return col;
	}

	private float maxValue = 0;
	private int cow, col;

	/**
	 * 构造函数，得到矩阵中的最大值和其坐标。
	 * 
	 * @param array
	 */
	public MaxValueforMatrix(float[][] array) {

		for (int i = 0; i < array.length; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i][j] > maxValue) {
					maxValue = array[i][j];
					cow = i;
					col = j;
				}
			}
		}
	}

}
