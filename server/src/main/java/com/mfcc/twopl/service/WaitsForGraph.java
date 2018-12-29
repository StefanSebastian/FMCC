package com.mfcc.twopl.service;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author stefansebii@gmail.com
 */
@Component
public class WaitsForGraph {
    // edge meaning t1 waits for t2
    private class Edge {
        long t1;
        long t2;

        Edge(long t1, long t2){
            this.t1 = t1; this.t2 = t2;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Edge edge = (Edge) o;

            if (t1 != edge.t1) return false;
            return t2 == edge.t2;

        }

        @Override
        public int hashCode() {
            int result = (int) (t1 ^ (t1 >>> 32));
            result = 31 * result + (int) (t2 ^ (t2 >>> 32));
            return result;
        }
    }

    private Set<Edge> edges = new HashSet<>();

    public synchronized void addEdge(long t1, long t2) {
        edges.add(new Edge(t1, t2));
    }

    public synchronized void removeEdge(long t1, long t2) {
        edges.remove(new Edge(t1, t2));
    }

    private List<Long> possibleVictims = new LinkedList<>();
    private Boolean foundConflict = false;

    public synchronized void checkForConflicts(){
        foundConflict = false;
        possibleVictims = new LinkedList<>();
        Set<Long> visited = new HashSet<>(); // marks nodes which were visited
        for (Edge edge : edges) {
            if (!visited.contains(edge.t1)) {
                dfs(visited, edge.t1);
            }
        }
    }

    private void dfs(Set<Long> visited, Long currentNode) {
        if (visited.contains(currentNode)) {
            foundConflict = true;
            possibleVictims.add(currentNode);
            return;
        }
        visited.add(currentNode);
        for (Edge edge : edges) {
            if (edge.t1 == currentNode) {
                dfs(visited, edge.t2);
            }
        }
    }

    public Boolean getFoundConflict() {
        return this.foundConflict;
    }

    public List<Long> getPossibleVictims() {
        return this.possibleVictims;
    }

}
