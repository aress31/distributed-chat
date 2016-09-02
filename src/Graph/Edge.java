/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.io.Serializable;
import java.util.HashSet;

/**
 *
 * @authors Ezzat, Alexandre & Simon
 */
public class Edge implements Comparable<Edge>, Serializable {
    
    private final String id; 
    private final Vertex source;
    private final Vertex destination;
    private final int weight; 
    private String intf;
    
    

    public Edge(String id, Vertex source, Vertex destination, int weight, String intf) {
        this.id = id;
        this.source = source;
        this.destination = destination;
        this.weight = weight;
        this.intf = intf;
    }
    public String getId() {
        return id;
    }
    public Vertex getDestination() {
        return destination;
    }

    public Vertex getSource() {
        return source;
    }
    public int getWeight() {
        return weight;
    }
    
    public String getIntf() {
        return intf;
    }

        
    @Override
    public String toString()
    {
        return "(" + source.getName() + ", " + destination.getName() + ") : Weight = " + weight;
    }
    public int compareTo(Edge edge)
    {
        //== is not compared so that duplicate values are not eliminated.
        return (this.weight < edge.weight) ? -1: 1;
    }

} 