package martin.determinantcalculator;

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
    public static void main( String[] args ) throws ParseException
    {
    	Options options = new Options();
    	CommandLineParser parser = new DefaultParser();
    	CommandLine cmd = parser.parse( options, args);

    	int tasks = 4;

    	if (cmd.hasOption('i')) {
    		
    	} else if (cmd.hasOption('n')) {
    		
    	}

    	if (cmd.hasOption('t')) {
    		tasks = Integer.valueOf(cmd.getOptionValue('t'));
    	}
    }
}
