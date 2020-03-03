package com.coding.exercise.codetest;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Data Structure for node in graph.
 * @param <T>
 */
public class Node<T> {

    /**
     * Node value
     */
    public T value;

    /**
     * List of Edges : List of adjacent nodes
     */
    private List<Node<T>> edges ;

    /**
     * Indicates whether node id visited on BFS traversal
     */
    private boolean visited = false;

    /**
     * Indiactes parent of node in shortest path of BFS traversal.
     */
    private Node<T> parent = null;

    public Node(T value){
        this.value = value;
        this.edges = new LinkedList<>();
    }

    public List<Node<T>> getEdges(){
        return this.edges;
    }

    public void addEdge(Node<T> neighbour){
        this.edges.add(neighbour);
    }

    public void setParent(Node<T> parent){
        this.parent = parent;
    }

    public Node<T> getParent(){
        return this.parent;
    }

    public void setVisited(boolean isVisited){
        this.visited = isVisited;
    }

    public boolean isVisited(){
        return this.visited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(value, node.value);
    }

    @Override
    public int hashCode() {

        return Objects.hash(value);
    }
}

