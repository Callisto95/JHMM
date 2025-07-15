package net.callisto.jhmm;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

public class Option implements DisplayAble {
	@JsonProperty("Name")
	public String          name;
	@JsonProperty("Description")
	public String          description;
	@JsonProperty("Include")
	public List<String>    include;
	@JsonProperty("SubOptions")
	/**
	 * Guaranteed to be an array with length of at least 2
	 * First index will always be {@link SubOption#DISABLED}
	 */
	public List<SubOption> subOptions = new ArrayList<>();
	@JsonIgnore
	public boolean         selected   = false;
	
	@Override
	public String getDisplayName() {
		return String.format("%s: %s", this.name, this.description);
	}
	
	@Override
	public String toString() {
		return String.format("%s: %s :: %s (enabled: %b)", this.name, this.description, this.include, this.selected);
	}
}
