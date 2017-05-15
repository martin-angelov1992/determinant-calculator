package martin.determinantcalculator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
    	long start = System.currentTimeMillis();

    	Options options = new Options();
    	options.addOption("i", true, "Read matrix from file");
    	options.addOption("q", "Quiet mode");
    	options.addOption("t", true, "Number of threads");
    	options.addOption("n", true, "Generate random matrix with size");
    	options.addOption("o", true, "Output file");

    	CommandLineParser parser = new DefaultParser();
    	CommandLine cmd = parser.parse( options, args);

    	// Default number of threads will be 4
    	int threads = 4;

    	boolean isQuiet = cmd.hasOption('q');
    	Logging logging = new Logging(isQuiet);
    	Calculator calculator = new Calculator(logging);

    	if (cmd.hasOption('t')) {
    		threads = Integer.valueOf(cmd.getOptionValue("t"));
    	}

    	double[][] matrix;
    	if (cmd.hasOption('i')) {
    		String file = cmd.getOptionValue("i");
    		matrix = matrixUtils.readMatrixFromFile(file);
    	} else if (cmd.hasOption('n')) {
    		int size = Integer.valueOf(cmd.getOptionValue("n"));
    		matrix = matrixUtils.generateRandomMatrix(size, threads);
    	} else {
    		throw new RuntimeException("You need to provide -n or -i parameter.");
    	}

    	double result = calculator.calcDeterminant(matrix, threads);

    	if (cmd.hasOption('o')) {
    		String outputFile = cmd.getOptionValue("o");

    		writeResultToFile(result, outputFile);
    	}

    	long end = System.currentTimeMillis();

    	logging.logImportant("Total execution time for current run (millis): "+(end-start));
    }

	private void writeResultToFile(double result, String fileStr) {
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