package metro.logic;

import com.google.gson.JsonElement;
import metro.util.JsonRead;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

public class MetroNetwork {
    
    private HashMap<String, LinkedList<StationNode>> metroNetwork;
    
    public MetroNetwork(File json) {
        metroNetwork = new HashMap<>();
        processLines(JsonRead.readFile(json));
    }
    
    private void processLines(JsonElement rawData) {
        HashMap<String, LinkedList<StationNode>> map = new HashMap<>();
        //for (String lineName : lines.keySet()) {
        //    LinkedList<StationNode> line = new LinkedList<>();
        //    metroNetwork.putIfAbsent(lineName, line);
        //
        //    HashMap<Integer, StationNode> ref = lines.get(lineName);
        //    for (int i = 1; i <= ref.size(); ++i) {
        //        line.addLast(ref.get(i));
        //    }
        //    line.addLast(new StationNode("depot"));
        //}
    }
    
    public void appendStation(String lineName, String newStation) {
        LinkedList<StationNode> line = metroNetwork.get(lineName);
        if (line == null) {
            System.out.println("Not an existing line name.");
            return;
        }
        StationNode tailTmp = line.removeLast();
        line.addLast(new StationNode(newStation));
        line.addLast(tailTmp);
    }
    
    public void addStationToHead(String lineName, String newStation) {
        LinkedList<StationNode> line = metroNetwork.get(lineName);
        if (line == null) {
            System.out.println("Not an existing line name.");
            return;
        }
        line.addFirst(new StationNode(newStation));
    }
    
    public void removeStation(String lineName, String station) {
        LinkedList<StationNode> line = metroNetwork.get(lineName);
        if (line == null) {
            System.out.println("Not an existing line name.");
            return;
        }
        if (!line.remove(new StationNode(station))) {
            System.out.println("Not an existing station.");
        }
    }
    
    public void printLine(String lineName) {
        LinkedList<StationNode> line = metroNetwork.get(lineName);
    
        System.out.println(line.getLast() + " - " +
                line.getFirst() +" - " +
                line.get(1));
        for (int i = 0; i < line.size() - 2; ++i) {
            System.out.println(line.get(i) + " - " +
                    line.get(i + 1) + " - " +
                    line.get(i + 2));
        }
    }
}
