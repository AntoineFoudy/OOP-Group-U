package SideSubject;

import java.io.*;

/**
 * @author Shoaib
 * Backend class to read/write subject text files and return chapter content
 * Automatically creates a file if it does not exist
 */
public class sideSubjects {

    private static final String basePath = 
        "C:\\Users\\shoai\\OneDrive - National College of Ireland\\Semester One\\Object Oriented Programming\\OOP-Group-U\\EduApp\\src\\SideSubject\\ComputerScience\\";

    /**
     * Get chapter content as a string
     * @param chapterNumber Chapter number (1, 2, 3)
     * @return Content of the chapter or message if file missing
     */
    public static String getChapterContent(int chapterNumber) {
        String filePath = basePath + "Chapter" + chapterNumber + ".txt";

        File chapterFile = new File(filePath);

        // If file does not exist, create an empty file
        if (!chapterFile.exists()) {
            try {
                if (chapterFile.createNewFile()) {
                    System.out.println("Chapter" + chapterNumber + ".txt created successfully!");
                } else {
                    System.out.println("Failed to create Chapter" + chapterNumber + ".txt");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error creating chapter file!";
            }
        }

        // Read file content
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(chapterFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading chapter file!";
        }

        return content.toString();
    }

    /**
     * Write content to chapter file
     * @param chapterNumber Chapter number
     * @param content Text content to write
     */
    public static void writeChapterContent(int chapterNumber, String content) {
        String filePath = basePath + "Chapter" + chapterNumber + ".txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(content);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error writing chapter file!");
        }
    }
    
}
