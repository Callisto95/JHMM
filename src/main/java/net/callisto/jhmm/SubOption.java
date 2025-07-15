package net.callisto.jhmm;

import com.fasterxml.jackson.annotation.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class SubOption implements DisplayAble {
	/**
	 * SubOption used to indicate a disabled option
	 * Should never be contained within an Options' suboption list.
	 */
	public static final SubOption DISABLED = new SubOption() {
		@Override
		public String getDisplayName() {
			return name;
		}
	};
	
	/**
	 * SubOption used to indicate an enabled option
	 * Should never be contained within an Options' suboption list.
	 */
	public static final SubOption ENABLED = new SubOption() {
		@Override
		public String getDisplayName() {
			return name;
		}
	};
	static {
		DISABLED.name        = "disabled";
		DISABLED.description = "";
		DISABLED.include     = List.of();
		
		ENABLED.name        = "enabled";
		ENABLED.description = "";
		ENABLED.include     = List.of(".");
	}
	
	@JsonProperty("Name")
	public String       name;
	@JsonProperty("Description")
	public String       description;
	@JsonProperty("Include")
	public List<String> include;
	@JsonIgnore
	public boolean      selected = false;
	
	@Override
	public String getDisplayName() {
		return String.format("%s: %s", this.name, this.description);
	}
	
	@Override
	public String toString() {
		return String.format(
			"%s: %s :: %s (enabled: %b)",
			this.name,
			this.description,
			this.include,
			this.selected
		);
	}
	
	@Override
	public boolean equals(final Object o) {
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		final SubOption subOption = (SubOption) o;
		return Objects.equals(name, subOption.name) && Objects.equals(
			description,
			subOption.description
		);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(name, description);
	}
	
	public static List<File> getFilesOfDirectory(final Path includePath) {
		try (Stream<Path> paths = Files.walk(includePath)) {
			return paths.filter(Files::isRegularFile).map(Path::toFile).toList();
		} catch (IOException exc) {
			throw new IllegalStateException(exc);
		}
	}
	
	public List<File> getFiles(final Path extractedPath) {
		return this.include
			.stream()
			.map(extractedPath::resolve)
			.map(SubOption::getFilesOfDirectory)
			.collect(ArrayList<File>::new, ArrayList::addAll, ArrayList::addAll);
	}
}
