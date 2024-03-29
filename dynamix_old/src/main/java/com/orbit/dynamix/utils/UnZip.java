package com.orbit.dynamix.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class UnZip {

	List<String> fileList;

	private static final String INPUT_ZIP_FILE = "C:\\zip\\zipFile.zip";

	private static final String OUTPUT_FOLDER = "C:\\zip\\unzip";

	public static void main(String[] args) {
		UnZip unZip = new UnZip();
		unZip.unZipIt(INPUT_ZIP_FILE, OUTPUT_FOLDER);
	}

	/**
	 * Unzip it
	 * 
	 * @param zipFile
	 *            input zip file
	 * @param output
	 *            zip file output folder
	 */
	public List<String> unZipIt(String zipFile, String outputFolder) {
		List<String> arrayFiles = new ArrayList<String>(0);
		byte[] buffer = new byte[1024];

		try {

			// create output directory is not exists
			File folder = new File(OUTPUT_FOLDER);
			if (!folder.exists()) {
				folder.mkdir();
			}

			// get the zip file content
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			// get the zipped file list entry
			ZipEntry ze = zis.getNextEntry();

			while (ze != null) {

				String fileName = ze.getName();
				File newFile = new File(outputFolder + File.separator + fileName);

				if (ze.isDirectory()) {
					newFile.mkdir();
				} else {

					System.out.println("file unzip : " + newFile.getAbsoluteFile());
					arrayFiles.add(newFile.getAbsolutePath());
					
					// create all non exists folders
					// else you will hit FileNotFoundException for compressed
					// folder
					new File(newFile.getParent()).mkdirs();

					FileOutputStream fos = new FileOutputStream(newFile);

					int len;
					while ((len = zis.read(buffer)) > 0) {
						fos.write(buffer, 0, len);
					}

					fos.close();
				}
				ze = zis.getNextEntry();
			}

			zis.closeEntry();
			zis.close();

			System.out.println("Done");

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return arrayFiles;
	}
}