package metro.io;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

public class JsonIO {
    
    protected static Gson gson = new Gson();
    
    public static HashMap<String, HashMap<Integer, String>> readFile(File f) {
        HashMap<String, HashMap<Integer, String>> jsonData = new HashMap<>();
        Type type =
                new TypeToken<HashMap<String, HashMap<Integer, String>>>(){}
                        .getType();
    
        try (JsonReader reader = new JsonReader(new FileReader(f))) {
            jsonData = gson.fromJson(reader, type);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonData;
    }
}
