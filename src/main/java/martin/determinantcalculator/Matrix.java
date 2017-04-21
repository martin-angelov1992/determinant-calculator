package martin.determinantcalculator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Matrix {
	private float[][] matrix;

	private Matrix() {
	}

	public void buildFromFile(String file) {
		try (Stream<String> stream = Files.lines(Paths.get(file))) {
			stream.forEach(System.out::println);
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
	}

	public float get(int n, int m) {
		return matrix[n][m];
	}
}