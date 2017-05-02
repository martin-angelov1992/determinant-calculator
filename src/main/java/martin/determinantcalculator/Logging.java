package martin.determinantcalculator;

public class Logging {
	private boolean quiet;

	public Logging(boolean quiet) {
		this.quiet = quiet;
	}

	public void log(String str) {
		if (!quiet) {
			System.out.println(str);
		}
	}

	public void logImportant(String str) {
		System.out.println(str);
	}
}