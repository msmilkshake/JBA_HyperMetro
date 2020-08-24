package metro.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class JsonRead {
    
    protected static Gson gson = new Gson();
    
    public static JsonElement readFile(File f) {
        JsonElement root = null;
        try (JsonReader reader = new JsonReader(new FileReader(f))) {
            root = JsonParser.parseReader(reader);
        } catch (FileNotFoundException e) {
            System.out.println("Incorrect file");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return root;
    }
}
