/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Graph;

import java.io.Serializable;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Vector;

/**
 *
 * @author Ezzat
 */
public class KruskalAlgorithm implements Serializable {

    
    Vector<HashSet<String>> vertexGroups = new Vector<HashSet<String>>();
    TreeSet<Edge> kruskalEdges = new TreeSet<Edge>();

    public TreeSet<Edge> getEdges()
    {
        return kruskalEdges;
    }
    HashSet<String> getVertexGroup(String vertex)
    {
        for (HashSet<String> vertexGroup : vertexGroups) {
            if (vertexGroup.contains(vertex)) {
                return vertexGroup;
            }
        }
        return null;
    }
    public void insertEdge(Edge edge)
    {
        String vertexA = edge.getSource().getName();
        String vertexB = edge.getDestination().getName();

        HashSet<String> vertexGroupA = getVertexGroup(vertexA);
        HashSet<String> vertexGroupB = getVertexGroup(vertexB);

        if (vertexGroupA == null) {
            kruskalEdges.add(edge);
            if (vertexGroupB == null) {
                HashSet<String> htNewVertexGroup = new HashSet<String>();
                htNewVertexGroup.add(vertexA);
                htNewVertexGroup.add(vertexB);
                vertexGroups.add(htNewVertexGroup);
            }
            else {
                vertexGroupB.add(vertexA);           
            }
        }
        else {
            if (vertexGroupB == null) {
                vertexGroupA.add(vertexB);
                kruskalEdges.add(edge);
            }
            else if (vertexGroupA != vertexGroupB) {
                vertexGroupA.addAll(vertexGroupB);
                vertexGroups.remove(vertexGroupB);
                kruskalEdges.add(edge);
            }
        }
    }
}