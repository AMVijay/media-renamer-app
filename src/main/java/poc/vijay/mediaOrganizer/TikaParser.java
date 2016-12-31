package poc.vijay.mediaOrganizer;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.metadata.TikaCoreProperties;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class TikaParser {	
	
	private static final String DATE_FORMATTER = "yyyyMMdd_HHmmss";
	
	public String fetchCreationDate(String filePath) throws IOException, SAXException, TikaException{
		
		AutoDetectParser parser = new AutoDetectParser();
		BodyContentHandler handler = new BodyContentHandler();
		Metadata metadata = new Metadata();
				
		InputStream stream = new FileInputStream(filePath);
		parser.parse(stream, handler, metadata);
		stream.close();		
		Date mediaCreationDate = metadata.getDate(TikaCoreProperties.CREATED);
		
		String mediaCreationDateString = formatDate(mediaCreationDate);
		
		return mediaCreationDateString;
	}

	private String formatDate(Date creationDate) {
		String creationDateString = null;
		if(creationDate != null){
			SimpleDateFormat dateFormatter = new SimpleDateFormat(DATE_FORMATTER);
			creationDateString = dateFormatter.format(creationDate);
		}
		return creationDateString;
	}

}
