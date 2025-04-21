package net.callisto.jhmm;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

public class SubOption {
	public static final SubOption DISABLED = new SubOption() {
		String       name        = "disabled";
		String       description = "";
		List<String> include     = List.of();
		
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
	
	@Override
	public String toString() {
		return String.format("%s: %s", this.name, this.description);
	}
}
