package martin.determinantcalculator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Matrix {
	private float[][] matrix;

	private Matrix() {
	}

	public void buildFromFile(String file) {
		try (Stream<String> stream = Files.lines(Paths.get(file))) {
			int n = Integer.valueOf(stream.findFirst().get().trim());
			matrix = new float[n][n];
			stream.forEach(new FileLineParser());
		} catch (IOException e) {
			System.err.println("unable to open file");
			System.exit(-1);
		}
	}

	public void buildRandomly(int threadsCount) {
		Randomizer[] threads = new Randomizer[threadsCount];

		for (int i = 0; i < threadsCount; ++i) {
			threads[i] = new Randomizer();
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

	public float get(int n, int m) {
		return matrix[n][m];
	}

	private class FileLineParser implements Consumer<String> {

		private int rowNum = 0;

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