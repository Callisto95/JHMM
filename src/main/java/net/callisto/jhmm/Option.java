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
	List<SubOption> subOptions;
	
	@Override
	public String toString() {
		return String.format("%s: %s", this.name, this.description);
	}
}
