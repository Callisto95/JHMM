package net.callisto.jhmm;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import java.util.zip.*;

public class UnZipper implements AutoCloseable {
	protected final ZipFile zipFile;
	protected final Path    extractionPath;
	
	public UnZipper(final File zipPath, final Path extractionPath) throws IOException {
		this.zipFile        = new ZipFile(zipPath);
		this.extractionPath = extractionPath;
		
		unzip();
	}
	
	public void unzip() throws IOException {
		Iterator<? extends ZipEntry> entries = zipFile.entries().asIterator();
		
		while (entries.hasNext()) {
			final ZipEntry entry        = entries.next();
			Path           resolvedPath = extractionPath.resolve(entry.getName()).normalize();
			
			if (!resolvedPath.startsWith(extractionPath)) {
				// see: https://snyk.io/research/zip-slip-vulnerability
				throw new IllegalStateException("Entry with an illegal path: " + entry.getName());
			}
			
			if (entry.isDirectory()) {
				Files.createDirectories(resolvedPath);
			} else {
				Files.createDirectories(resolvedPath.getParent());
				Files.copy(zipFile.getInputStream(entry), resolvedPath);
			}
		}
	}
	
	private void deleteDirContent(final Path dir) throws IOException {
		try (Stream<Path> paths = Files.walk(dir)) {
			paths.forEach(path -> {
				if (path == dir) {
					return;
				}
				
				try {
					if (Files.isDirectory(path)) {
						deleteDirContent(path);
					}
					Files.delete(path);
				} catch (IOException exc) {
					throw new IllegalStateException(exc);
				}
			});
		}
	}
	
	public void delete() throws IOException {
		deleteDirContent(extractionPath);
		Files.delete(extractionPath);
	}
	
	@Override
	public void close() throws IOException {
		delete();
	}
}
