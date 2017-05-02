package martin.determinantcalculator;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RecursiveTask;

public class DeterminantTask extends RecursiveTask<Float> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private float matrix[][];
	private int splitsLeft;

	public DeterminantTask(float[][] matrix) {
		this(matrix, 1);
	}

	public DeterminantTask(float[][] matrix, int splitsLeft) {
		this.matrix = matrix;
		this.splitsLeft = splitsLeft;
	}

	@Override
	protected Float compute() {
		float result = 0;

		if (splitsLeft == 0) {
			result = compute(matrix);
			return result;
		}

		DeterminantTask[] tasks = new DeterminantTask[matrix.length];

		int newSplitsLeft = --splitsLeft;

		for (int j1 = 0; j1 < matrix.length; j1++) {
			float[][] m = generateSubArray(matrix, j1);
			DeterminantTask d = new DeterminantTask(m, newSplitsLeft);
			d.fork();
			tasks[j1] = d;
		}

		for (int j1 = 0; j1 < matrix.length; j1++) {
			try {
				result += Math.pow(-1.0, 1.0 + j1 + 1.0) * matrix[0][j1] * tasks[j1].get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private float[][] generateSubArray(float matrix[][], int j1) {
		float[][] m = new float[matrix.length - 1][];
		for (int k = 0; k < matrix.length - 1; k++)
			m[k] = new float[matrix.length - 1];

		for (int i = 1; i < matrix.length; i++) {
			int j2 = 0;
			for (int j = 0; j < matrix.length; j++) {
				if (j == j1)
					continue;
				m[i - 1][j2] = matrix[i][j];
				j2++;
			}
		}
		return m;
	}

	private float compute(float[][] A) {
		float res;

		if (A.length == 1) {
			res = A[0][0];
		} else if (A.length == 2) {
			res = A[0][0] * A[1][1] - A[1][0] * A[0][1];
		} else {
			res = 0;
			for (int j1 = 0; j1 < A.length; j1++) {
				float[][] m = generateSubArray(A, j1);
				res += Math.pow(-1.0, 1.0 + j1 + 1.0) * A[0][j1] * compute(m);
			}
		}

		return res;
	}
}
