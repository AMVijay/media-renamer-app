package com.amvijay.media_renamer.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	private static final String DATE_FORMATTER = "yyyyMMdd_HHmmss";
	
	private DateUtil() {
		// Not allowed instantiate directly
	}
	
	public static DateUtil getInstance() {
		DateUtil object = new DateUtil();
		return object;
	}

	public String formatDate(Date creationDate) {
		String creationDateString = null;
		if (creationDate != null) {
			SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATTER);
			creationDateString = dateFormatter.format(creationDate);
		}
		return creationDateString;
	}
	
	public String formatDate(long timeinmilliseconds) {
		String creationDateString = null;
		SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATTER);
		creationDateString = dateFormatter.format(timeinmilliseconds);
		return creationDateString;
	}
	
}
