package net.callisto.jhmm;

import java.util.*;

public record PatchFile(String basePatch, Optional<String> gpuPatch, Optional<String> streamPatch) {
	public static PatchFile wrapper(
		final String basePatch,
		final String gpuPatch,
		final String streamPatch
	) {
		return new PatchFile(basePatch, Optional.of(gpuPatch), Optional.of(streamPatch));
	}
}
