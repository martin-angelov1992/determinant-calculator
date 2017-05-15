package martin.determinantcalculator;

import org.junit.Test;

import junit.framework.Assert;

public class CalculatorTest {
	@Test
	public void testCalc7x7() {
		double[][] matrix = {{1, 4, 5, 2, 9, 10, 12},
				{9, 2, 8, 7, 12, 6, 14},
				{29, 20, 23, 24, 12, 8, 4},
				{8, 1, 3, 4, 5, 6, 10},
				{9, 18, 7, 16, 79, 8, 71},
				{84, 71, 7, 8, 12, 6, 12},
				{7, 12, 1, 7, 1, 3, 4}};

			assertDeterminant(matrix, 164385264);
	}

	@Test
	public void testCalc6x6() {
		double[][] matrix = {{1, 4, 5, 1, 2, 3},
				{4, 5, 6, 7, 10, 19},
				{12, 99, 22, 88, 11, 99},
				{44, 33, 41, 43, 87, 61},
				{81, 72, 62, 92, 71, 12},
				{1, 7, 8, 1, 4, 5}};

			assertDeterminant(matrix, 370735);
	}

	@Test
	public void testCalc5x5() {
		double[][] matrix = {{4, 5, 3, 2, 67},
			{1, 3, 4, 5, 6},
			{7, 8, 12, 14, 13},
			{16, 93, 92, 30, 42},
			{31, 33, 44, 11, 22}};

		assertDeterminant(matrix, -599604);
	}

	@Test
	public void testCalc4x4() {
		double[][] matrix = {{53, 17, 51, 25},
				{44, 27, 93, 26},
				{72, 69, 77, 67},
				{45, 59, 3, 46}};

		assertDeterminant(matrix, 1329824.0);
	}

	@Test
	public void testCalc3x3() {
		double[][] matrix = {{1, 2, 3},{4, 5, 7},{7, 8, 10}};

		assertDeterminant(matrix, 3.0);
	}

	@Test
	public void testCalc2x2() {
		double[][] matrix = {{2, 3},{5, 7}};

		assertDeterminant(matrix, -1.0);
	}

	@Test
	public void testCalc1x1() {
		double[][] matrix = {{2}};

		assertDeterminant(matrix, 2);
	}

	private void assertDeterminant(double[][] matrix, double determinant) {
		Calculator calc = new Calculator();

		double result = calc.calcDeterminant(matrix, 7);

		Assert.assertEquals(determinant, result);;
	}
}