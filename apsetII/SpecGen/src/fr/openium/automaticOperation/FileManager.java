package fr.openium.automaticOperation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import org.apache.commons.io.FileUtils;

public class FileManager {
	/**
	 * 
	 * @param sourceFile
	 * @param destFile
	 * @return - true if copy is succed
	 * @return -false if copy failed
	 * @throws IOException
	 */

	public static boolean copy(File sourceFile, File destFile)
			throws IOException {
		if (!sourceFile.exists()) {
			return false;
		}
		if (!destFile.exists()) {
			destFile.createNewFile();
		}
		FileChannel source = null;
		FileChannel destination = null;
		source = new FileInputStream(sourceFile).getChannel();
		destination = new FileOutputStream(destFile).getChannel();
		if (destination != null && source != null) {
			destination.transferFrom(source, 0, source.size());
		}
		if (source != null) {
			source.close();
		}
		if (destination != null) {
			destination.close();
		}
		return true;
	}

	/***
	 * copying contents of one file to another
	 * 
	 * @param sourceFile
	 * @param destFile
	 * @return
	 */
	public static boolean copyFiletoFile(File sourceFile, File destFile) {
		try {
			FileUtils.copyFile(sourceFile, destFile);
			return true;
		} catch (IOException e) {
			return false;
		}

	}

	/***
	 * copying file from one directory to another directory.
	 * 
	 * @param sourceFile
	 * @param destFile
	 * @return
	 */
	public static boolean copyFiletoDirectory(File sourceFile, File destFile) {
		try {
			FileUtils.copyFileToDirectory(sourceFile, destFile);
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	/***
	 * copying directory from one directory to another directory.
	 * 
	 * @param sourceDirectory
	 * @param destFile
	 * @return
	 */
	public static boolean copyDirectorytoDirectory(File sourceDirectory,
			File destFile) {
		try {
			FileUtils.copyDirectory(sourceDirectory, destFile);
			return true;
		} catch (IOException e) {
			return false;
		}

	}
}
