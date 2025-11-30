/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flashcardlogic;
import java.util.HashMap;
import java.io.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.*;
import java.util.Map;
import java.util.ArrayList;


/**
 *
 * @author afoud
 */
public class ViewFlashcard {
    private String flashcard_set_view;
    private ArrayList<String> flashcard_question;
    private ArrayList<String> flashcard_answer;
    
    public ViewFlashcard(String flashcard_set_view) throws FileNotFoundException {
        this.flashcard_set_view = flashcard_set_view;
        System.out.println(flashcard_set_view);
        
        this.flashcard_question = new ArrayList<String>();
        this.flashcard_answer = new ArrayList<String>();
        
        
        InputStream  read_json = new FileInputStream("Flashcard Resource/flashcard.JSON");
        
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(read_json, "UTF-8"));
            
            JsonObject jobject = JsonParser.parseReader(reader).getAsJsonObject();
            System.out.println(jobject.getClass());
            
            JsonObject root_json = jobject.getAsJsonObject("flashcard");
            
           JsonObject set_here = root_json.getAsJsonObject(flashcard_set_view);
           System.out.println(set_here);
           
           
           for(Map.Entry<String, JsonElement> current : set_here.entrySet()) {
            
               String question = current.getKey();
               String answer = current.getValue().getAsString();
               flashcard_question.add(question);
               flashcard_answer.add(answer);
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
           
    }

    public ArrayList<String> get_flashcard_question() {
        return flashcard_question;
    }
    public ArrayList<String> get_flashcard_answer() {
        return flashcard_answer;
    }
}
