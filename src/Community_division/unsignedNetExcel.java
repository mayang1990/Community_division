package Community_division;

import java.util.ArrayList;
import java.util.List;

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
	static float[] memDegree;// 隶属度
	float f1 = 1;
	float f2 = (float) 1.0 / 2;

	/**
	 * 处理无符号网络
	 * 
	 * @param array
	 */
	public unsignedNetExcel(int[][] array) {
		mMatrix = array;
		LEN = mMatrix.length;

		for (int i = 0; i < readFileExcel.matrix.length; i++) {
			Community.add(i, new ArrayList<Integer>());
		}
		memDegree = new float[Community.size()];
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
	 * 计算度
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
	 * 计算度向量
	 */
	public void dVector() {
		dVector = new int[LEN];
		for (int i = 1; i < LEN; i++) {
			dVector[i] = cal_k(i);
			// System.out.println(dVector[i]);
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

	/**
	 * 得到初始社团com,社团内的节点的邻居节点集合
	 * 
	 * @param com
	 * @param init
	 * @param array
	 */
	@SuppressWarnings("unchecked")
	public void neighourCom(int com, int init, int[][] array) {
		Community.get(com).add(com);
		for (int i = 0; i < Community.size(); i++) {
			if (array[com][i] == 1 && !Community.get(init).contains(i)) {
				Community.get(com).add(i);// 直接把節點加入第k個社區，應該不會重疊吧。
			}
		}
		// 输出结果
		for (int i = 0; i < Community.size(); i++) {
			if (!Community.get(i).isEmpty() && !Community.get(i).contains(null)) {
				System.out.println(Community.get(i));
			}
		}
	}

	/**
	 * 求社區com中的每一個节点和初始社区init的隶属度
	 * 
	 * @param com
	 * @param init
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
					/ cal_k((Integer) Community.get(com).get(i));

			System.out.printf("%d %f %d %f\n", Community.get(com).get(i), d,
					cal_k((Integer) Community.get(com).get(i)),
					memDegree[(Integer) Community.get(com).get(i)]);
		}
	}

	/**
	 * 对社区com进行初始阈值f1判断,的到最终的初始社区
	 * 
	 * @param com
	 */
	@SuppressWarnings("unchecked")
	public void initalCom_f1(int com) {
		for (int i = 0; i < Community.get(com).size(); i++) {
			if (memDegree[(Integer) Community.get(com).get(i)] < f1) {
				System.out.printf("%d %f\n", Community.get(com).get(i),
						memDegree[i]);
				Community.get(com).set(i, null);

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

	/**
	 * 扩展社区，求初始社区中的每一个节点的邻居和每一个邻居和初始社区之间的隶属度
	 * 
	 * @param extend
	 * @param init
	 * @param array
	 */
	public void extendCom(int extend, int init, int[][] array) {
		if (Community.get(extend).size() > 1) {
			for (int i = 1; i < Community.get(extend).size(); i++) {
				neighourCom((Integer) Community.get(extend).get(i), init, array);
				memberDegree((Integer) Community.get(extend).get(i), init,
						array);

			}
		}

	}

	@SuppressWarnings("rawtypes")
	List<List> Community = new ArrayList<List>();

	// List<Integer> list = new ArrayList<Integer>();
}
