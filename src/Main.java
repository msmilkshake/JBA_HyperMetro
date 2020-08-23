import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import metro.logic.StationNode;
import metro.ui.TextUI;
import metro.util.JsonRead;


import java.io.File;
import java.lang.reflect.Type;
import java.util.*;

public class Main {
    
    public static void main(String[] args) {
        new TextUI(args, new Scanner(System.in)).start();
    }
    
}

class Test {
    
    public static void main(String[] args) {
        
        JsonElement rootEl = JsonRead.readFile(new File("t3.json"));
        JsonObject rootOb = rootEl.getAsJsonObject();
        Map<String, JsonElement> lines = new HashMap<>();
        for (Map.Entry<String, JsonElement> map : rootOb.entrySet()) {
            lines.put(map.getKey(), map.getValue());
        }
        HashMap<String, LinkedList<StationNode>> linesMap = new HashMap<>();
    
        List<Map<Integer, JsonElement>> linesStations = new ArrayList<>();
        for (String lineName : lines.keySet()) {
            Map<Integer, JsonElement> line = new HashMap<>();
            for (Map.Entry<String, JsonElement> map
                    : lines.get(lineName).getAsJsonObject().entrySet()) {
                line.put(Integer.valueOf(map.getKey()), map.getValue());
            }
            linesStations.add(line);
    
            for (Map<Integer, JsonElement> stations : linesStations) {
                LinkedList<StationNode> stationList = new LinkedList<>();
                for (int i = 1; i <= stations.size(); ++i) {
                    JsonObject jsonStation = stations.get(i).getAsJsonObject();
                    String name = jsonStation.get("name").getAsString();
                    JsonArray jsonTransfer = null;
                    if (jsonStation.get("transfer").isJsonArray()) {
                        jsonTransfer = jsonStation.get("transfer").getAsJsonArray();
                    }
                    Type t = new TypeToken<ArrayList<JsonObject>>() {}.getClass();
                    List<JsonObject> transfer = new Gson().fromJson(jsonTransfer,t);
                    StationNode node = new StationNode(name);
                    if (jsonTransfer != null) {
                        for (JsonElement object : jsonTransfer.getAsJsonArray()) {
                            node.addTransferLine(
                                    ((JsonObject) object).get("line").getAsString(),
                                    ((JsonObject) object).get("station").getAsString());
                        }
                    }
                    stationList.addLast(node);
                }
                stationList.addLast(new StationNode("depot"));
                linesMap.put(lineName, stationList);
                
            }
        }
    }
}