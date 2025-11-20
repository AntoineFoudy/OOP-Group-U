// For read and parse https://stackoverflow.com/questions/10926353/how-to-read-json-file-into-java-with-simple-json-library

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package flashcardlogic;
import java.util.HashMap;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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
        

        JSONParser parser = new JSONParser();
        
        try {
            Object obj = parser.parse(new FileReader("flashcard.JSON"));
            
            JSONObject jsonObject = ( JSONObject ) obj;
            
            JSONObject flashcards = ( JSONObject ) jsonObject.get("flashcard");
            
            JSONObject flashcard_this_set_view = ( JSONObject ) flashcards.get(flashcard_set_view);
            
            // Add in the data for passing all the questions and answer to the hasmap
            
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
}
