package metro.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GraphUtil<T> {
    
    private Map<T, Double> distances;
    private Map<T, T> previousNodes;
    private DirectedGraph<T> graph;
    
    public GraphUtil(DirectedGraph<T> graph) {
        this.graph = graph;
    }
    
    public void loadGraph(DirectedGraph<T> graph) {
        this.graph = graph;
    }
    
    public void breadthFirstSearch(T from) {
        breadthFirstSearch(from, null);
    }
    
    public void breadthFirstSearch(T from, T to) {
        Set<T> nodes = new HashSet<>(graph.getNodes());
        previousNodes = new HashMap<>(nodes.size(), 1);
        Set<T> visited = new HashSet<>();
        LinkedList<T> queue = new LinkedList<>();
        queue.add(from);
        while (!queue.isEmpty()) {
            T parent = queue.pop();
            visited.add(parent);
            for (T node : graph.outDegreeOf(parent)) {
                if (visited.contains(node)) {
                    continue;
                }
                queue.add(node);
                previousNodes.putIfAbsent(node, parent);
                if (node.equals(to)) {
                    return;
                }
            }
        }
    }
    
    public void dijkstraSearch(T from) {
        Set<T> nodes = new HashSet<>(graph.getNodes());
        Set<T> settledNodes = new HashSet<>();
        distances = new HashMap<>(nodes.size(), 1);
        previousNodes = new HashMap<>(nodes.size(), 1);
        for (T node : nodes) {
            distances.put(node, Double.POSITIVE_INFINITY);
        }
        distances.put(from, 0.0);
        T previous = from;
        
        while (!nodes.isEmpty()) {
            Set<T> outDegree = graph.outDegreeOf(previous);
            for (T node : outDegree) {
                if (settledNodes.contains(node)) {
                    continue;
                }
                double weight = graph.getEdgeWeight(previous, node);
                if (weight < distances.get(node)) {
                    distances.put(node, distances.get(previous) + weight);
                    previousNodes.put(node, previous);
                }
            }
            settledNodes.add(previous);
            nodes.remove(previous);
            double smallest = Double.POSITIVE_INFINITY;
            for (T node : nodes) {
                smallest = Math.min(smallest, distances.get(node));
            }
            for (T node : nodes) {
                if (distances.get(node) == smallest) {
                    previous = node;
                }
            }
        }
    }
    
    public List<T> getPathTo(T to) {
        if (previousNodes == null || previousNodes.isEmpty()) {
            return null;
        }
        T next = to;
        LinkedList<T> list = new LinkedList<>();
        while (next != null) {
            list.addFirst(next);
            next = previousNodes.getOrDefault(next, null);
        }
        return list;
    }
    
    public double getDistanceTo(T to) {
        if (distances == null || distances.isEmpty()) {
            return Double.NaN;
        }
        return distances.getOrDefault(to, Double.NaN);
    }
}
