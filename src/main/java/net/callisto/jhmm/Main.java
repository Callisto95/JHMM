package net.callisto.jhmm;

import java.io.*;
import java.util.*;

public class Main {
	public static final QuickLogger LOGGER = new QuickLogger("JHMM");
	
	public static void main(String[] args) throws IOException {
		LOGGER.enabledDebug();
		Configuration configuration = new Configuration();
		
		final File manifestFile = new File(args[0]);
		
		LOGGER.debug(manifestFile);
		
		final Manifest manifest = Manifest.fromZip(manifestFile, configuration.tempDir);
		
		// LOGGER.debug(manifest);
		
		new TerminalUI().showUI(manifest);
		
		LOGGER.debug(manifest);
		
		final List<SubOption> selectedSubOptions = manifest.getSelectedOptions();
		
		selectedSubOptions.forEach(o -> System.out.println(o.include));
	}
}
