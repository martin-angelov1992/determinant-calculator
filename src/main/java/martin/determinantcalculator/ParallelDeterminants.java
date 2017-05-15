package martin.determinantcalculator;

import java.util.concurrent.Callable;

public class ParallelDeterminants implements Callable<Double> {
	private double A[][];
	// Used for logging purposes
	private String threadName;
	private Logging logging;
	private int j1;
	private int j2;
	private double coeff;
	private static int threadsCounter = 1;

	public ParallelDeterminants(int j1, int j2, double coeff, double[][] A, Logging logging) {
		this.A = A;
		this.logging = logging;
		this.j1 = j1;
		this.j2 = j2;
		this.coeff = coeff;
		this.threadName = "Thread"+threadsCounter;
		++threadsCounter;
	}

	@Override
	public Double call() {
		long start = System.currentTimeMillis();

		logging.log(threadName+" started.");

		int power = j1+j2;

		if (j1 < j2) {
			++power;
		}

		double result = Math.pow(-1.0, power)*coeff*computeDeterminant(A);

		logging.log(threadName+" stopped.");

		long end = System.currentTimeMillis();

		String str = String.format("%s execution time was (millis): %d", threadName, end - start);

		logging.log(str);

		return result;
	}

	public static double[][] generateSubArray(double A[][], int j1) {
		double[][] m = new double[A.length - 1][];
		for (int k = 0; k < A.length - 1; k++)
			m[k] = new double[A.length - 1];

		for (int i = 1; i < A.length; i++) {
			int j2 = 0;
			for (int j = 0; j < A.length; j++) {
				if (j == j1)
					continue;
				m[i - 1][j2] = A[i][j];
				j2++;
			}
		}
		return m;
	}

	public double computeDeterminant(double matrix[][]) {
		double res;

		// Trivial 1x1 matrix
		if (matrix.length == 1)
			res = matrix[0][0];
		// Trivial 2x2 matrix
		else if (matrix.length == 2)
			res = matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
		// NxN matrix
		else {
			res = 0;
			for (int j1 = 0; j1 < matrix.length; j1++) {
				double[][] m = generateSubArray(matrix, j1);
				double subDeterminant = computeDeterminant(m);

				res += Math.pow(-1.0, j1) * matrix[0][j1] * subDeterminant;
			}
		}

		return res;
	}
}