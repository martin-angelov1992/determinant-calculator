package martin.determinantcalculator;

import org.junit.Test;

import junit.framework.Assert;

public class CalculatorTest {

	@Test
	public void testCalc() {
		Calculator calc = new Calculator(new Logging(false));

		double[][] matrix = {{1, 2, 3},{4, 5, 7},{7, 8, 10}};
		double result = calc.calcDeterminant(matrix, 4);

		Assert.assertEquals(3.0, result);;
	}
}
