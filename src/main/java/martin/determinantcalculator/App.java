package martin.determinantcalculator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Hello world!
 *
 */
public class App 
{
	private MatrixUtils matrixUtils = new MatrixUtils();

    public static void main( String[] args ) throws ParseException
    {
    	new App().run(args);
    }

    public void run(String[] args) throws ParseException {
    	Options options = new Options();
    	CommandLineParser parser = new DefaultParser();
    	CommandLine cmd = parser.parse( options, args);

    	int tasks = 4;

    	float[][] matrix;
    	if (cmd.hasOption('i')) {
    		String file = cmd.getOptionValue('i');
    		matrix = matrixUtils.readMatrixFromFile(file);
    	} else if (cmd.hasOption('n')) {
    		int size = Integer.valueOf(cmd.getOptionValue('n'));
    		matrix = matrixUtils.generateRandomMatrix(size);
    	} else {
    		throw new RuntimeException("You need to provide -n or -i parameter.");
    	}

    	if (cmd.hasOption('t')) {
    		tasks = Integer.valueOf(cmd.getOptionValue('t'));
    	}

    	ForkJoinPool fjPool = new ForkJoinPool(tasks);

    	DeterminantTask task = new DeterminantTask(matrix);

    	float result = fjPool.invoke(task);

    	if (cmd.hasOption('o')) {
    		String outputFile = cmd.getOptionValue('o');

    		writeResultToFile(result, outputFile);
    	}
    }

	private void writeResultToFile(float result, String fileStr) {
		File file = new File(fileStr);
		try {
			FileWriter  writer = new FileWriter(file, false);
			writer.append(String.valueOf(result));
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}