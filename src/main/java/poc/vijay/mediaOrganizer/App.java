package poc.vijay.mediaOrganizer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

/**
 * MediaNameFormatter - Class to rename the files as yyyyMMdd_HHmmss format by when the picture/video taken.
 * @author Vijay 
 *
 */
public class App {

	private static final String PATH_SEPARATOR = "//";

	public static void main(String[] args) {

		TikaParser tikaParser = new TikaParser();
		try {

			File root = new File("C:\\Vijay\\media");

			if (root.isDirectory()) {
				File[] fileArray = root.listFiles();
				if (fileArray != null && fileArray.length > 0) {
					int fileCount = 1;
					List<String> notModifiedFiles = new ArrayList<String>();
					for (File file : fileArray) {
						if (file.isFile()) {
							String creationDate = tikaParser.fetchCreationDate(file.getAbsolutePath());
							if (creationDate != null && !creationDate.isEmpty()) {
								String extension = getExtension(file.getName());
								File newFile = new File(root + PATH_SEPARATOR + creationDate + extension);
								if (newFile.exists()) {
									Integer count = Integer.valueOf(0);
									newFile = fetchNextUniqueFileObject(root.getPath(), creationDate, extension, count);
								}
								System.out.println(fileCount + ". Old File Name :: " + file.getName()
										+ " ;New File Name :: " + newFile.getName());
								boolean success = file.renameTo(newFile);
								if (!success) {
									notModifiedFiles.add(file.getName());
								}

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
	 * Method to get next Unique File Object. 
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
