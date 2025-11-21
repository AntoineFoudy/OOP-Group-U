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


/**
 *
 * @author afoud
 */
public class ViewFlashcard {
    private String flashcard_set_view;
    
    public ViewFlashcard(String flashcard_set_view) {
        this.flashcard_set_view = flashcard_set_view;
        
        // HashMap will be how I store the question and answers from the json file
        // Question == Key, Answer == Value
        HashMap<String, String> flashcard_list = new HashMap<String, String>();
        
        InputStream read_json = getClass().getResourceAsStream("flashcard.JSON");
        
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(read_json, "UTF-8"));
            
            JsonObject jobject = JsonParser.parseReader(reader).getAsJsonObject();
            
            JsonObject set_here = jobject.getAsJsonObject(flashcard_set_view);
            
            for(Map.Entry<String, JsonElement> jelement : set_here.entrySet()) {
                String question = jelement.getKey();
                String answer = jelement.getValue().getAsString();
                flashcard_list.put(question, answer);
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
           
    }
    
}
