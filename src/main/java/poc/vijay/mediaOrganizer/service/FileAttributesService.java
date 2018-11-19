package poc.vijay.mediaOrganizer.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import poc.vijay.mediaOrganizer.util.DateUtil;

public class FileAttributesService {

	public static FileAttributesService getInstance() {
		FileAttributesService object = new FileAttributesService();
		return object;
	}

	public String fetchCreationDate(String filePath) throws IOException {

		BasicFileAttributes attributes = Files.readAttributes(Paths.get(filePath), BasicFileAttributes.class);

		String mediaCreationDateString = null;

		if (attributes.creationTime().compareTo(attributes.lastModifiedTime()) < 0) {
			mediaCreationDateString = DateUtil.getInstance().formatDate(new Date(attributes.creationTime().to(TimeUnit.MILLISECONDS)));
		} else {
			mediaCreationDateString = DateUtil.getInstance().formatDate(new Date(attributes.lastModifiedTime().to(TimeUnit.MILLISECONDS)));
		}

		System.out.println("mediaCreationDateString :: " + mediaCreationDateString);

		return mediaCreationDateString;
	}

	public String fetchLastModifiedDate(String absolutePath) {
		long modifiedDate = new File(absolutePath).lastModified();
		String lastModifiedDate = DateUtil.getInstance().formatDate(modifiedDate);
		return lastModifiedDate;
	}
}
