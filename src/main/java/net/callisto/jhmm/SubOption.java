package net.callisto.jhmm;

import com.fasterxml.jackson.annotation.*;

import java.util.*;

public class SubOption {
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
