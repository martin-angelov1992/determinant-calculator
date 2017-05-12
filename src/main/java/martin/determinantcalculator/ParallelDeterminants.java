package martin.determinantcalculator;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ParallelDeterminants implements Callable<Double> {
	private double A[][];
	// Used for logging purposes
	private String threadName;
	private Logging logging;
	private int splitDepth;
	private int j1;
	private int j2;

	public ParallelDeterminants(int j1, int j2, double[][] A, Logging logging) {
		this.A = A;
		this.logging = logging;
		this.j1 = j1;
		this.j2 = j2;
	}

	@Override
	public Double call() {
		long start = System.currentTimeMillis();

		logging.log(threadName+" started.");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public static double computeDeterminant(double matrix[][], int splitDepth, ExecutorService es, Logging logging) {
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
				double subDeterminant;
				if (m.length == splitDepth) {
					subDeterminant = computeInParallel(m, splitDepth, es, logging);
				} else {
					subDeterminant = computeDeterminant(m, splitDepth, es, logging);
				}

				res += Math.pow(-1.0, 1.0 + j1 + 1.0) * matrix[0][j1] * subDeterminant;
			}
		}
		return res;
	}

	public static double computeInParallel(double matrix[][], int splitDepth, ExecutorService es, Logging logging) {
		double result = 0;

    	Future<Double>[] futures = new Future[matrix.length];

    	System.out.println("Paralelizing when size="+matrix.length);
    	// Submit every submatrix to a different thread
        for (int j1 = 0; j1 < matrix.length; j1++) {
        	double[][] subMatrix = ParallelDeterminants.generateSubArray(matrix, j1);
        	futures[j1] = es.submit(new ParallelDeterminants(es, splitDepth, subMatrix, "Thread"+(j1+1), logging));
        }

    	System.out.println("Done paralelizing");
        
        for (int j1 = 0; j1 < matrix.length; j1++) {
        	try {
        		// Time to sum up the results from the calculations
				result += Math.pow(-1.0, 1.0 + j1 + 1.0) * matrix[0][j1] * futures[j1].get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
        }

        return result;
	}
}