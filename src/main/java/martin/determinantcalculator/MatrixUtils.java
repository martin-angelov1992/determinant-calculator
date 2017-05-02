package martin.determinantcalculator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class MatrixUtils {
	private Randomizer randomizer = new SingleThreadedRandomizer();
	public double[][] readMatrixFromFile(String file) {
		try (Stream<String> stream = Files.lines(Paths.get(file))) {
			int n = Integer.valueOf(stream.findFirst().get().trim());
			double[][] matrix = new double[n][n];
			stream.forEach(new FileLineParser(matrix));
			return matrix;
		} catch (IOException e) {
			System.err.println("unable to open file");
			System.exit(-1);
			return null;
		}
	}

	public double[][] generateRandomMatrix(int n, int threadsCount) {
		double[][] matrix = new double[n][n];

		randomizer.randomize(matrix, threadsCount);

		return matrix;
	}

	private class FileLineParser implements Consumer<String> {

		private int rowNum = 0;
		private double[][] matrix;

		public FileLineParser(double[][] matrix) {
			this.matrix = matrix;
		}

		@Override
		public void accept(String str) {
			String parts[] = str.split(" ");

			for (int i=0; i < parts.length; ++i) {
				String part = parts[i];

				matrix[rowNum][i] = Float.valueOf(part);
			}

			++rowNum;
		}
	}
}
