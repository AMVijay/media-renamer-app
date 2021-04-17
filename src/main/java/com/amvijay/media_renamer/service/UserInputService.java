package com.amvijay.media_renamer.service;

import java.util.Scanner;

/**
 * Service class to read user input from command prompt.
 * 
 * @author Vijayaraaghavan Manoharan
 */
public class UserInputService {

    /**
     * Method to fetch the source folder path from user.
     * @return
     */
    public String fetchFolderPathFromUser() {
        String inputFolderPath = null;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please Enter the media folder path: ");
        inputFolderPath = scanner.nextLine();
        scanner.close();
        return inputFolderPath;
    }

}
