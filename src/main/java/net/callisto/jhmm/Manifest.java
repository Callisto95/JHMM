package net.callisto.jhmm;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.util.*;
import java.util.zip.*;

public class Manifest {
	@JsonProperty("Version")
	int          version;
	@JsonProperty("Guid")
	UUID         guid;
	@JsonProperty("Name")
	String       name;
	@JsonProperty("Description")
	String       description;
	@JsonProperty("Options")
	List<Option> options;
	
	public static Manifest fromZip(final File zipFile) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // images
		
		Manifest manifest;
		
		try (final ZipFile zip = new ZipFile(zipFile)) {
			final ZipEntry manifestEntry = zip.getEntry("manifest.json");
			
			if (manifestEntry == null) {
				return createManifestFromFile(zipFile);
			}
			
			manifest = mapper.readValue(zip.getInputStream(manifestEntry), Manifest.class);
		}
		
		return manifest;
	}
	
	private static Manifest createManifestFromFile(final File zipFile) {
		final String modName = nameFromZip(zipFile);
		
		final Manifest manifest = new Manifest();
		final Option   option   = new Option();
		
		option.name        = "(Auto generated) All files";
		option.include     = List.of(".");
		option.description = "use all files in the zip";
		option.subOptions  = List.of();
		
		manifest.name        = modName;
		manifest.description = "Auto generated manifest";
		manifest.guid        = UUID.randomUUID();
		manifest.options     = List.of(option);
		manifest.version     = 1;
		
		return manifest;
	}
	
	private static String nameFromZip(final File zipFile) {
		return zipFile
			.getName()
			.replaceAll("\\.(zip|rar|7z)", "")
			.replaceFirst("-\\d+-[^a-zA-Z]*-\\d+$", ""); // NexusMods: remove numbers
	}
	
	@Override
	public String toString() {
		return "Manifest{" + "version=" + version + ", guid=" + guid + ", name='" + name + '\'' +
			", description='" + description + '\'' + ", options=" + options + '}';
	}
}
