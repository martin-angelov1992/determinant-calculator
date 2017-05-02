package martin.determinantcalculator;

public class MiltiThreadedRandomizer implements Randomizer {

	public void randomize(double[][] matrix, int threadsCount) {
		GridRandomizer[] threads = new GridRandomizer[threadsCount];

		for (int i = 0; i < threadsCount; ++i) {
			threads[i] = new GridRandomizer(matrix, threadsCount, i);
			threads[i].start();
		}

		for (int i = 0; i < threadsCount; ++i) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}