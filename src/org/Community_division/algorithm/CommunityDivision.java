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
	float f1 = (float) 1.0 / 2; // 随便设的，根据需要调整
	float f2 = (float) 1.0 / 3; // 随便设的，根据需要调整

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
	 * 计算度向量
	 */
	public void dVector() {
		dVector = new int[mMatrix.length];
		for (int i = 1; i < mMatrix.length; i++) {
			dVector[i] = m.pDegree(mMatrix, i);
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
		for (int i = 1; i < mMatrix.length; i++) {
			if (array[i] > max) {
				max = array[i];
				index = i;
			}
		}
		return index;
	}

	/**
	 * 打印Community
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
	 * 恢复Community
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
		System.out.println("邻居社区:");
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
					/ m.pDegree(array, (Integer) Community.get(com).get(i));

			System.out.printf("%d %f %d %f\n", Community.get(com).get(i), d,
					m.pDegree(array, (Integer) Community.get(com).get(i)),
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
		qMatrix = unsigned.qMatrix(mMatrix);

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
				System.out.println(Community.get(com).get(i));
				unsigned.reqMatrix(qMatrix,
						(Integer) Community.get(com).get(i), com);
				unsigned.printqMatris(qMatrix);
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
				System.out.printf("节点为：%d\n", i);

				// 判断模块度增量是否大于0
				if (qMatrix[i][init] > 0) {
					Community.get(init).add(i);
					System.out.println("模块度增量大于零,合并:");
					unsigned.reqMatrix(qMatrix, i, init);
					unsigned.printqMatris(qMatrix);
				}
				System.out.println("模块度增量小于零,不合并.");
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

		// 输出Community和Result
		System.out.println("Community:");
		printCom(Community);
		System.out.println("Result:");
		printCom(Result);
	}

	/**
	 * 合并社区得到全局模块度矩阵
	 */
	@SuppressWarnings("unchecked")
	public void mergeCom() {

		Q = modularity.unsignedGlobalModularity(mMatrix, Result);
		QMatrix = new float[mMatrix.length][mMatrix.length];

		for (int i = 1; i < Community.size(); i++) {
			if (!Community.get(i).isEmpty()) {
				for (int j = i + 1; j < Community.size(); j++) {
					if (!Community.get(j).isEmpty()) {

						// 将Ci加到Cj上
						Community.get(j).addAll(Community.get(i));

						// 清空Ci
						Community.get(i).clear();

						// 求组合的模块度
						QMatrix[i][j] = modularity.unsignedGlobalModularity(
								mMatrix, Community);
						// 清空Cj
						Community.get(j).clear();

						// 恢复Community
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
