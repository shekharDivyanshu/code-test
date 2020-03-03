package com.coding.exercise.codetest;

import java.util.*;

/**
 * Data structure for graph.
 *
 * @param <T>
 */
public class Graph<T>{

    /**
     * Contains List of all nodes in graph
     */
    private List<Node<T>> nodes;

    /**
     * Map for constant time retrieval of Node based on value.
     */
    private Map<T, Node<T>> nodeMap ;

    public Graph(){
        this.nodes = new LinkedList<>();
        this.nodeMap = new HashMap<>();
    }

    public void reset(){
        this.nodes.stream().forEach(p -> {
            p.setParent(null);
            p.setVisited(false);
        });
    }

    public void addNode(Node<T> newNode){
        this.nodes.add(newNode);
        this.nodeMap.put(newNode.value, newNode);
    }

    public List<Node<T>> getNodeList(){
        return this.nodes;
    }

    public Node<T> getNode(T nodeValue){
        return this.nodeMap.get(nodeValue);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Graph<?> graph = (Graph<?>) o;
        return Objects.equals(nodes, graph.nodes);
    }

    @Override
    public int hashCode() {

        return Objects.hash(nodes);
    }
}
