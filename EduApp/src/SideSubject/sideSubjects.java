/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SideSubject;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.FileReader;
import java.io.IOException;

/**
 *  @author Shoaib
 * Backend class to read subject JSON files and return chapter content
 */
public class sideSubjects {

    private static final String basePath = "C:\\Users\\shoai\\OneDrive - National College of Ireland\\Semester One\\Object Oriented Programming\\OOP-Group-U\\EduApp\\src\\SideSubject\\sideSubjectJson\\";

    /**
     * Get chapter content for a given subject and chapter
     *
     * @param subject Name of the subject file without extension (e.g., "ComputerScience")
     * @param chapter Chapter name ("Chapter1", "Chapter2", "Chapter3")
     * @return Content as String
     */
    public static String getChapterContent(String subject, String chapter) {
        String filePath = basePath + subject + ".json";
        Gson gson = new Gson();

        try (FileReader reader = new FileReader(filePath)) {
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            if (jsonObject.has(chapter)) {
                return jsonObject.get(chapter).getAsString();
            } else {
                return "Chapter not found!";
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error reading JSON file!";
        }
    }
}
