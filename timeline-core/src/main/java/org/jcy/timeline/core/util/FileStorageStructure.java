package org.jcy.timeline.core.util;

import java.io.File;

public class FileStorageStructure {

	static final String STORAGE_FILE = "session.storage";
	static final String TIMELINE_DIRECTORY = ".timeline";
	private File baseDirectory;

	/**
	 *
	 * @param baseDirectory
	 */
	public FileStorageStructure(File baseDirectory) {
		// TODO - implement FileStorageStructure.FileStorageStructure
		throw new UnsupportedOperationException();
	}

	public File getTimelineDirectory() {
		// TODO - implement FileStorageStructure.getTimelineDirectory
		throw new UnsupportedOperationException();
	}

	public File getStorageFile() {
		// TODO - implement FileStorageStructure.getStorageFile
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param baseDirectory
	 */
	private File ensureTimelineDirectory(File baseDirectory) {
		// TODO - implement FileStorageStructure.ensureTimelineDirectory
		throw new UnsupportedOperationException();
	}

	/**
	 *
	 * @param parent
	 */
	private File ensureStorageFile(File parent) {
		// TODO - implement FileStorageStructure.ensureStorageFile
		throw new UnsupportedOperationException();
	}

}