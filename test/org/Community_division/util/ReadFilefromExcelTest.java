package org.Community_division.util;

import static org.junit.Assert.*;

import org.Community_division.core.Matrix;
import org.Communiyt_division.util.ReadFilefromExcel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ReadFilefromExcelTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		ReadFilefromExcel readExcel = new ReadFilefromExcel();
		Matrix m = new Matrix();
		m.printMatrix(readExcel.getMatrix());
	}

}
