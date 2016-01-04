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
	float Q;// 模塊度

	static int dVector[]; // 度向量
	static float[] memDegree;// 隶属度
	float f1 = 1;
	float f2 = (float) 1.0 / 2;

	int totalNum = 0, pNum = 0;// 矩阵中的边数，正边数负边数。
	float pRatio;// 矩阵中正边和负边分别占得比例。
	float pVector[];

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
				reqMatrix(qMatrix,i,com);
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

	/**
	 * 对扩展社区进行初始阈值f2判断,的到最终的初始社区
	 * 
	 * @param init
	 * @param memDegree2
	 */
	@SuppressWarnings("unchecked")
	public void extendCom_f2(int init, float[] memDegree2) {
		for (int i = 1; i < memDegree2.length; i++) {
			if (!Community.get(init).contains(i) && memDegree2[i] > f2) {
				Community.get(init).add(i);
				memDegree2[i] = 0;
			}
		}
		for (int i = 1; i < memDegree2.length; i++) {
			if (i != init) {
				Community.get(i).clear();
			}
		}
		Result.addAll(Community);
		// 输出结果
		for (int i = 0; i < Result.size(); i++) {
			if (!Result.get(i).isEmpty() && !Result.get(i).contains(null)) {
				System.out.println(Result.get(i));
			}
		}
	}

	/**
	 * 打印初始模块度增量矩阵
	 */
	public void printqMatris() {
		System.out.printf("\nsigned Q:\n");
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
	 * 计算模块度矩阵。
	 */
	public void qMatrix() {

		pVector = new float[LEN];
		//nVector = new float[LEN];
		qMatrix = new float[LEN][LEN];

		// 计算所有正边和负边的和。 pRatio為正邊所佔比例，nRatio為負邊所佔比例。
		for (int i = 0; i < LEN; i++) {
			for (int j = 0; j < LEN; j++) {
				if (mMatrix[i][j] == 1) {
					pNum++;
				} else if (mMatrix[i][j] == -1) {
					//nNum++;
				}
			}
		}
		totalNum = pNum ;
		pRatio = (float) pNum / totalNum;
		//nRatio = (float) nNum / totalNum;
		System.out.printf("%d", pNum);
		System.out.printf(" %f", pRatio);

		// 得到正强度数组和负强度数组。
		for (int i = 0; i < LEN; i++) {
			pVector[i] = (float) pStrength(i) / pNum;
			// nVector[i] = (float) nStrength(i) / nNum;
		}

		for (int i = 0; i < LEN; i++) {

			System.out.printf("\n%d", pStrength(i));
			//System.out.printf(" %d", nStrength(i));
			System.out.printf("\n%f", pVector[i]);
			//System.out.printf(" %f", nVector[i]);
			System.out.printf("\n");
		}

		// 计算初始模块度增量矩阵。
		for (int i = 0; i < LEN; i++) {
			for (int j = 0; j < LEN; j++) {
				if (mMatrix[i][j] == 1) {
					qMatrix[i][j] = pRatio
							* ((float) 1 / pNum - pVector[i] * pVector[j]);
				} else if (mMatrix[i][j] == -1) {
					qMatrix[i][j] = -pRatio * pVector[i] * pVector[j];
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
	 * @param cow
	 * @param col
	 */
	public void reqMatrix(float[][] array, int cow, int col) {
		for (int k = 0; k < array.length; k++) {
			if (array[cow][k] != 0 && array[col][k] != 0 && k != cow
					&& k != col) {

				array[col][k] = array[cow][k] + array[col][k];

			} else if (array[cow][k] == 0 && array[col][k] != 0 && k != cow
					&& k != col) {
				array[col][k] = array[col][k]
						- 2
						* (pRatio * pVector[cow] * pVector[k] );

			} else if (array[cow][k] != 0 && array[col][k] == 0 && k != cow
					&& k != col) {
				array[col][k] = array[cow][k]
						- 2
						* (pRatio * pVector[col] * pVector[k] );

			}
			array[k][col] = array[col][k];
			array[k][cow] = 0;
			array[cow][k] = 0;
		}
		pVector[col] = pVector[cow] + pVector[col];
		//nVector[col] = nVector[cow] + nVector[col];
		pVector[cow] = 0;
		//nVector[cow] = 0;

		for (int m = 0; m < mMatrix.length; m++) {

			System.out.printf("pVecter[%d] = %f",m, pVector[m]);
			//System.out.printf("%f", nVector[m]);
			System.out.printf("\n");
		}

	}

	@SuppressWarnings("rawtypes")
	List<List> Community = new ArrayList<List>();
	List<List> Result = new ArrayList<List>();

	// List<Integer> list = new ArrayList<Integer>();
}
