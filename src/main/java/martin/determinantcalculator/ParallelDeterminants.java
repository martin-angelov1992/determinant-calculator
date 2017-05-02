package martin.determinantcalculator;

import java.util.concurrent.Callable;

public class ParallelDeterminants implements Callable<Double> {
	private double A[][];
	private String threadName;
	private Logging logging;

	public ParallelDeterminants(double[][] A, String threadName, Logging logging) {
		this.A = A;
		this.threadName = threadName;
		this.logging = logging;
	}

	@Override
	public Double call() {
		long start = System.currentTimeMillis();

		logging.log(threadName+" started.");

		double result = computeDeterminant(A);

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

	public double computeDeterminant(double A[][]) {
		double res;

		// Trivial 1x1 matrix
		if (A.length == 1)
			res = A[0][0];
		// Trivial 2x2 matrix
		else if (A.length == 2)
			res = A[0][0] * A[1][1] - A[1][0] * A[0][1];
		// NxN matrix
		else {
			res = 0;
			for (int j1 = 0; j1 < A.length; j1++) {
				double[][] m = generateSubArray(A, j1);
				res += Math.pow(-1.0, 1.0 + j1 + 1.0) * A[0][j1] * computeDeterminant(m);
			}
		}
		return res;
	}
}
