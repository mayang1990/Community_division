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

	float Q;// 模塊度
	int pNum = 0;// 矩阵中的边数。
	float pVector[];

	/**
	 * 处理无符号网络
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
	 * 打印初始模块度增量矩阵
	 */
	public void printqMatris() {
		System.out.printf("\nQ增量 Martix Graph:\n");
		for (int i = 0; i < LEN; i++) {
			for (int j = 0; j < LEN; j++)
				System.out.printf("%10f", qMatrix[i][j]);
			System.out.printf("\n");
		}
	}

	/**
	 * 计算节点正强度
	 * 
	 * @param i
	 *            節點
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
	 * 计算度向量
	 */
	public void dVector() {
		dVector = new int[LEN];
		for (int i = 1; i < LEN; i++) {
			dVector[i] = pStrength(i);
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
	 * 计算模块度矩阵。
	 */
	public void qMatrix() {

		pVector = new float[LEN];
		qMatrix = new float[LEN][LEN];

		// 计算所有正边的和。
		for (int i = 0; i < LEN; i++) {
			for (int j = 0; j < LEN; j++) {
				if (mMatrix[i][j] == 1) {
					pNum++;
				}
			}
		}
		System.out.printf("正边数 = %d", pNum);

		// 得到正强度数组。
		for (int i = 0; i < LEN; i++) {
			pVector[i] = (float) pStrength(i) / pNum;
		}
		for (int i = 0; i < LEN; i++) {
			System.out.printf("\n正度 = %d 正强度 = %f\n", pStrength(i), pVector[i]);
		}

		// 计算初始模块度增量矩阵。
		for (int i = 0; i < LEN; i++) {
			for (int j = 0; j < LEN; j++) {
				if (mMatrix[i][j] == 1) {
					qMatrix[i][j] = (float) 1 / pNum - pVector[i] * pVector[j];
				}
			}
		}

		// 输出初始模块度增量矩阵。
		printqMatris();

	}

	/**
	 * 更新模块度增量矩阵。
	 * 
	 * @param array
	 *            矩阵
	 * @param cow
	 *            横坐标
	 * @param col
	 *            纵坐标
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
	 * 得到初始社团init,社团内的节点的邻居节点集合
	 * 
	 * @param com
	 *            节点
	 * @param init
	 *            初始社团
	 * @param array
	 */
	@SuppressWarnings("unchecked")
	public void neighourCom(int com, int init, int[][] array) {
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
	 *            社区
	 * @param init
	 *            初始社区
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
	 * 对社区com进行初始阈值f1判断,得到最终的初始社区,并且计算合并初始社区的模块度增量
	 * 
	 * @param com
	 *            社区
	 * @param memDegree1
	 *            隶属度数组
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
				memDegree1[j] = 0; // 将没有合并的社团隶属度清零
			}

		}

		// 将没有合并的社区删除
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

		// 计算合并社区的模块度增量
		for (int i = 0; i < Community.get(com).size(); i++) {
			if ((Integer) Community.get(com).get(i) != com) {
				System.out.println(i);
				reqMatrix(qMatrix, (Integer) Community.get(com).get(i), com);
			}
		}
	}

	/**
	 * 扩展社区，求初始社区中的每一个节点的邻居和每一个邻居和初始社区之间的隶属度
	 * 
	 * @param extend
	 *            扩展社区
	 * @param init
	 *            初始社区
	 * @param array
	 */
	public void extendCom(int extend, int init, int[][] array) {

		if (Community.get(extend).size() > 1) {
			for (int i = 1; i < Community.get(extend).size(); i++) {

				// 计算初始社区中每一个节点的邻居节点
				neighourCom((Integer) Community.get(extend).get(i), init, array);

				// 计算扩展社区中的每一个节点的隶属度
				memberDegree((Integer) Community.get(extend).get(i), init,
						array);

			}
		}

	}

	/**
	 * 对扩展社区进行初始阈值f2判断,得到最终的扩展社区
	 * 
	 * @param init
	 *            初始社区
	 * @param memDegree2
	 *            隶属度数组
	 */
	@SuppressWarnings("unchecked")
	public void extendCom_f2(int init, float[] memDegree2) {
		for (int i = 1; i < memDegree2.length; i++) {
			if (!Community.get(init).contains(i) && memDegree2[i] > f2) {
				printqMatris();

				// 判断模块度增量是否大于0
				if (qMatrix[i][init] > 0) {
					Community.get(init).add(i);
					reqMatrix(qMatrix, i, init);
					printqMatris();
				}
			}
		}

		// 清除掉已经划分完社区的节点
		for (int i = 0; i < Community.get(init).size(); i++) {
			if ((Integer) Community.get(init).get(i) != init) {
				Community.get((Integer) Community.get(init).get(i)).clear();
				dVector[(Integer) Community.get(init).get(i)] = 0;
			}
		}

		// 清空隶属度数组。
		for (int i = 1; i < memDegree2.length; i++) {
			if (i != init) {
				memDegree2[i] = 0;
			}
		}

		// 将结果赋值给Result。该赋值为赋值不为引用。
		for (int i = 0; i < Community.get(init).size(); i++) {
			Result.get(init).add(i, Community.get(init).get(i));
		}
		Community.get(init).clear();

		// 输出Community和Result
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
