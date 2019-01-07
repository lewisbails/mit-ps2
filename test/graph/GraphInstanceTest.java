/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO
    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }

    @Test
    public void testAdd(){
        Graph<String> newGraph = emptyInstance();
        assertTrue(newGraph.add("vertex 1"));
        assertFalse(newGraph.add("vertex 1"));
    }

    @Test
    public void testSet(){
        Graph<String> newGraph = emptyInstance();
        newGraph.add("vertex 1");
        newGraph.add("vertex 2");
        // new edge, return previous weight of 0
        assertEquals(0,newGraph.set("vertex 1","vertex 2",5));
        // update weight, return previous weight of 5
        assertEquals(5,newGraph.set("vertex 1","vertex 2",10));
        // add reverse edge, check returned previous weight is 0 (directedness)
        assertEquals(0,newGraph.set("vertex 2","vertex 1", 5));
        // remove edge, return previous weight of 10 (return from delection correct)
        assertEquals(10,newGraph.set("vertex 1","vertex 2",0));
        // new edge, return previous weight of 0 (actual deletion correct)
        assertEquals(0,newGraph.set("vertex 1", "vertex 2", 5));
    }

    @Test
    public void testRemove(){
        Graph<String> newGraph = emptyInstance();
        newGraph.add("vertex 1");
        newGraph.add("vertex 2");
        newGraph.set("vertex 1","vertex 2",10);

        // remove non existant vertex
        assertFalse(newGraph.remove("vertex 3"));
        // remove existant vertex
        assertTrue(newGraph.remove("vertex 1"));
        // remove existant vertex again
        assertFalse(newGraph.remove("vertex 1"));
        // check for edge deletion
        assertEquals(0,newGraph.set("vertex 1","vertex 2",10));

    }

    @Test
    public void testVertices(){
        Graph<String> newGraph = emptyInstance();
        newGraph.add("vertex 1");
        newGraph.add("vertex 2");

        assertEquals(2,newGraph.vertices().size());
        newGraph.remove("vertex 1");
        assertEquals(1,newGraph.vertices().size());
        newGraph.add("vertex 2");
        assertEquals(1,newGraph.vertices().size());

    }

    @Test
    public void testSources(){
        Graph<String> newGraph = emptyInstance();
        newGraph.add("vertex 1");
        newGraph.add("vertex 2");
        newGraph.set("vertex 1", "vertex 2",10);
        assertEquals(10,(int) newGraph.sources("vertex 2").get("vertex 1"));
        assertTrue(newGraph.sources("vertex 1").isEmpty());
        assertTrue(newGraph.sources("vertex 3").isEmpty());

        newGraph.set("vertex 3","vertex 2", 5);
        assertTrue(newGraph.sources("vertex 2").size()==2);
    }

    @Test
    public void testTargets() {
        Graph<String> newGraph = emptyInstance();
        newGraph.add("vertex 1");
        newGraph.add("vertex 2");
        newGraph.set("vertex 1", "vertex 2",10);
        assertEquals(10,(int) newGraph.targets("vertex 1").get("vertex 1"));
        // test directedness
        assertTrue(newGraph.targets("vertex 2").isEmpty());
        // test non existent vertices
        assertTrue(newGraph.targets("vertex 3").isEmpty());
        // test multiple sources
        newGraph.set("vertex 3","vertex 2", 5);
        assertTrue(newGraph.sources("vertex 2").size()==2);

    }
    
}
