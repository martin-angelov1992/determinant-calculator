package martin.determinantcalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Calculator {
	private Logging logging;

	public Calculator() {
		this.logging = new Logging(false);
	}

	public Calculator(Logging logging) {
		this.logging = logging;
	}

	public double calcDeterminant(double[][] matrix, int tasks) {
    	double result = 0;

		// Trivial 1x1 matrix
		if (matrix.length == 1) {
			return matrix[0][0];
		}
		// Trivial 2x2 matrix
		else if (matrix.length == 2) {
			return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
		}

		ExecutorService es = Executors.newFixedThreadPool(tasks);

		List<Future<Double>> futures = feedMatrixes(matrix, es);

		for (Future<Double> future : futures) {
			try {
				result += future.get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}

        logging.log("Max threads running at the same time in current run: "+tasks);

        es.shutdown();

        return result;
	}

	private List<Future<Double>> feedMatrixes(double[][] matrix, ExecutorService es) {
		List<Future<Double>> futures = new ArrayList<>(matrix.length*matrix.length-1);

		for (int j1 = 0; j1<matrix.length; ++j1) {
			for (int j2 = 0; j2<matrix.length; ++j2) {
				if (j1 == j2) {
					continue;
				}

				double[][] subMatrix = generateSubMatrix(j1, j2, matrix);

				double coeff = matrix[j1][0]*matrix[j2][1];

				futures.add(es.submit(new ParallelDeterminants(j1, j2, coeff, subMatrix, logging)));
			}
		}

		return futures;
	}

	private double[][] generateSubMatrix(int j1, int j2, double[][] matrix) {
		double[][] newMatrix = new double[matrix.length-2][matrix.length-2];

		int newMatrixJ = 0;

		for (int j=0; j<matrix.length; ++j) {
			if (j == j1 || j == j2) {
				continue;
			}

			for (int i=0; i<newMatrix.length; ++i) {
	
				newMatrix[newMatrixJ][i] = matrix[j][i+2];
			}

			++newMatrixJ;
		}

		return newMatrix;
	}
}