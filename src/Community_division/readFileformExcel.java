package Community_division;

import java.io.*;

import jxl.*;

/**
 * 
 * @author Mayang
 * 
 */

public class readFileformExcel {

	static int[][] matrix = null;

	public readFileformExcel() {

		try {
			Workbook book = Workbook.getWorkbook(new File("kong.xls"));
			// 获得第一个工作表对象
			Sheet sheet = book.getSheet(0);
			matrix = new int[sheet.getRows()][sheet.getColumns()];
			for (int i = 1; i < sheet.getRows(); i++) {
				for (int j = 1; j < sheet.getColumns(); j++) {
					Cell cell = sheet.getCell(j, i);
					matrix[i][j] = Integer.valueOf(cell.getContents());
					// System.out.print(matrix[i][j] + "  ");
				}
				// System.out.println();
			}
			book.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * 主函数
	 * 
	 * @param args
	 */
	@SuppressWarnings({ "unused", "unchecked", })
	public static void main(String[] args) {
		int initalNode = 0;

		// 读Excel文件
		readFileformExcel readExcel = new readFileformExcel();
		unsignedNetExcel pG2 = new unsignedNetExcel(matrix);
		// Community resultCom = new Community();

		// 打印图
		pG2.printMatrix();
		// 得到初始模块度增量矩阵
		pG2.qMatrix();
		// 得到度向量
		pG2.dVector();
		// 选出初始节点
		initalNode = pG2.maxValue(unsignedNetExcel.dVector);

		while (initalNode > 0) {
			unsignedNetExcel.dVector[initalNode] = 0;
			System.out.printf("\n");
			System.out.printf("initalNode = %d\n", initalNode);
			// 选出初始社区
			pG2.neighourCom(initalNode, initalNode, matrix);
			// 计算出初始社区中每一个节点的隶属度，并且存储在数组memDegree中
			pG2.memberDegree(initalNode, initalNode, matrix);
			// 处理初始社区
			pG2.initalCom_f1(initalNode, unsignedNetExcel.memDegree);
			for (int i = 0; i < unsignedNetExcel.memDegree.length; i++) {
				System.out
						.printf("%d = %f\n", i, unsignedNetExcel.memDegree[i]);
			}
			// 选出扩展社区
			pG2.extendCom(initalNode, initalNode, matrix);

			// 输出隶属度数组
			for (int i = 0; i < unsignedNetExcel.memDegree.length; i++) {
				System.out
						.printf("%d = %f\n", i, unsignedNetExcel.memDegree[i]);
			}

			// 处理扩展社区
			pG2.extendCom_f2(initalNode, unsignedNetExcel.memDegree);

			// 输出隶属度数组
			for (int i = 0; i < unsignedNetExcel.memDegree.length; i++) {
				System.out
						.printf("%d = %f\n", i, unsignedNetExcel.memDegree[i]);
			}

			// 重新选出初始节点
			initalNode = pG2.maxValue(unsignedNetExcel.dVector);
			// 输出度向量
			System.out.println("dVector 数组:");
			for (int i = 0; i < unsignedNetExcel.dVector.length; i++) {
				System.out.printf("%3d", unsignedNetExcel.dVector[i]);
			}
		}

		// 合并小社区并输出结果
		while (true) {

			// 合并社区得到全局模块度矩阵
			pG2.mergeCom();

			// 求合并社区后的全局模块度矩阵
			MaxValue mMax = new MaxValue(unsignedNetExcel.QMatrix);
			float max = mMax.getMaxValue();
			int cow = mMax.getCow();
			int col = mMax.getCol();

			// 输出最大值和它的坐标
			System.out.printf("%f %d %d\n", max, cow, col);

			if (max > unsignedNetExcel.Q) {

				// 如果应该合并社区，则合并社区
				unsignedNetExcel.Result.get(cow).addAll(
						unsignedNetExcel.Result.get(col));
				unsignedNetExcel.Result.get(col).clear();
				unsignedNetExcel.Q = max;
			} else {

				// 最后输出结果
				System.out.printf("模块度 Q = %f\n", unsignedNetExcel.Q);
				unsignedNetExcel.printCom(unsignedNetExcel.Result);
				break;
			}
		}
	}
}
