package net.callisto.jhmm;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

public class Manifest {
	@JsonProperty("Version")
	public int          version;
	@JsonProperty("Guid")
	public UUID         guid;
	@JsonProperty("Name")
	public String       name;
	@JsonProperty("Description")
	public String       description;
	@JsonProperty("Options")
	public List<Option> options;
	
	public static Optional<Manifest> fromDir(final Path directory) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // images
		
		Manifest manifest;
		
		final File manifestFile = directory.resolve("manifest.json").toFile();
		
		Main.LOGGER.debug(manifestFile);
		
		if (!manifestFile.exists()) {
			return Optional.empty();
		}
		
		manifest = mapper.readValue(manifestFile, Manifest.class);
		
		manifest.fixMissingSubOptions();
		
		return Optional.of(manifest);
	}
	
	private void fixMissingSubOptions() {
		for (Option option : this.options) {
			if (option.subOptions.isEmpty()) {
				option.subOptions.add(SubOption.ENABLED);
			}
			
			option.subOptions.addFirst(SubOption.DISABLED);
		}
	}
	
	public static Manifest createManifestFromZip(final File zipFile) {
		final String modName = nameFromZip(zipFile);
		
		final Manifest manifest = new Manifest();
		final Option   option   = new Option();
		
		option.name        = "(Auto generated) All files";
		option.include     = List.of(".");
		option.description = "use all files in the zip";
		option.subOptions  = new ArrayList<>();
		
		manifest.name        = modName;
		manifest.description = "Auto generated manifest";
		manifest.guid        = UUID.randomUUID();
		manifest.options     = List.of(option);
		manifest.version     = 1;
		
		manifest.fixMissingSubOptions();
		
		return manifest;
	}
	
	private static String nameFromZip(final File zipFile) {
		return zipFile.getName()
			.replaceAll("\\.(zip|rar|7z)", "")
			.replaceFirst("-\\d+-[^a-zA-Z]*-\\d+$", ""); // NexusMods: remove numbers
	}
	
	public List<SubOption> getSelectedOptions() {
		return options.stream()
			.filter(option -> option.selected)
			.map(option -> option.subOptions)
			.collect(ArrayList<SubOption>::new, ArrayList::addAll, ArrayList::addAll)
			.stream()
			.filter(subOption -> subOption.selected)
			.toList();
	}
	
	@Override
	public String toString() {
		return "Manifest{" + "version=" + version + ", guid=" + guid + ", name='" + name + '\'' +
			", description='" + description + '\'' + ", options=" + options + '}';
	}
}
