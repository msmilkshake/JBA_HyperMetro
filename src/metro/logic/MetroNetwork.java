package metro.logic;

import metro.io.JsonIO;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

public class MetroNetwork {
    
    private HashMap<String, LinkedList<String>> metroNetwork;
    
    public MetroNetwork(File json) {
        metroNetwork = new HashMap<>();
        processLines(JsonIO.readFile(json));
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
    
    private static void printStations(LinkedList<String> line) {
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
