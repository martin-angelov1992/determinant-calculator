package martin.determinantcalculator;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;

public class Calculator {
	public long calcDeterminant(Matrix matrix) {
		ForkJoinPool pool = new ForkJoinPool();

		pool.invoke(task)
	}

	private class ForkCalcDeterminant extends RecursiveTask<Float> {
		private float[][] matrix;

		public ForkCalcDeterminant(float[][] matrix) {
			this.matrix = matrix;
		}

		@Override
		protected Float compute() {
			float result = 0;
			float temporary[][];
			
			for (int i = 0; i < matrix[0].length; i++) {
				temporary = new float[matrix.length - 1][matrix[0].length - 1];

				for (int j = 1; j < matrix.length; j++) {
					for (int k = 0; k < matrix[0].length; k++) {
						if (k < i) {
							temporary[j - 1][k] = matrix[j][k];
						} else if (k > i) {
							temporary[j - 1][k - 1] = matrix[j][k];
						}
					}
				}

				result += matrix[0][i] * Math.pow (-1, (double) i) * new ForkCalcDeterminant(temporary).join();
			}

			return result;
		}
	}
}
