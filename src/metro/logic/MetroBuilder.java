package metro.logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import metro.util.DirectedGraph;
import metro.util.JsonRead;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MetroBuilder {
    
    private JsonElement rawJson;
    private DirectedGraph<Station> graph;
    private Map<String, LinkedList<Station>> lines;
    
    private static final MetroBuilder INSTANCE = new MetroBuilder();
    
    private MetroBuilder() {}
    
    public static MetroBuilder builder() {
        INSTANCE.nullify();
        return INSTANCE;
    }
    
    private void validateFields() {
        if (rawJson == null || graph == null || lines == null) {
            throw new IllegalStateException("build() requires chaining of" +
                    "withFile(), withGraph() and withMap() with non null values.");
        }
    }
    
    private void nullify() {
        rawJson = null;
        graph = null;
        lines = null;
    }
    
    public void buildNetwork() {
        validateFields();
        buildGraph();
        buildMap();
        nullify();
    }
    
    private void buildGraph() {
        for (Map.Entry<String, JsonElement> rawLine :
                rawJson.getAsJsonObject().entrySet()) {
            Map<Integer, JsonElement> unordered = new HashMap<>();
            for (Map.Entry<String, JsonElement> rawStation :
                    rawLine.getValue().getAsJsonObject().entrySet()) {
                unordered.put(Integer.valueOf(rawStation.getKey()),
                        rawStation.getValue());
            }
            Station previous = null;
            for (int i = 1; i <= unordered.size(); ++i) {
            
                String line = rawLine.getKey();
                String name = ((JsonObject) unordered.get(i))
                        .get("name").getAsString();
                Station station = new Station(line, name);
                
                if (graph.getNode(station) != null) {
                    station = graph.getNode(station);
                }
                if (previous == null) {
                    graph.addNode(station);
                } else {
                    graph.addEdge(previous, station);
                }
                if (lines.putIfAbsent(line, new LinkedList<>()) == null) {
                    lines.get(line).add(new Station(line, "depot"));
                }
                lines.get(line).add(station);
                previous = station;
            
                Map<Station, Station> transfers =
                        buildTransfers(((JsonObject) unordered.get(i))
                                .get("transfer"));
                for (Station transfer : transfers.keySet()) {
                    if (graph.getNode(transfer) != null) {
                        transfer = graph.getNode(transfer);
                    }
                    station.addTransfer(transfer);
                    graph.addEdge(station, transfer);
                }
            
            }
        }
    }
    
    private void buildMap() {
    
    }
    
    private Map<Station, Station> buildTransfers(JsonElement rawTransfer) {
        Map<Station, Station> transfers = new HashMap<>(5, 1.0f);
        if (rawTransfer.isJsonArray()) {
            if (((JsonArray) rawTransfer).size() == 0) {
                return transfers;
            }
        }
        if (rawTransfer.isJsonObject()) {
            assert rawTransfer instanceof JsonObject;
            Station station = new Station(
                    ((JsonObject) rawTransfer).get("line").getAsString(),
                    ((JsonObject) rawTransfer).get("station").getAsString());
            transfers.putIfAbsent(station, station);
        } else if (rawTransfer.isJsonArray()) {
            for (JsonElement object : (JsonArray) rawTransfer) {
                assert object instanceof JsonObject;
                Station station = new Station(
                        ((JsonObject) object).get("line").getAsString(),
                        ((JsonObject) object).get("station").getAsString());
                transfers.putIfAbsent(station, station);
            }
        }
        return transfers;
    }
    
    public MetroBuilder withFile(File file) {
        rawJson = JsonRead.readFile(file);
        return this;
    }
    
    public MetroBuilder withGraph(DirectedGraph<Station> graph) {
        this.graph = graph;
        return this;
    }
    
    public MetroBuilder withMap(Map<String, LinkedList<Station>> map) {
        lines = map;
        return this;
    }
}
