package net.callisto.jhmm;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

public class SubOption {
	/**
	 * SubOption used to indicate a disabled option
	 * Should never be contained within an Options' suboption list.
	 */
	public static final SubOption DISABLED = new SubOption() {
		final String       name        = "disabled";
		final String       description = "";
		final List<String> include     = List.of();
		
		@Override
		public String toString() {
			return this.name;
		}
	};
	
	/**
	 * SubOption used to indicate a enabled option
	 * Should never be contained within an Options' suboption list.
	 */
	public static final SubOption ENABLED = new SubOption() {
		final String       name        = "enabled";
		final String       description = "";
		final List<String> include     = List.of();
		
		@Override
		public String toString() {
			return this.name;
		}
	};
	
	@JsonProperty("Name")
	String       name;
	@JsonProperty("Description")
	String       description;
	@JsonProperty("Include")
	List<String> include;
	@JsonIgnore
	boolean      selected = false;
	
	@Override
	public String toString() {
		// return String.format("%s: %s (%b)", this.name, this.description, this.selected);
		return String.format("%s: %s", this.name, this.description);
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
}
