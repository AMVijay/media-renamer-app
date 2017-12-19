package poc.vijay.mediaOrganizer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class FileAttributesService {

	private static final String DATE_FORMATTER = "yyyyMMdd_HHmmss";

	public static FileAttributesService getInstance() {
		FileAttributesService object = new FileAttributesService();
		return object;
	}

	public String fetchCreationDate(String filePath) throws IOException {

		BasicFileAttributes attributes = Files.readAttributes(Paths.get(filePath), BasicFileAttributes.class);

		String mediaCreationDateString = null;

		if (attributes.creationTime().compareTo(attributes.lastModifiedTime()) < 0) {
			mediaCreationDateString = formatDate(new Date(attributes.creationTime().to(TimeUnit.MILLISECONDS)));
		} else {
			mediaCreationDateString = formatDate(new Date(attributes.lastModifiedTime().to(TimeUnit.MILLISECONDS)));
		}

		System.out.println("mediaCreationDateString :: " + mediaCreationDateString);

		return mediaCreationDateString;
	}

	private String formatDate(Date creationDate) {
		String creationDateString = null;
		if (creationDate != null) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATTER);
			creationDateString = dateFormatter.format(creationDate);
		}
		return creationDateString;
	}
}
