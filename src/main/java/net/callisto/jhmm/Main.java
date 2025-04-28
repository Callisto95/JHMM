package net.callisto.jhmm;

import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		final File manifestFile = new File(args[0]);
		
		final Manifest manifest = Manifest.fromZip(manifestFile);
		
		// System.out.println(manifest);
		
		new TerminalUI().showUI(manifest);
		
		System.out.println(manifest);
	}
}
