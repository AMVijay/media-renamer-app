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
import com.amvijay.media_renamer.service.MediaRenamerService;
import com.amvijay.media_renamer.service.TikaParserService;
import com.amvijay.media_renamer.service.UserInputService;
import com.amvijay.media_renamer.util.DateUtil;

/**
 * MediaNameFormatter - Class to rename the files as yyyyMMdd_HHmmss format by
 * when the picture/video taken.
 * 
 * @author Vijayaraaghavan Manoharan
 *
 */
public class App {

	/**
	 * Main method for project
	 * 
	 * @param args as String array.
	 */
	public static void main(String[] args) {

		try {

			UserInputService userInputService = new UserInputService();
			String inputFolderPath = userInputService.fetchFolderPathFromUser();

			File sourceRoot = new File(inputFolderPath);
			// Check whether it is valid source directory
			if (sourceRoot.isDirectory()) {
				MediaRenamerService mediaRenamerService = new MediaRenamerService();
				mediaRenamerService.renameFiles(sourceRoot);
			} else {
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

}
