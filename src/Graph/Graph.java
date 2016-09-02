/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

/**
 *
 * @authors Ezzat, Alexandre & Simon
 */


import InterfaceS.GlobalServer;
import Network.Link;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;


public class Graph implements Serializable {
    
    private final List<Vertex> vertices;
    private final List<Edge> edges;
    private KruskalAlgorithm mst = new KruskalAlgorithm();
    
    
    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }
    
    public KruskalAlgorithm getMst() {
        return mst;
    }

    public void setMst(KruskalAlgorithm mst) {
        this.mst = mst;
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }
  
    
    
    public void fillGraph(ArrayList<Link> list) {
        
        Vertex source = new Vertex(list.get(0).getNameSource());
        this.vertices.add(source);
        
        for(int i=0; i< list.size(); i++) {
            
            Vertex destination = new Vertex(list.get(i).getNameDest());
            this.vertices.add(destination);
            
            Edge edge = new Edge(list.get(i).getIdL(),source,destination,list.get(i).getWeight(), list.get(i).getIntf());
            this.edges.add(edge);
            
        }
        
        
       TreeSet<Edge> edgesT = new TreeSet<Edge>();
                
        for(Edge edge : this.edges)
            edgesT.add(edge);
       
        KruskalAlgorithm help = new KruskalAlgorithm();

        for (Edge edge : edgesT) {
            System.out.println(edge);
            help.insertEdge(edge);
        }

        this.mst = help;
        
        System.out.println("Kruskal algorithm");
        int total = 0;
        for (Edge edge : this.mst.getEdges()) {
            System.out.println(edge);
            total += edge.getWeight();
        }
        System.out.println("Total weight is " + total);
        
        GlobalServer.mfs.setJTextArea2();
    }
    
    public void updateGraph(Graph graph){
        
        System.out.println("hi");
        
       for(Vertex vertex : graph.vertices) {
           if(searchVertex(vertex.getName()) == null) {
               this.vertices.add(vertex);
           }
       }
       
       for(Edge edge : graph.edges) {
           if(searchEdge(edge) == null) {
               this.edges.add(edge);
           }
       }
                
       TreeSet<Edge> edgesT = new TreeSet<Edge>();
                
        for(Edge edge : this.edges)
            edgesT.add(edge);
       
        KruskalAlgorithm help = new KruskalAlgorithm();

        for (Edge edge : edgesT) {
            System.out.println(edge);
            help.insertEdge(edge);
        }
        
        this.mst = help;

        System.out.println("Kruskal algorithm");
        int total = 0;
        for (Edge edge : this.mst.getEdges()) {
            System.out.println(edge);
            total += edge.getWeight();
        }
        System.out.println("Total weight is " + total);
        
        GlobalServer.mfs.setJTextArea2();
        
    }
    
    public Vertex searchVertex(String name) {
        for (Vertex vertex : vertices) {
            if (vertex.getName().equals(name)) {
                return vertex;
            }
        }
        
        return null; 
    }
    
    public Edge searchEdge(Edge edgeP) {
        for (Edge edge : edges) {
            if (edge.getId().equals(edgeP.getId())) {
                return edge;
            }
            if (edge.getDestination().getName().equals(edgeP.getSource().getName()) 
                    && edge.getSource().getName().equals(edgeP.getDestination().getName())){
                return edge;
            }
                
        }
        
        return null; 
    }
} 
