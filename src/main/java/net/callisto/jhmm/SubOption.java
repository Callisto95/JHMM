package net.callisto.jhmm;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

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
	String       name;
	@JsonProperty("Description")
	String       description;
	@JsonProperty("Include")
	List<String> include;
	@JsonIgnore
	boolean      selected = false;
	
	@Override
	public String getDisplayName() {
		return String.format("%s: %s", this.name, this.description);
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s :: %s (enabled: %b)", this.name, this.description, this.include, this.selected);
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
	
	// public List<?> getFiles() {
	//
	// }
}
