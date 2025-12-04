/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flashcardlogic;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author afoud
 */
public class CreateFlashcard {
    private ArrayList<String> set_question = new ArrayList<String>();
    private ArrayList<String> set_answer = new ArrayList<String>();
    private String flashcard_set_save;
    private Map<String, String> flashcard_to_hashmap = new HashMap<>();
    
    public CreateFlashcard(ArrayList<String> set_question, ArrayList<String> set_answer, String flashcard_set_save) throws FileNotFoundException {
        this.set_question = set_question;
        this.set_answer = set_answer;
        this.flashcard_set_save = flashcard_set_save;
        
        // Goes through each K, V and adds it to a hashmap
        for (int i = 0; i < set_question.size(); i++) {
            this.flashcard_to_hashmap.put(set_question.get(i), set_answer.get(i));
            System.out.println("here is hasmap" + flashcard_to_hashmap);
        }
        
        LoadJSONtoMemory();
    }
    
    // Loades the json file and passes the hasmap to the correct location
    public void LoadJSONtoMemory () throws FileNotFoundException {
        InputStream  read_json = new FileInputStream("Flashcard Resource/flashcard.JSON");
        
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(read_json, "UTF-8"));
            
            JsonObject jobject = JsonParser.parseReader(reader).getAsJsonObject();
            
            JsonObject root_json = jobject.getAsJsonObject("flashcard");
            System.out.println(flashcard_set_save);
            
           JsonObject save_set_here = root_json.getAsJsonObject(flashcard_set_save);
           
           WriteHashmapToJSON(save_set_here, jobject);
        }
        catch (IOException e){
            System.out.println(e);
        } 
    }
    
    // Over writes the  updated json file
    public void WriteHashmapToJSON (JsonObject save_set_here, JsonObject jobject) {
        for(Map.Entry<String, String> current : flashcard_to_hashmap.entrySet()) {
            save_set_here.addProperty(current.getKey(), current.getValue());
            System.out.println(save_set_here);    
        }
        System.out.println(jobject);
        try {
            FileOutputStream fos = new FileOutputStream("Flashcard Resource/flashcard.JSON", false);
            
            byte[] flashcardBytes = jobject.toString().getBytes();
            
            fos.write(flashcardBytes);
            System.out.println("We got here");
        }
        catch (IOException e){
            System.out.println(e);
        }
        
        
    }
}
