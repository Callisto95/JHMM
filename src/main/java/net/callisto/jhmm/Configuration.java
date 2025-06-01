package net.callisto.jhmm;

import java.io.*;
import java.nio.file.*;

public class Configuration {
	public final Path extractionPath;
	// TODO: don't hard code path
	// public final Path dataDirectory = Path.of("/run/media/callisto/SpeedData/SteamLibrary/steamapps/common/Helldivers 2/data");
	public final Path dataDirectory = Path.of("/tmp/JHMM/");
	
	public Configuration() {
		try {
			extractionPath = Files.createTempDirectory("JHMM");
			// only empty dirs can be deleted
			// tempDir.toFile().deleteOnExit();
			Main.LOGGER.debug("using temp dir '%s'", extractionPath);
		} catch (IOException exc) {
			throw new RuntimeException(exc);
		}
	}
}
