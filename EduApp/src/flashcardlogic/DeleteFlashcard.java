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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author afoud
 */
public class DeleteFlashcard {
    private ArrayList<String> delete_set;
    
    public DeleteFlashcard(ArrayList<String> delete_set) throws FileNotFoundException {
        this.delete_set = delete_set;
        
        // Passes each set one at a time
        for (int i = 0; i < delete_set.size(); i++) {
            String this_set = delete_set.get(i);
            delete_set(this_set);
        }
    }
    
    public void delete_set(String this_set) throws FileNotFoundException {
        InputStream  read_json = new FileInputStream("Flashcard Resource/flashcard.JSON");
        
        // Goes to the set location
        try {
            JsonReader reader = new JsonReader(new InputStreamReader(read_json, "UTF-8"));
            
            JsonObject jobject = JsonParser.parseReader(reader).getAsJsonObject();
            System.out.println(jobject.getClass());
            
            JsonObject root_json = jobject.getAsJsonObject("flashcard");
           
            // Removes the set entirly before adding it back in
            root_json.remove(this_set);
            root_json.add(this_set, new JsonObject());
           
            update_set(jobject);
        }
        catch (IOException e){
            System.out.println(e);
        }
    }
    
    // Writes the updated version of json file
    public void update_set(JsonObject jobject) {
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
