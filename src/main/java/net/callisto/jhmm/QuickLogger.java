package net.callisto.jhmm;

public class QuickLogger {
	private static final String FORMAT = "%s[%s][%s] %s";
	
	private static final String COLOUR_CYAN = "\u001B[36m";
	private static final String COLOUR_RED = "\u001B[31m";
	private static final String COLOUR_GREEN = "\u001B[32m";
	
	protected boolean verbose = false;
	
	public void disableDebug() {
		verbose = false;
	}
	
	public void enabledDebug() {
		verbose = true;
	}
	
	protected final String name;
	
	public QuickLogger(final String name) {
		this.name = name;
	}
	
	protected void formattedLog(String colour, String level, String formatString, Object...args) {
		System.out.printf(FORMAT, colour, name, level, String.format(formatString, args));
		System.out.println();
	}
	
	protected void formattedLog(String colour, String level, Object o) {
		formattedLog(colour, level, "%s", o);
	}
	
	public void debug(String format, Object...args) {
		if (verbose) {
			formattedLog(COLOUR_GREEN, "debug", format, args);
		}
	}
	
	public void debug(Object o) {
		formattedLog(COLOUR_GREEN, "debug", o);
	}
	
	public void info(String format, Object...args) {
		formattedLog(COLOUR_CYAN, "info", format, args);
	}
	
	public void info(Object o) {
		formattedLog(COLOUR_CYAN, "info", o);
	}
	
	public void error(String format, Object...args) {
		formattedLog(COLOUR_RED, "error", format, args);
	}
	
	public void error(Object o) {
		formattedLog(COLOUR_RED, "error", o);
	}
	
	public void log(String format, Object...args) {
		System.out.printf(format, args);
		System.out.println();
	}
}
