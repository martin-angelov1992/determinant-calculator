package martin.determinantcalculator;

import java.util.concurrent.ThreadLocalRandom;

public class GridRandomizer extends Thread {

	private double[][] matrix;
	private int maxThreads;
	private int threadIndex;

	public GridRandomizer(double[][] matrix, int maxThreads, int threadIndex) {
		this.matrix = matrix;
		this.maxThreads = maxThreads;
		this.threadIndex = threadIndex;
	}

	@Override
	public void run() {
		int minCell = calcMinCell();
		int myCells = myCells();
		int maxCell = minCell + myCells;

		for (int i = minCell; i <= maxCell; ++i) {
			matrix[i/matrix.length][i%matrix.length] = ThreadLocalRandom.current().nextInt();
		}
	}

	private int myCells() {
		int leftoverRows = matrix.length % maxThreads;

		if (threadIndex < leftoverRows) {
			return (int)Math.ceil((double)matrix.length/maxThreads)+1;
		} else {
			return (int)Math.floor((double)matrix.length/maxThreads)+1;
		}
	}

	private int calcMinCell() {
		int leftoverRows = matrix.length % maxThreads;

		int ranksWithLeftover = threadIndex > leftoverRows ? leftoverRows : threadIndex;
		int ranksWithoutLeftover = threadIndex > leftoverRows ? threadIndex - leftoverRows : 0;

		return ranksWithLeftover*(int)Math.ceil((double)matrix.length/maxThreads) + 
				ranksWithoutLeftover*(int)Math.floor((double)matrix.length/maxThreads); 
	}
}