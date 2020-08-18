package metro.logic;

import metro.io.JsonRead;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

public class MetroNetwork {
    
    private HashMap<String, LinkedList<String>> metroNetwork;
    
    public MetroNetwork(File json) {
        metroNetwork = new HashMap<>();
        processLines(JsonRead.readFile(json));
    }
    
    private void processLines(HashMap<String, HashMap<Integer, String>> lines) {
        for (String lineName : lines.keySet()) {
            LinkedList<String> line = new LinkedList<>();
            metroNetwork.putIfAbsent(lineName, line);
            
            HashMap<Integer, String> ref = lines.get(lineName);
            for (int i = 1; i <= ref.size(); ++i) {
                line.addLast(ref.get(i));
            }
            line.addLast("depot");
        }
    }
    
    public void appendStation(String lineName, String newStation) {
        LinkedList<String> line = metroNetwork.get(lineName);
        if (line == null) {
            System.out.println("Not an existing line name.");
            return;
        }
        String tailTmp = line.removeLast();
        line.addLast(newStation);
        line.addLast(tailTmp);
    }
    
    public void addStationToHead(String lineName, String newStation) {
        LinkedList<String> line = metroNetwork.get(lineName);
        if (line == null) {
            System.out.println("Not an existing line name.");
            return;
        }
        line.addFirst(newStation);
    }
    
    public void removeStation(String lineName, String station) {
        LinkedList<String> line = metroNetwork.get(lineName);
        if (line == null) {
            System.out.println("Not an existing line name.");
            return;
        }
        if (!line.remove(station)) {
            System.out.println("Not an existing station.");
        }
    }
    
    public void printLine(String lineName) {
        LinkedList<String> line = metroNetwork.get(lineName);
    
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
