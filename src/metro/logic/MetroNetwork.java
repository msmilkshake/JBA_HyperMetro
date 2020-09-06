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
    
    public void connect(String line1Name, String station1,
            String line2Name, String station2) {
        
        LinkedList<StationNode> line1 = metroNetwork.get(line1Name);
        LinkedList<StationNode> line2 = metroNetwork.get(line2Name);
        
        if (isValidLine(line1, line2)) {
            int i1 = line1.indexOf(new StationNode(station1));
            int i2 = line2.indexOf(new StationNode(station2));
            
            if (i1 == -1 || i2 == -1) {
                System.out.println("Not an existing station.");
                return;
            }
            
            line1.get(i1).addTransferLine(line2Name, station2);
            line2.get(i2).addTransferLine(line1Name, station1);
        }
    }
    
    @SafeVarargs
    private boolean isValidLine(LinkedList<StationNode>... lines) {
        for (LinkedList<StationNode> line : lines) {
            if (line == null) {
                System.out.println("Not an existing line name.");
                return false;
            }
        }
        return true;
    }
}
