package org.Communiyt_division.util;

import java.io.File;

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

	public ReadFilefromExcel() {

		try {
			Workbook book = Workbook.getWorkbook(new File("16nodesSigned.xls"));
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
