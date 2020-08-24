package metro.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import metro.logic.StationNode;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MetroUtil {
    
    public static HashMap<String, LinkedList<StationNode>> rawNetworkBuilder(
            JsonElement rawData) {
        HashMap<String, LinkedList<StationNode>> buildNetwork = new HashMap<>();
        
        JsonObject rawNetwork = rawData.getAsJsonObject();
        for (Map.Entry<String, JsonElement> entry : rawNetwork.entrySet()) {
            buildNetwork.put(
                    entry.getKey(),
                    rawLineBuilder(entry.getValue().getAsJsonObject()));
        }
        
        return buildNetwork;
    }
    
    private static LinkedList<StationNode> rawLineBuilder(JsonObject rawLine) {
        LinkedList<StationNode> buildLine = new LinkedList<>();
        
        for (int i = 1; i <= rawLine.size(); ++i) {
            buildLine.addLast(rawStationBuilder(
                    rawLine.getAsJsonObject(String.valueOf(i))));
        }
        buildLine.addLast(new StationNode("depot"));
        
        return buildLine;
    }
    
    private static StationNode rawStationBuilder(JsonObject rawStation) {
        String name = rawStation.get("name").getAsString();
        StationNode station = new StationNode(name);
        
        JsonArray jsonTransfer = null;
        if (rawStation.get("transfer").isJsonArray()) {
            jsonTransfer = rawStation.get("transfer").getAsJsonArray();
        }
        
        if (jsonTransfer != null) {
            for (JsonElement object : jsonTransfer.getAsJsonArray()) {
                station.addTransferLine(
                        ((JsonObject) object).get("line").getAsString(),
                        ((JsonObject) object).get("station").getAsString());
            }
        }
        
        return station;
    }
}
