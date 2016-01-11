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
			// ��õ�һ�����������
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
	 * ������
	 * 
	 * @param args
	 */
	@SuppressWarnings({ "unused", "unchecked", })
	public static void main(String[] args) {
		int initalNode = 0;

		// ��Excel�ļ�
		readFileformExcel readExcel = new readFileformExcel();
		unsignedNetExcel pG2 = new unsignedNetExcel(matrix);
		// Community resultCom = new Community();

		// ��ӡͼ
		pG2.printMatrix();
		// �õ���ʼģ�����������
		pG2.qMatrix();
		// �õ�������
		pG2.dVector();
		// ѡ����ʼ�ڵ�
		initalNode = pG2.maxValue(unsignedNetExcel.dVector);

		while (initalNode > 0) {
			unsignedNetExcel.dVector[initalNode] = 0;
			System.out.printf("\n");
			System.out.printf("initalNode = %d\n", initalNode);
			// ѡ����ʼ����
			pG2.neighourCom(initalNode, initalNode, matrix);
			// �������ʼ������ÿһ���ڵ�������ȣ����Ҵ洢������memDegree��
			pG2.memberDegree(initalNode, initalNode, matrix);
			// �����ʼ����
			pG2.initalCom_f1(initalNode, unsignedNetExcel.memDegree);
			for (int i = 0; i < unsignedNetExcel.memDegree.length; i++) {
				System.out
						.printf("%d = %f\n", i, unsignedNetExcel.memDegree[i]);
			}
			// ѡ����չ����
			pG2.extendCom(initalNode, initalNode, matrix);

			// �������������
			for (int i = 0; i < unsignedNetExcel.memDegree.length; i++) {
				System.out
						.printf("%d = %f\n", i, unsignedNetExcel.memDegree[i]);
			}

			// ������չ����
			pG2.extendCom_f2(initalNode, unsignedNetExcel.memDegree);

			// �������������
			for (int i = 0; i < unsignedNetExcel.memDegree.length; i++) {
				System.out
						.printf("%d = %f\n", i, unsignedNetExcel.memDegree[i]);
			}

			// ����ѡ����ʼ�ڵ�
			initalNode = pG2.maxValue(unsignedNetExcel.dVector);
			// ���������
			System.out.println("dVector ����:");
			for (int i = 0; i < unsignedNetExcel.dVector.length; i++) {
				System.out.printf("%3d", unsignedNetExcel.dVector[i]);
			}
		}

		// �ϲ�С������������
		while (true) {

			// �ϲ������õ�ȫ��ģ��Ⱦ���
			pG2.mergeCom();

			// ��ϲ��������ȫ��ģ��Ⱦ���
			MaxValue mMax = new MaxValue(unsignedNetExcel.QMatrix);
			float max = mMax.getMaxValue();
			int cow = mMax.getCow();
			int col = mMax.getCol();

			// ������ֵ����������
			System.out.printf("%f %d %d\n", max, cow, col);

			if (max > unsignedNetExcel.Q) {

				// ���Ӧ�úϲ���������ϲ�����
				unsignedNetExcel.Result.get(cow).addAll(
						unsignedNetExcel.Result.get(col));
				unsignedNetExcel.Result.get(col).clear();
				unsignedNetExcel.Q = max;
			} else {

				// ���������
				System.out.printf("ģ��� Q = %f\n", unsignedNetExcel.Q);
				unsignedNetExcel.printCom(unsignedNetExcel.Result);
				break;
			}
		}
	}
}
