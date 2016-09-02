/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Network;

//import Client.Client;
import Client.Client;
import Client.ClientInterface;
import Graph.DijkstraAlgorithm;
import Graph.Edge;
import Graph.Graph;
import Graph.KruskalAlgorithm;

import Graph.Vertex;
import InterfaceS.GlobalServer;
//import com.sun.security.ntlm.Client;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ezzat
 */
public class Router extends UnicastRemoteObject implements RouterInterface {
    
    private String idR;
    private String nameR;
    ArrayList<Link> neighborsR = new ArrayList<>();
    
    private final List<Vertex> vertices = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();
    private final Graph graph = new Graph(vertices,edges);
    
    ArrayList<String> directlyConnectedClients = new ArrayList<String>();
    ArrayList<String> connectedClients = new ArrayList<String>();
    
    public Router(String idR, String nameR) throws RemoteException {
        this.idR = idR;
        this.nameR = nameR;
    }

    public ArrayList<String> getConnectedClients() throws RemoteException {
        return connectedClients;
    }

    public ArrayList<String> getDirectlyConnectedClients() throws RemoteException {
        return directlyConnectedClients;
    }

    
    public Graph getGraph() throws RemoteException{
        return graph;
    }

    public String getIdR() {
        return idR;
    }

    public String getNameR() {
        return nameR;
    }

    public ArrayList<Link> getNeighborsR() {
        return neighborsR;
    }

    public void setIdR(String idR) {
        this.idR = idR;
    }

    public void setNameR(String nameR) {
        this.nameR = nameR;
    }

    public void setNeighborsR(ArrayList<Link> neighborsR) {
        this.neighborsR = neighborsR;
    }
    
    public void fillRouter(File filename) {
        String sCurrentLine;
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            
            sCurrentLine = br.readLine();
            
            String[] token = sCurrentLine.split("\\s+");
            this.idR = token[0];
            this.nameR = token[1];
            
            br.readLine();
            
            while((sCurrentLine = br.readLine()) != null) {
                token = sCurrentLine.split("\\s+");
                this.neighborsR.add(new Link(token[0], this, token[2], Integer.parseInt(token[3]), token[4]));
            } 
            
        }
        
        catch (IOException e) {
            e.printStackTrace();
        }
        
        
        
