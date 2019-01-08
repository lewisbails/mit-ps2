/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;
import java.util.stream.Collectors;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   Manages collection of distinct vertex objects
    // Representation invariant:
    //   No duplicate vertices
    // Safety from rep exposure:
    //   private fields, returns copies from getters
    
    // TODO constructor
    public ConcreteVerticesGraph(){}
    
    // TODO checkRep
    public boolean checkRep(){
        for (int i=0;i<vertices.size();i++){
            for (int j=0;j<vertices.size();j++){
                if (i!=j &&
                    vertices.get(i).getName().equals(vertices.get(j).getName())){
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override public boolean add(L vertex){
        for (Vertex existingVertex : vertices){
            if (existingVertex.getName().equals(vertex)) return false;
        }
        return vertices.add(new Vertex<>(vertex));
    }
    
    @Override public int set(L source, L target, int weight) {
        assert weight>=0 : "Must be positive weight";
        assert !source.equals(target) : "Source cannot be target";

        Iterator<Vertex> it = vertices.iterator();
        int oldWeight = 0;
        while (it.hasNext()) {
            Vertex<L> vertex = it.next();
            if (vertex.getName().equals(source)) {
                for (L v : vertex.getAdjacentTo().keySet()) {
                    if (v.equals(target)) {
                        oldWeight = vertex.getAdjacentTo().get(v);
                        it.remove();
                        break;
                    }
                }
            } else if (vertex.getName().equals(target)) {
                for (L v : vertex.getAdjacentFrom().keySet()) {
                    if (v.equals(source)) {
                        oldWeight = vertex.getAdjacentFrom().get(v);
                        it.remove();
                        break;
                    }
                }
            }
        }
        Vertex<L> To;
        Vertex<L> From;
        if (vertices.stream().filter(v->v.getName().equals(source)).collect(Collectors.toList()).isEmpty()){
            From = new Vertex<>(source);
            vertices.add(From);
        }
        else{
            From = vertices.stream().filter(v->v.getName().equals(source)).collect(Collectors.toList()).get(0);
        }
        if (vertices.stream().filter(v->v.getName().equals(target)).collect(Collectors.toList()).isEmpty()){
            To = new Vertex<>(target);
            vertices.add(To);
        }
        else{
            To = vertices.stream().filter(v->v.getName().equals(target)).collect(Collectors.toList()).get(0);
        }

        From.addTo(To.getName(),weight);
        To.addFrom(From.getName(),weight);
        return oldWeight;
    }
    
    @Override public boolean remove(L vertex) {
        if (vertices.removeIf(v->v.getName().equals(vertex))){
            for (Vertex<L> v : vertices){
                v.removeFrom(vertex);
                v.removeTo(vertex);
            }
            return true;
        }
        return false;
    }
    
    @Override public Set<L> vertices() {
        return vertices.stream().map(Vertex<L>::getName).collect(Collectors.toSet());
    }
    
    @Override public Map<L, Integer> sources(L target) {
        for (Vertex<L> v : vertices){
            if (v.getName().equals(target)){
                return v.getAdjacentFrom();
            }
        }
        return new HashMap<>();
    }
    
    @Override public Map<L, Integer> targets(L source) {
        for (Vertex<L> v : vertices){
            if (v.getName().equals(source)){
                return v.getAdjacentTo();
            }
        }
        return new HashMap<>();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        vertices.forEach(stringBuilder::append);
        return stringBuilder.toString();
    }
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex<L> {
    
    // TODO fields
    private L name;
    private Map<L,Integer> adjacentTo;
    private Map<L,Integer> adjacentFrom;
    
    // Abstraction function:
    //   Contains vertex string and all adjacent vertices
    // Representation invariant:
    //  Adjacent vertices do not include vertex itself
    // Safety from rep exposure:
    //   Private fields, defensive copying
    
    // TODO constructor
    public Vertex(L name){
        this.name = name;
        this.adjacentFrom = new HashMap<>();
        this.adjacentTo = new HashMap<>();
    }
    // TODO checkRep
    public boolean checkRep(){
        return !adjacentFrom.containsKey(name) && !adjacentTo.containsKey(name);
    }
    // TODO methods
    public boolean addTo(L v, int weight){
        if (adjacentTo.containsKey(v)){
            adjacentTo.put(v,weight);
            return true;
        }
        adjacentTo.put(v,weight);
        return false;
    }

    public boolean addFrom(L v, int weight){
        if (adjacentFrom.containsKey(v)){
            adjacentFrom.put(v,weight);
            return true;
        }
        adjacentFrom.put(v,weight);
        return false;
    }

    public L getName(){
        return name;
    }

    public Map<L, Integer> getAdjacentFrom() {
        return new HashMap<>(adjacentFrom);
    }

    public Map<L, Integer> getAdjacentTo() {
        return new HashMap<>(adjacentTo);
    }

    public boolean removeTo(L vertex){
        if (adjacentTo.containsKey(vertex)){
            adjacentTo.remove(vertex);
            return true;
        }
        return false;
    }

    public boolean removeFrom(L vertex){
        if (adjacentFrom.containsKey(vertex)){
            adjacentFrom.remove(vertex);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        adjacentTo.forEach((k,v)->stringBuilder.append(name+"->"+k+": "+v+"\n"));
        adjacentFrom.forEach((k,v)->stringBuilder.append(k+"->"+name+": "+v+"\n"));
        return stringBuilder.toString();
    }
}
