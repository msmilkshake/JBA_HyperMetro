package metro.util;

import java.util.Set;

public class UndirectedGraph<T> extends DirectedGraph<T> {
    
    @Override
    public int getEdgeCount() {
        return super.getEdgeCount() / 2;
    }
    
    @Override
    public boolean addEdge(T node1, T node2, double weight, boolean addsNodes) {
        if (super.addEdge(node1, node2, weight, addsNodes)) {
            return super.addEdge(node2, node1, weight, addsNodes);
        }
        return false;
    }
    
    @Override
    public boolean removeEdge(T node1, T node2) {
        if (super.removeEdge(node1, node2)) {
            return super.removeEdge(node2, node1);
        }
        return false;
    }
    
    @Override
    public Set<T> inDegreeOf(T node) {
        return super.outDegreeOf(node);
    }
}
