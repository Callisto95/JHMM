package net.callisto.jhmm;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.*;

import java.io.*;
import java.nio.file.*;
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
	
	public static void unzip(final ZipFile is, final Path targetDir) throws IOException {
		final Path                      extractionDir = targetDir.toAbsolutePath();
		Iterator<? extends ZipEntry> entries       = is.entries().asIterator();
		
		while (entries.hasNext()) {
			final ZipEntry ze = entries.next();
			Path resolvedPath = extractionDir.resolve(ze.getName()).normalize();
			if (!resolvedPath.startsWith(extractionDir)) {
				// see: https://snyk.io/research/zip-slip-vulnerability
				throw new IllegalStateException("Entry with an illegal path: " + ze.getName());
			}
			if (ze.isDirectory()) {
				Files.createDirectories(resolvedPath);
			} else {
				Files.createDirectories(resolvedPath.getParent());
				Files.copy(is.getInputStream(ze), resolvedPath);
			}
		}
	}
	
	public static Manifest fromZip(final File zipFile, final Path extractPath) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES); // images
		
		Manifest manifest;
		
		try (final ZipFile zip = new ZipFile(zipFile)) {
			unzip(zip, extractPath);
			
			final File manifestFile = extractPath.resolve("manifest.json").toFile();
			
			if (!manifestFile.exists()) {
				return createManifestFromFile(zipFile);
			}
			
			manifest = mapper.readValue(manifestFile, Manifest.class);
		}
		
		manifest.fixMissingSubOptions();
		
		return manifest;
	}
	
	private void fixMissingSubOptions() {
		for (Option option : this.options) {
			if (option.subOptions.isEmpty()) {
				option.subOptions.add(SubOption.ENABLED);
			}
			
			option.subOptions.addFirst(SubOption.DISABLED);
		}
	}
	
	private static Manifest createManifestFromFile(final File zipFile) {
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
