package net.callisto.jhmm;

import java.io.*;
import java.util.*;

public class Main {
	public static final QuickLogger LOGGER = new QuickLogger("JHMM");
	
	public static void main(String[] args) throws IOException {
		LOGGER.enabledDebug();
		Configuration configuration = new Configuration();
		
		final File modZip = new File(args[0]);
		
		LOGGER.debug(modZip);
		
		try (UnZipper zipper = new UnZipper(modZip, configuration.extractionPath)) {
			final Optional<Manifest> containedManifest = Manifest.fromDir(configuration.extractionPath);
			
			Manifest manifest = containedManifest.orElseGet(() -> Manifest.createManifestFromZip(modZip));
			
			new TerminalUI().showUI(manifest);
			
			LOGGER.debug(manifest);
			
			final List<SubOption> selectedSubOptions = manifest.getSelectedOptions();
			
			selectedSubOptions.forEach(o -> System.out.println(o.getFiles(configuration.extractionPath)));
		}
	}
}
