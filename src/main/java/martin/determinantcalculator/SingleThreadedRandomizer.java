package martin.determinantcalculator;

import java.util.concurrent.ThreadLocalRandom;

public class SingleThreadedRandomizer implements Randomizer {

	// Creates a random matrix
	public void randomize(double[][] matrix, int threads) {
		int n = matrix.length;

		for (int i = 0; i < n; ++i) {
			for (int j = 0; j < n; ++j) {
				matrix[i][j] = ThreadLocalRandom.current().nextInt();
			}
		}
	}
}