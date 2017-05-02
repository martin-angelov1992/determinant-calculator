package martin.determinantcalculator;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Calculator {
	private Logging logging;

	public Calculator(Logging logging) {
		this.logging = logging;
	}

	public double calcDeterminant(double[][] matrix, int tasks) {
    	double result = 0;

		ExecutorService es = Executors.newFixedThreadPool(tasks);
    	Future<Double>[] futures = new Future[matrix.length];

        for (int j1 = 0; j1 < matrix.length; j1++) {
        	double[][] subMatrix = ParallelDeterminants.generateSubArray(matrix, j1);
        	futures[j1] = es.submit(new ParallelDeterminants(subMatrix, "Thread"+(j1+1), logging));
        }

        for (int j1 = 0; j1 < matrix.length; j1++) {
        	try {
				result += Math.pow(-1.0, 1.0 + j1 + 1.0) * matrix[0][j1] * futures[j1].get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
        }

        logging.log("Threads used in current run: "+matrix.length);

        return result;
	}
}