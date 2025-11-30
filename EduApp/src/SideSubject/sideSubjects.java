package SideSubject;

import java.io.*;
import java.util.ArrayList;

public class sideSubjects {

    private static final String basePath = "C:\\Users\\shoai\\OneDrive - National College of Ireland\\Semester One\\Object Oriented Programming\\OOP-Group-U\\EduApp\\src\\SideSubject\\ComputerScience\\";

    private ArrayList<Chapter> chapters;

    public sideSubjects() {
        chapters = new ArrayList<>();
    }

    // Load chapters from text files
    public void loadChapters() {
        for (int i = 1; i <= 3; i++) {
            String fileName = basePath + "Chapter" + i + ".txt";
            File file = new File(fileName);
            try {
                if (!file.exists()) {
                    file.createNewFile(); // create if not exist
                }
                BufferedReader br = new BufferedReader(new FileReader(file));
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\n");
                }
                br.close();
                chapters.add(new Chapter(i, "Chapter " + i, content.toString()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Get chapter by number
    public String getChapterContent(int chapterNumber) {
        for (Chapter ch : chapters) {
            if (ch.getChapterNumber() == chapterNumber) {
                return ch.getContent();
            }
        }
        return "Chapter not found!";
    }

    // Add new chapter content
    public void addChapterContent(int chapterNumber, String content) {
        for (Chapter ch : chapters) {
            if (ch.getChapterNumber() == chapterNumber) {
                ch.setContent(content);
                saveChapterToFile(ch);
                return;
            }
        }
        Chapter newChapter = new Chapter(chapterNumber, "Chapter " + chapterNumber, content);
        chapters.add(newChapter);
        saveChapterToFile(newChapter);
    }

    private void saveChapterToFile(Chapter ch) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(basePath + "Chapter" + ch.getChapterNumber() + ".txt"))) {
            bw.write(ch.getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
