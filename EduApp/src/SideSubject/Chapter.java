/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SideSubject;

/**
 *
 * @author shoaib
 * Class representing a single Chapter in a side subject.
 * Each chapter has a number, a title, and content.
 */
public class Chapter {
    private int chapterNumber;
    private String title;
    private String content;

    public Chapter(int chapterNumber, String title, String content) {
        this.chapterNumber = chapterNumber;
        this.title = title;
        this.content = content;
    }

    // Getters and setters
    public int getChapterNumber() {
        return chapterNumber;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
 

    public void setContent(String content) {
        this.content = content; 
    }
}