       this.graph.fillGraph(this.neighborsR);
       
       
    }
    
    public void getStubs() {
        //System.out.println(this.graph);
        String url = null;
        for(int i=0; i<this.neighborsR.size(); i++) {
            
            
       url = "rmi://localhost/"+this.neighborsR.get(i).getNameDest();   
            
//            try {
//                url = "rmi://"+InetAddress.getLocalHost().getHostAddress()+ "/"+this.neighborsR.get(i).getNameDest();
//            } 
//            
//            catch (UnknownHostException ex) {
//                Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            
            
            try {
                RouterInterface stub = (RouterInterface) Naming.lookup(url);
                this.neighborsR.get(i).setInterfaceDest(stub);
                stub.notifyConnection(this.nameR);
                System.out.println("Router ok");
                stub.giveNeighborsList(this.graph);
                
            } 
            
            catch (NotBoundException | MalformedURLException | RemoteException ex) {
                //ex.printStackTrace();
                System.out.println("Router not yet connected" + url);
            }
        }
        GlobalServer.mfs.setJTextArea1();
    }
    
    public void notifyConnection(String name) throws RemoteException{
        String url = null;
        
        
        url = "rmi://localhost/"+ name;
        
//        try {
//                url = "rmi://"+InetAddress.getLocalHost().getHostAddress()+ "/"+ name;
//            } 
//            
//            catch (UnknownHostException ex) {
//                Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            try {
                RouterInterface stub = (RouterInterface) Naming.lookup(url);
                
                for (Link link : neighborsR) {
                    if (link.getNameDest().equals(name)) {
                        link.setInterfaceDest(stub);
                        System.out.println("Router ok");
                        stub.giveNeighborsList(this.graph);
                    }
                }
            }
            catch (NotBoundException | MalformedURLException | RemoteException ex) {
                //ex.printStackTrace();
            }
            GlobalServer.mfs.setJTextArea1();
    } 
    
    public void giveNeighborsList(Graph remoteGraph) throws RemoteException {
        
        this.graph.updateGraph(remoteGraph);
        
    }
    
    public boolean checkExistanceUser(String cl){
        
        for(String client : this.connectedClients) {
            
            if(client.equals(cl)) {
                return true;
            }
        }
        for(String client : this.directlyConnectedClients) {
            
            if(client.equals(cl)) {
                return true;
            }
        }
        return false;
    }
    
    public boolean registerUser(String cl){
        
        if(this.checkExistanceUser(cl) == false) {
            this.directlyConnectedClients.add(cl);
            
            return true;
        }
        else {
            return false;
        }
    }
    
    public boolean propagateUser(String cl) throws RemoteException {
        
        if(this.registerUser(cl) == true){
        
            for(Edge edge : this.graph.getMst().getEdges()) {
                if(edge.getSource().getName().equals(this.nameR)){
                    for(Link link : this.neighborsR) {
                        if(link.getNameDest().equals(edge.getDestination().getName())){
                            System.out.println(link.getInterfaceDest());
                            if(link.getInterfaceDest() != null)
                                link.getInterfaceDest().recieveUser(cl, this.graph.getMst());
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    public void recieveUser(String cl, KruskalAlgorithm paraMst)throws RemoteException {
  
            this.connectedClients.add(cl);
            
            String urlClient;
            for(String combined : directlyConnectedClients) {

                String arr[] = combined.split(" ", 2);
                String client = arr[0];
                
                urlClient = "rmi://localhost/"+ client;
            
                try {
                    ClientInterface stub = (ClientInterface) Naming.lookup(urlClient);
                    stub.refreshUsersList();
                } catch (        NotBoundException | MalformedURLException ex) {
                    Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                
                
                
//                try {
//                     urlClient = "rmi://"+InetAddress.getLocalHost().getHostAddress()+ "/"+ client;
//                     ClientInterface stub = (ClientInterface) Naming.lookup(urlClient);
//                     stub.refreshUsersList();
//                } catch (NotBoundException | MalformedURLException | UnknownHostException ex) {
//                    Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
//                }
            }
            
        
            for(Edge edge : paraMst.getEdges()) {
                if(edge.getSource().getName().equals(this.nameR)){
                    for(Link link : this.neighborsR) {
                        if(link.getNameDest().equals(edge.getDestination().getName())){
                            if(link.getInterfaceDest() != null)
                                link.getInterfaceDest().recieveUser(cl, paraMst);
                        }
                    }
                }
            
        }
        
    }
    
    public void broadcastMsg(String sender, String msg, KruskalAlgorithm paraMst) throws RemoteException {
        
        String urlClient;
        for(String combined : directlyConnectedClients) {
            
            String arr[] = combined.split(" ", 2);
            String client = arr[0];
            
            urlClient = "rmi://localhost/"+ client;
            
            try {
                ClientInterface stub = (ClientInterface) Naming.lookup(urlClient);
                stub.displayMsg(sender, msg, 0);
            } catch (    NotBoundException | MalformedURLException ex) {
                Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
            
            
//            try {
//                 urlClient = "rmi://"+InetAddress.getLocalHost().getHostAddress()+ "/"+ client;
//                 ClientInterface stub = (ClientInterface) Naming.lookup(urlClient);
//                 stub.displayMsg(sender, msg);
//            } catch (NotBoundException | MalformedURLException | UnknownHostException ex) {
//                Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
//            }
            
            
            
        }
        for(Edge edge : paraMst.getEdges()) {
            if(edge.getSource().getName().equals(this.nameR)){
                    for(Link link : this.neighborsR) {
                        if(link.getNameDest().equals(edge.getDestination().getName())){
                            if(link.getInterfaceDest() != null)
                                link.getInterfaceDest().broadcastMsg(sender, msg, paraMst);

                        }

                }
            
            }
        }
    }
    
    public void sendMessage(String sender, String receiver, String msg) throws RemoteException {
    

            DijkstraAlgorithm sst = new DijkstraAlgorithm(this.graph);
            sst.execute(this.graph.searchVertex(this.nameR));
            
            String arr[] = receiver.split(" ", 2);
            String rcv = arr[0];
            String dstRouter = arr[1];
            
            LinkedList<Vertex> path = sst.getPath(this.graph.searchVertex(dstRouter));
            
            propagateMsg(sender, receiver, msg, path);
           
            
    }
    public void propagateMsg(String sender, String receiver, String msg, LinkedList<Vertex> path) throws RemoteException {
        
        System.out.println("-------------------------------");
        System.out.println(this.checkExistanceUser(receiver));
        if(this.checkExistanceUser(receiver) == true){

        System.out.println("-------------------------------");
        System.out.println(this.getNameR());
        System.out.println(path.getLast().getName());
        System.out.println("-------------------------------");
        
            if(path.size() == 1){
                
                ClientInterface stub = null;
                
                String arr[] = receiver.split(" ", 2);
                String rcv = arr[0];
                
                try {
                    stub = (ClientInterface) Naming.lookup("rmi://localhost/"+ rcv);
                    stub.displayMsg(sender, msg, 1);
                } catch (        NotBoundException | MalformedURLException ex) {
                    Logger.getLogger(Router.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else {
                
                path.remove();
                String headName = path.getFirst().getName();
                
                for(Link router : this.getNeighborsR())
                    if(router.getNameDest().equals(headName))
                        router.getInterfaceDest().propagateMsg(sender, receiver, msg, path);        
                }
        }
    }
    

}
