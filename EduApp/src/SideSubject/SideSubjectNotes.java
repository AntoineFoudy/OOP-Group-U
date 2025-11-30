/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SideSubject;

import java.io.*;
import java.util.ArrayList;

/**
 * @author Shoaib
 * SideSubjectNotes Handle CRUD for the SideNotes 
 * Notes are stored in a txt file: "notes.txt".
 */
public class SideSubjectNotes {

    private final String filePath = "notes.txt"; // store notes in txt file
    private ArrayList<Note> notes;

    // Note object to hold title and content
    public static class Note {
        String title;
        String content;

        public Note(String title, String content) {
            this.title = title;
            this.content = content;
        }
    }

    // Constructor loads existing notes
    public SideSubjectNotes() {
        notes = new ArrayList<>();
        loadNotes();
    }

    // Load notes from file into ArrayList
    private void loadNotes() {
        File file = new File(filePath);
        if (!file.exists()) {
            try { file.createNewFile(); } catch (IOException e) { e.printStackTrace(); }
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            notes.clear();
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("::"); // title::content
                if (parts.length == 2) {
                    notes.add(new Note(parts[0], parts[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Save notes back to file
    private void saveNotes() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Note note : notes) {
                bw.write(note.title + "::" + note.content);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Add a new note
    public void addNote(String title, String content) {
        notes.add(new Note(title, content));
        saveNotes();
    }

    // Search note by title
    public Note searchNote(String title) {
        for (Note note : notes) {
            if (note.title.equalsIgnoreCase(title)) {
                return note;
            }
        }
        return null; // not found
    }

    // Delete note by title
    public boolean deleteNote(String title) {
        for (int i = 0; i < notes.size(); i++) {
            if (notes.get(i).title.equalsIgnoreCase(title)) {
                notes.remove(i);
                saveNotes();
                return true;
            }
        }
        return false; // not found
    }

    // Get all notes
    public ArrayList<Note> getAllNotes() {
        return new ArrayList<>(notes);
    }
}
