package metro.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class StationNode {
    
    private String name;
    private HashMap<String, ArrayList<String>> transferStations;
    
    public StationNode(String name) {
        this.name = name;
        transferStations = new HashMap<>();
    }
    
    public void addTransferLine(String line, String station) {
        transferStations.putIfAbsent(line, new ArrayList<>());
        transferStations.get(line).add(station);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name);
        for (String line : transferStations.keySet()) {
            for (String station : transferStations.get(line))
            sb.append(" - ").append(station)
                    .append(" (").append(line).append(")");
        }
        return sb.toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof StationNode)) {
            return false;
        }
        StationNode stationNode = (StationNode) o;
        return Objects.equals(name, stationNode.name);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
