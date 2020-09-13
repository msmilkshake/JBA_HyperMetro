package metro.logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import metro.util.DirectedGraph;
import metro.util.JsonRead;

import java.io.File;
import java.util.LinkedList;
import java.util.Map;

public class MetroBuilder {
    
    private JsonElement rawJson;
    private DirectedGraph<Station> graph;
    private Map<String, LinkedList<Station>> lines;
    
    private static final MetroBuilder INSTANCE = new MetroBuilder();
    
    private MetroBuilder() {
    }
    
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
        nullify();
    }
    
    private void buildGraph() {
        for (Map.Entry<String, JsonElement> line :
                rawJson.getAsJsonObject().entrySet()) {
            buildLine(line.getKey(), line.getValue());
        }
    }
    
    private void buildLine(String lineName, JsonElement data) {
        JsonArray lineData = data.getAsJsonArray();
        lines.putIfAbsent(lineName, new LinkedList<>());
        
        for (JsonElement stationElement : lineData) {
            JsonObject stationData = stationElement.getAsJsonObject();
            
            lines.get(lineName).add(buildStation(lineName, stationData));
        }
    }
    
    private Station buildStation(String lineName, JsonObject stationData) {
        String name = stationData.get("name").getAsString();
        JsonArray prev = stationData.getAsJsonArray("prev");
        JsonArray next = stationData.getAsJsonArray("next");
        JsonArray transfer = stationData.getAsJsonArray("transfer");
        double time = Double.NaN;
        if (next.size() > 0) {
            time = stationData.get("time").getAsDouble();
        }
        
        Station newStation = getOrCreate(lineName, name);
        newStation.setTime(time);
        graph.addNode(newStation);
        
        for (JsonElement prevElement : prev) {
            Station prevStation = getOrCreate(lineName, prevElement.getAsString());
            graph.addEdge(newStation, prevStation, prevStation.getTime());
        }
        
        for (JsonElement nextElement : next) {
            Station nextStation = getOrCreate(lineName, nextElement.getAsString());
            graph.addEdge(newStation, nextStation, time);
        }
        
        buildTransfer(newStation, transfer);
        return newStation;
    }
    
    private void buildTransfer(Station station, JsonArray transferData) {
        for (JsonElement transferElement : transferData) {
            JsonObject transferObject = transferElement.getAsJsonObject();
            String line = transferObject.get("line").getAsString();
            String name = transferObject.get("station").getAsString();
            Station transfer = getOrCreate(line, name);
            station.addTransfer(transfer);
            graph.addEdge(station, transfer, 5.0);
        }
    }
    
    private Station getOrCreate(String line, String name) {
        Station station = new Station(line, name);
        if (graph.getNode(station) != null) {
            station = graph.getNode(station);
        }
        return station;
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
