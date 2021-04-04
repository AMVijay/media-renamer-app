package com.amvijay.media_renamer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

import com.amvijay.media_renamer.service.FileAttributesService;
import com.amvijay.media_renamer.service.TikaParserService;
import com.amvijay.media_renamer.util.DateUtil;

/**
 * MediaNameFormatter - Class to rename the files as yyyyMMdd_HHmmss format by
 * when the picture/video taken.
 * 
 * @author Vijayaraaghavan Manoharan
 *
 */
public class App {

	private static final String PATH_SEPARATOR = "//";
	
	private static final String HYPEN = "-";
	

	/**
	 * Main method for project
	 * @param args as String array.
	 */
	public static void main(String[] args) {

		try {

			if (args.length > 0 && args[0] != null) {

				File sourceRoot = new File(args[0]);

				if (sourceRoot.isDirectory()) {
					
					File destinationRoot = new File(sourceRoot.getAbsolutePath() + HYPEN + DateUtil.getInstance().formatDate(Calendar.getInstance().getTime()));
					destinationRoot.mkdir();
					
					File[] fileArray = sourceRoot.listFiles();
					if(fileArray != null && fileArray.length > 0) {
						int fileCount = 1;
						List<String> notModifiedFiles = new ArrayList<String>();
						for (File file : fileArray) {
							
							if (file.isFile()) {
								String creationDate = fetchCreationDate(file.getAbsolutePath());

								if (creationDate != null && !creationDate.isEmpty()) {
									String extension = getExtension(file.getName());
									File newFile = new File(destinationRoot + PATH_SEPARATOR + creationDate + extension);
									if (newFile.exists()) {
										Integer count = Integer.valueOf(0);
										newFile = fetchNextUniqueFileObject(destinationRoot.getPath(), creationDate, extension,
												count);
									}
									System.out.println(fileCount + ". Old File Name :: " + file.getName()
											+ " ;New File Name :: " + newFile.getName());
									// Copy file to new location with new file name
									Files.copy(file.toPath(), newFile.toPath(), StandardCopyOption.COPY_ATTRIBUTES);

								}
							}
							fileCount = fileCount + 1;
						}

						if (notModifiedFiles != null && !notModifiedFiles.isEmpty()) {
							for (String fileName : notModifiedFiles) {
								System.err.println(fileName + " is not modified");
							}
						}
					}
				}
			}

			else {
				System.out.println("There is no Argument with valid file path");
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TikaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Method to fetch best FileCreationDate.
	 * 
	 * @param absolutePath as String
	 * @return creationDate as String
	 * @throws IOException
	 * @throws SAXException
	 * @throws TikaException
	 */
	private static String fetchCreationDate(String absolutePath) throws IOException, SAXException, TikaException {
		String creationDate = null;
		if (absolutePath != null) {	
			String extension = getExtension(absolutePath);
			if(extension.equalsIgnoreCase(".mov")) {
				creationDate = FileAttributesService.getInstance().fetchLastModifiedDate(absolutePath);
			}
			else {
				creationDate = TikaParserService.getInstance().fetchCreationDate(absolutePath);
			}
			if (creationDate == null || creationDate.isEmpty()) {
				creationDate = FileAttributesService.getInstance().fetchCreationDate(absolutePath);
			}
		}
		return creationDate;
	}

	/**
	 * Method to get next Unique File Object.
	 * 
	 * @param path as String
	 * @param creationDate as String
	 * @param extension as String
	 * @param count as Integer
	 * @return newFile as File
	 */
	private static File fetchNextUniqueFileObject(String path, String creationDate, String extension, Integer count) {
		String newFileName = null;
		newFileName = path + PATH_SEPARATOR + creationDate + "_" + count + extension;
		File newFile = new File(newFileName);
		if (newFile.exists()) {
			count = count + 1;
			newFile = fetchNextUniqueFileObject(path, creationDate, extension, count);
		}
		return newFile;
	}

	/**
	 * Method to get extension for a file.
	 * 
	 * @param name as String
	 * @return extension as String
	 */
	private static String getExtension(String name) {
		String extension = null;
		int lastIndex = name.lastIndexOf(".");
		extension = name.substring(lastIndex);
		return extension;
	}

}
