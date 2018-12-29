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

    public synchronized void removeEdges(long t) {
        edges.removeIf(edge -> edge.t1 == t || edge.t2 == t);
    }

    private Set<Long> possibleVictims = new HashSet<>();
    private Boolean foundConflict = false;

    public synchronized void checkForConflicts(){
        foundConflict = false;
        possibleVictims = new HashSet<>();

        Set<Long> toVisit = new HashSet<>();
        for (Edge edge : edges) {
            toVisit.add(edge.t1); toVisit.add(edge.t2);
        }
        for (Long node : toVisit){
            dfs(node, new HashSet<>(), new LinkedList<>());
        }
    }

    private void dfs(Long currentNode, Set<Long> visited, List<Long> path) {
        if (visited.contains(currentNode)) {
            foundConflict = true;
            possibleVictims.addAll(path.subList(path.indexOf(currentNode), path.size()));
            return;
        }
        visited.add(currentNode);
        path.add(currentNode);
        for (Edge edge : edges) {
            if (edge.t1 == currentNode) {
                dfs(edge.t2, visited, path);
            }
        }
        visited.remove(currentNode);
        path.remove(currentNode);
    }

    public Boolean getFoundConflict() {
        return this.foundConflict;
    }

    public Set<Long> getPossibleVictims() {
        return this.possibleVictims;
    }

}
