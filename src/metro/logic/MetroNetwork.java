package metro.logic;

import metro.util.JsonRead;
import metro.util.MetroUtil;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;

public class MetroNetwork {
    
    private HashMap<String, LinkedList<StationNode>> metroNetwork;
    
    public MetroNetwork(File json) {
        metroNetwork = MetroUtil.rawNetworkBuilder(JsonRead.readFile(json));
    }
    
    public void appendStation(String lineName, String newStation) {
        LinkedList<StationNode> line = metroNetwork.get(lineName);
        if (isValidLine(line)) {
            StationNode tailTmp = line.removeLast();
            line.addLast(new StationNode(newStation));
            line.addLast(tailTmp);
        }
    }
    
    public void addStationToHead(String lineName, String newStation) {
        LinkedList<StationNode> line = metroNetwork.get(lineName);
        if (isValidLine(line)) {
            line.addFirst(new StationNode(newStation));
        }
    }
    
    public void removeStation(String lineName, String station) {
        LinkedList<StationNode> line = metroNetwork.get(lineName);
        if (isValidLine(line)) {
            if (!line.remove(new StationNode(station))) {
                System.out.println("Not an existing station.");
            }
        }
    }
    
    public void printLine(String lineName) {
        LinkedList<StationNode> line = metroNetwork.get(lineName);
        
        if (isValidLine(line)) {
            System.out.println(line.getLast());
            for (StationNode station : line) {
                System.out.println(station);
            }
        }
    }
    
    private boolean isValidLine(LinkedList<StationNode> line) {
        if (line == null) {
            System.out.println("Not an existing line name.");
            return false;
        }
        return true;
    }
}
