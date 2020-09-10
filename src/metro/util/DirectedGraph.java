package metro.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class DirectedGraph<T> {
    private final Map<T, Map<T, Double>> graphData = new HashMap<>();
    private final Map<T, T> nodeSet = new HashMap<>();
    protected int edgeCount = 0;
    
    public int getNodeCount() {
        return nodeSet.size();
    }
    
    public int getEdgeCount() {
        return edgeCount;
    }
    
    public boolean addNode(T node) {
        graphData.putIfAbsent(node, new HashMap<>());
        return nodeSet.putIfAbsent(node, node) == null;
    }
    
    public T getNode(T node) {
        return nodeSet.getOrDefault(node, null);
    }
    
    public T removeNode(T node) {
        node = getNode(node);
        if (node != null) {
            edgeCount -= graphData.get(node).size();
            graphData.remove(node);
            nodeSet.remove(node);
            for (T t : graphData.keySet()) {
                if (graphData.get(t).remove(node) != null) {
                    --edgeCount;
                }
            }
        }
        return node;
    }
    
    public Iterator<T> nodeIterator() {
        return graphData.keySet().iterator();
    }
    
    public Set<T> getNodes() {
        return nodeSet.keySet();
    }
    
    public boolean addEdge(T from, T to) {
        return addEdge(from, to, 1.0);
    }
    
    public boolean addEdge(T from, T to, double weight) {
        return addEdge(from, to, weight, true);
    }
    
    public boolean addEdge(T from, T to, double weight, boolean addsNodes) {
        if (addsNodes) {
            addNode(from);
            addNode(to);
        }
        if ((from = getNode(from)) == null
                || (to = getNode(to)) == null) {
            return false;
        }
        Double oldWeight = graphData.get(from).put(to, weight);
        if (oldWeight == null) {
            ++edgeCount;
        }
        return !Objects.equals(weight, oldWeight);
    }
    
    public double getEdgeWeight(T from, T to) {
        if (hasEdge(from, to)) {
            return graphData.get(from).get(to);
        }
        return Double.NaN;
    }
    
    private boolean hasEdge(T from, T to) {
        return graphData.getOrDefault(from, new HashMap<>(0)).containsKey(to);
    }
    
    public boolean removeEdge(T from, T to) {
        if (hasEdge(from, to)) {
            graphData.get(from).remove(to);
            --edgeCount;
            return true;
        }
        return false;
    }
    
    public Set<T> outDegreeOf(T node) {
        if (getNode(node) == null) {
            return null;
        }
        return graphData.get(node).keySet();
    }
    
    public Set<T> inDegreeOf(T node) {
        Set<T> inDegree = new HashSet<>();
        for (T graphNode : nodeSet.keySet()) {
            if (graphData.get(graphNode).getOrDefault(node, null) != null) {
                inDegree.add(graphNode);
            }
        }
        return inDegree.isEmpty() ? null : inDegree;
    }
    
    public void clear() {
        graphData.clear();
        nodeSet.clear();
        edgeCount = 0;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DirectedGraph)) {
            return false;
        }
        DirectedGraph<?> that = (DirectedGraph<?>) o;
        return Objects.equals(graphData, that.graphData);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(graphData);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        Iterator<T> nodesIter = graphData.keySet().iterator();
        while (nodesIter.hasNext()) {
            T node = nodesIter.next();
            sb.append(node);
            Iterator<T> edgesIter = graphData.get(node).keySet().iterator();
            sb.append(":[");
            while (edgesIter.hasNext()) {
                T edge = edgesIter.next();
                sb.append("<")
                        .append(edge)
                        .append(", ").append(graphData.get(node).get(edge))
                        .append(">");
                if (edgesIter.hasNext()) {
                    sb.append(", ");
                }
            }
            sb.append("]");
            if (nodesIter.hasNext()) {
                sb.append(", ");
            }
        }
        return sb.append("}").toString();
    }
}