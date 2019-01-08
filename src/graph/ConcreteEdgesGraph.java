/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   Contains set of vertices in graph (no duplicates) and
    //   list of edges without duplicates
    // Representation invariant:
    //   No duplicate vertices or edges
    // Safety from rep exposure:
    //   Mutator methods check for equality with existing vertices and edges
    
    // TODO
    public ConcreteEdgesGraph(){}
    
    // TODO checkRep
    public boolean checkRep(){
        for (int i=0;i<edges.size();i++){
            for (int j=0;j<edges.size();j++){
                if (i!=j &&
                    edges.get(i).getStart().equals(edges.get(j).getStart()) &&
                    edges.get(i).getEnd().equals(edges.get(j).getEnd())){
                    return false;
                }
            }
        }
        return true;
    }

    
    @Override public boolean add(L vertex) {
        return vertices.add(vertex);
    }
    
    @Override public int set(L source, L target, int weight) {
        assert weight>=0 : "Weight must be positive non-zero integer";

        Iterator<Edge<L>> it = edges.iterator();
        int oldWeight = 0;
        while (it.hasNext()){
            Edge edge = it.next();
            if (edge.getStart().equals(source) && edge.getEnd().equals(target)){
                oldWeight = edge.getWeight();
                it.remove();
                break;
            }
        }
        if (weight==0){
            return oldWeight;
        }
        if (!vertices.contains(source)) vertices.add(source);
        if (!vertices.contains(target)) vertices.add(target);
        edges.add(new Edge<>(source,target,weight));
        return oldWeight;

    }
    
    @Override public boolean remove(L vertex) {
        edges.removeIf(e->e.getStart().equals(vertex)||e.getEnd().equals(vertex));
        return vertices.remove(vertex);
    }
    
    @Override public Set<L> vertices() {
        return new HashSet<>(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L,Integer> sources = new HashMap<>();
        edges.stream().filter(e->e.getEnd().equals(target)).forEach(e->sources.put(e.getStart(),e.getWeight()));
        return sources;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L,Integer> targets = new HashMap<>();
        edges.stream().filter(e->e.getStart().equals(source)).forEach(e->targets.put(e.getEnd(),e.getWeight()));
        return targets;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Vertices:\n");
        vertices.forEach(v->stringBuilder.append(v+"\n"));
        edges.forEach(e->stringBuilder.append(e+"\n"));
        return stringBuilder.toString();
    }
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    // TODO fields
    private L start;
    private L end;
    private int weight;
    
    // Abstraction function:
    //   Represents edge in graph with beginning vertex, end vertex and weight
    // Representation invariant:
    //   start and end vertices
    // Safety from rep exposure:
    //   private fields without setters
    
    // TODO constructor
    public Edge(L start, L end, int weight){
        assert weight>0 : "Positive non-zero weight required!";
        this.start = start;
        this.end = end;
        this.weight = weight;
    }
    
    // TODO checkRep
    public L getStart(){
        return start;
    }

    public L getEnd() {
        return end;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return start.toString()+"->"+end.toString()+": "+weight;
    }
}
