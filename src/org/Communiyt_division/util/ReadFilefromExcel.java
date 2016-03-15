package org.Communiyt_division.util;

import java.io.File;

import org.Community_division.core.Matrix;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * 
 * Read the adjacent matrix from excel
 * 
 */
public class ReadFilefromExcel {
	static int[][] matrix = null;

	/**
	 * main for test
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		ReadFilefromExcel readExcel = new ReadFilefromExcel();
		Matrix m = new Matrix();
		m.printMatrix(readExcel.getMatrix());
	}

	public ReadFilefromExcel() {

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

	public static int[][] getMatrix() {
		return matrix;
	}

}
