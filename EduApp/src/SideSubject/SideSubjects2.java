package SideSubject;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Backend class for Chemistry subject
 * Reads chapter content from .txt files and returns as String
 */
public class SideSubjects2 {

    // Base path where the chapter .txt files will be stored
    private static final String basePath = "C:\\Users\\shoai\\OneDrive - National College of Ireland\\Semester One\\Object Oriented Programming\\OOP-Group-U\\EduApp\\src\\SideSubject\\Chemistry\\";

    /**
     * Get chapter content as String
     * @param chapterNumber 1, 2, or 3
     * @return Chapter content
     */
    public static String getChapterContent(int chapterNumber) {
        String filePath = basePath + "Chapter" + chapterNumber + ".txt";

        // If file does not exist, create empty file
        if (!Files.exists(Paths.get(filePath))) {
            try {
                Files.createDirectories(Paths.get(basePath));
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                    writer.write("This is Chapter " + chapterNumber + " content for Chemistry.\nYou can fill it with your content.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error creating chapter file!";
            }
        }

        // Read file content
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading chapter file!";
        }

        return content.toString();
    }
}
