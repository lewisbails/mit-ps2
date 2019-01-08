/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    @Test
    public void testInitialVerticesEmpty() {
        super.testInitialVerticesEmpty();
    }

    @Test
    public void testAdd(){
        super.testAdd();
    }

    @Test
    public void testSet(){
        super.testSet();
    }

    @Test
    public void testRemove(){
        super.testRemove();
    }

    @Test
    public void testVertices(){
        super.testVertices();
    }

    @Test
    public void testSources(){
        super.testSources();
    }

    @Test
    public void testTargets(){
        super.testTargets();
    }

    // Testing strategy for ConcreteEdgesGraph.toString()
    @Test
    public void testToString(){
    }

    /*
     * Testing Edge...
     */

    // Testing strategy for Edge
    //   TODO

    // TODO tests for operations of Edge

    @Test
    public void testEdge(){

    }

}