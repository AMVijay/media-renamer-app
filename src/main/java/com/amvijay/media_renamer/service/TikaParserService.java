package com.amvijay.media_renamer.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import com.amvijay.media_renamer.util.DateUtil;


public class TikaParserService {

	public static TikaParserService getInstance() {
		TikaParserService object = new TikaParserService();
		return object;
	}

	public String fetchCreationDate(String filePath) throws IOException, SAXException, TikaException {

		AutoDetectParser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();

		InputStream stream = new FileInputStream(filePath);
		parser.parse(stream, handler, metadata);
		stream.close();
		
		Date mediaCreationDate = metadata.getDate(TikaCoreProperties.CREATED);

		String mediaCreationDateString = DateUtil.getInstance().formatDate(mediaCreationDate);

		return mediaCreationDateString;
	}

}
