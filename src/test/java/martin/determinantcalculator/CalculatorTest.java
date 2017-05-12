package martin.determinantcalculator;

import org.junit.Test;

import junit.framework.Assert;

public class CalculatorTest {
	@Test
	public void testCalc7x7() {
		
	}

	@Test
	public void testCalc6x6() {
		
	}

	@Test
	public void testCalc5x5() {
		
	}

	@Test
	public void testCalc4x4() {
		
	}

	@Test
	public void testCalc3x3() {
		double[][] matrix = {{1, 2, 3},{4, 5, 7},{7, 8, 10}};

		assertDeterminant(matrix, 3.0);
	}

	@Test
	public void testCalc2x2() {
		
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