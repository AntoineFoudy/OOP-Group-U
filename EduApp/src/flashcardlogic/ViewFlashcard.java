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
    
    public ViewFlashcard(String flashcard_set_view) {
        this.flashcard_set_view = flashcard_set_view;
        System.out.println(flashcard_set_view);
        
        // HashMap will be how I store the question and answers from the json file
        // Question == Key, Answer == Value
        //HashMap<String, String> flashcard_list = new HashMap<String, String>();
        ArrayList<String> flashcard_question = new ArrayList<String>();
        ArrayList<String> flashcard_answer = new ArrayList<String>();
        
        
        InputStream read_json = getClass().getResourceAsStream("flashcard.JSON");
        
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(read_json, "UTF-8"));
            
            JsonObject jobject = JsonParser.parseReader(reader).getAsJsonObject();
            System.out.println(jobject.getClass());
            
            JsonObject root_json = jobject.getAsJsonObject("flashcard");
            
           JsonObject set_here = root_json.getAsJsonObject(flashcard_set_view);
           System.out.println(set_here);
           
           
           for(Map.Entry<String, JsonElement> current : set_here.entrySet()) {
            //JsonObject current = set_here(i).getAsJsonObject();
            
               String question = current.getKey();
               String answer = current.getValue().getAsString();
//               System.out.println(question);
//               System.out.println(answer);
                flashcard_question.add(question);
                flashcard_answer.add(answer);
            }
        }
        catch (IOException e){
            System.out.println(e);
        }
           
    }
    
}
