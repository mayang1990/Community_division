package Community_division;

import java.io.*;

import jxl.*;

/**
 * 
 * @author Mayang
 * 
 */

public class readFileExcel {

	static int[][] matrix = null;

	public readFileExcel() {

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
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		float Q = 0;
		int initalNode = 0;

		// ��Excel�ļ�
		readFileExcel readExcel = new readFileExcel();
		unsignedNetExcel pG2 = new unsignedNetExcel(matrix);
		// Community resultCom = new Community();

		// ��ӡͼ
		pG2.printMatrix();
		// �õ�������
		pG2.dVector();
		// ѡ����ʼ�ڵ�
		initalNode = pG2.maxValue(unsignedNetExcel.dVector);
		System.out.println(initalNode);
		// ѡ����ʼ����
		pG2.initalCom(initalNode, matrix);
		// �������ʼ������ÿһ���ڵ�������ȣ����Ҵ洢������memDegree��
		pG2.memberDegree(initalNode, matrix);
		// �����ʼ����
		pG2.initalCom_f1(initalNode, unsignedNetExcel.memDegree);
	}

}
