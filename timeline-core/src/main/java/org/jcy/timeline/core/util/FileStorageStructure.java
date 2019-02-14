package org.jcy.timeline.core.util;

import org.jcy.timeline.util.Assertion;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * File Storage Structure.
 */
public class FileStorageStructure {

	static final String STORAGE_FILE = "session.storage";
	static final String TIMELINE_DIRECTORY = ".timeline";
	private final File baseDirectory;

	/**
	 * Create a new FileStorageStructure linked with {@param baseDirectory}.
	 *
	 * @param baseDirectory the parent directory of the storage file in file system.
	 */
	public FileStorageStructure(File baseDirectory) {
		Assertion.check(baseDirectory != null, "BASE_DIRECTORY_MUST_NOT_BE_NULL");
		try {
			this.baseDirectory = baseDirectory.getCanonicalFile();
		} catch (IOException cause) {
			throw new IllegalArgumentException(cause);
		}
	}

	/**
	 * @return the baseDirectory.
	 */
	public File getTimelineDirectory() {
		return ensureTimelineDirectory(baseDirectory);
	}

	/**
	 * @return the actual storage file in baseDirectory.
	 */
	public File getStorageFile() {
		return ensureStorageFile(ensureTimelineDirectory(baseDirectory));
	}

	/**
	 * Create the directory if not exists.
	 *
	 * @param baseDirectory directory
	 */
	private File ensureTimelineDirectory(File baseDirectory) {
		File result = new File(baseDirectory.toString(), TIMELINE_DIRECTORY);
		try {
			Files.createDirectories(result.toPath());
		} catch (IOException cause) {
			throw new IllegalStateException(cause);
		}
		return result;
	}

	/**
	 * Create the storage file in {@param parent}.
	 *
	 * @param parent directory
	 */
	private File ensureStorageFile(File parent) {
		File result = new File(parent, STORAGE_FILE);
		if (!result.exists()) {
			try {
				result.createNewFile();
			} catch (IOException cause) {
				throw new IllegalStateException(cause);
			}
		}
		return result;
	}

}