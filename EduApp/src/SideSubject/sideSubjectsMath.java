package SideSubject;

/**
 * @author Shoaib
 * Backend class to read/write subject text files and return chapter content
 * Automatically creates a file if it does not exist
 */
import java.io.*;

public class sideSubjectsMath {

    private static final String basePath = "C:\\Users\\shoai\\OneDrive - National College of Ireland\\Semester One\\Object Oriented Programming\\OOP-Group-U\\EduApp\\src\\SideSubject\\Mathematics\\";

    /**
     * Reads chapter content from a text file.
     * @param chapterNumber 1, 2, or 3
     * @return Content as String
     */
    public static String getChapterContent(int chapterNumber) {
        String filePath = basePath + "Chapter" + chapterNumber + ".txt";
        File file = new File(filePath);

        // Create the file if it doesn't exist
        if (!file.exists()) {
            try {
                file.createNewFile();
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write("This is Chapter " + chapterNumber + " content. Fill it with Mathematics notes.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Error creating chapter file!";
            }
        }

        // Read content from file
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading chapter file!";
        }

        return content.toString();
    }
}
