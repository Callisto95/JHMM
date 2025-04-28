package net.callisto.jhmm;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

public class Option {
	@JsonProperty("Name")
	String          name;
	@JsonProperty("Description")
	String          description;
	@JsonProperty("Include")
	List<String>    include;
	@JsonProperty("SubOptions")
	/**
	 * Guaranteed to be an array with length of at least 2
	 * First index will always be {@link SubOption#DISABLED}
	 */
	List<SubOption> subOptions = new ArrayList<>();
	@JsonIgnore
	boolean         selected   = false;
	
	@Override
	public String toString() {
		// return String.format("%s: %s (%b)", this.name, this.description, this.selected);
		return String.format("%s: %s", this.name, this.description);
	}
}
