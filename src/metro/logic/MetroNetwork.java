package metro.logic;

import metro.util.DirectedGraph;
import metro.util.GraphUtil;
import metro.util.UndirectedGraph;

import java.io.File;
import java.util.*;

public class MetroNetwork {
    
    private final DirectedGraph<Station> GRAPH = new UndirectedGraph<>();
    private final Map<String, LinkedList<Station>> LINES = new HashMap<>();
    private final GraphUtil<Station> UTIL = new GraphUtil<>(GRAPH);
    
    public MetroNetwork(File file) {
        MetroBuilder.builder()
                .withFile(file)
                .withGraph(GRAPH)
                .withMap(LINES)
                .buildNetwork();
    }
    
    public Station getRef(String line, String station) {
        return GRAPH.getNode(new Station(line, station));
    }
    
    public boolean appendStation(String line, String station) {
        if (getRef(line, station) != null) {
            return false;
        }
        Station newStation = new Station(line, station);
        Station tail = LINES.get(line).getLast();
        GRAPH.addEdge(tail, newStation);
        LINES.get(line).add(newStation);
        return true;
    }
    
    public boolean addStationToHead(String line, String station) {
        if (getRef(line, station) != null) {
            return false;
        }
        Station depot = LINES.get(line).pop();
        Station newStation = new Station(line, station);
        Station head = LINES.get(line).getFirst();
        GRAPH.addEdge(newStation, head);
        LINES.get(line).push(newStation);
        LINES.get(line).push(depot);
        return true;
    }
    
    public Station removeStation(String line, String station) {
        Station toRemove = getRef(line, station);
        if (toRemove == null) {
            return null;
        }
        Iterator<Station> it = LINES.get(line).iterator();
        Station prev = null;
        Station next = null;
        while (it.hasNext()) {
            Station curr = it.next();
            if (curr.equals(toRemove)) {
                it.remove();
                if (it.hasNext()) {
                    next = it.next();
                }
                break;
            }
            prev = curr;
        }
        for (Station s : toRemove.getTransfers()) {
            getRef(s.getLine(), s.getStation());
            s.getTransfers().remove(toRemove);
        }
        GRAPH.removeNode(toRemove);
        if (prev != null && next != null) {
            GRAPH.addEdge(prev, next);
        }
        return toRemove;
    }
    
    public boolean connectStations(
            String line1, String station1,
            String line2, String station2) {
        Station ref1 = getRef(line1, station1);
        Station ref2 = getRef(line2, station2);
        if (ref1 == null || ref2 == null) {
            return false;
        }
        if (GRAPH.addEdge(ref1, ref2)) {
            ref1.addTransfer(ref2);
            ref2.addTransfer(ref1);
            return true;
        }
        return false;
    }
    
    public void printLine(String lineName) {
        if (LINES.getOrDefault(lineName, null) == null) {
            System.out.println("Line \"" + lineName + "\" does not exist.");
            return;
        }
        Station[] line = new Station[LINES.get(lineName).size()];
        LINES.get(lineName).toArray(line);
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (; i < line.length - 2; ++i) {
            sb.append(line[i])
                    .append(transfer(line[i])).append(" - ")
                    .append(line[i + 1])
                    .append(transfer(line[i + 1])).append(" - ")
                    .append(line[i + 2])
                    .append(transfer(line[i + 2])).append("\n");
        }
        sb.append(line[i])
                .append(transfer(line[i])).append(" - ")
                .append(line[i + 1])
                .append(transfer(line[i + 1])).append(" - ")
                .append(line[0])
                .append(transfer(line[0])).append("\n");
        System.out.println(sb.toString());
    }
    
    private String transfer(Station station) {
        Iterator<Station> it = station.getTransfers().iterator();
        if (it.hasNext()) {
            StringBuilder sb = new StringBuilder();
            while (it.hasNext()) {
                Station s = it.next();
                sb.append(" (").append(s.getLine())
                        .append(": ").append(s.getStation())
                        .append(")");
                if (it.hasNext()) {
                    sb.append(",");
                }
            }
            return sb.toString();
        }
        return "";
    }
    
    public void printRoute(String startLine, String startStation,
                           String endLine, String endStation) {
        Station start = getRef(startLine, startStation);
        Station end = getRef(endLine, endStation);
        UTIL.breadthFirstSearch(start, end);
        List<Station> path = UTIL.getPathTo(end);
        String currentLine = path.get(0).getLine();
        for (Station station : path) {
            if (!station.getLine().equals(currentLine)) {
                currentLine = station.getLine();
                System.out.println("Transition to line " + currentLine);
            }
            System.out.println(station.getStation());
        }
    }
    
    public void printGraph() {
        System.out.println(GRAPH);
    }
}
