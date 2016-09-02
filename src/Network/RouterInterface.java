/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Network;

import Client.Client;
import Graph.Graph;
import Graph.KruskalAlgorithm;
import Graph.Vertex;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 *
 * @author Ezzat
 */
public interface RouterInterface extends Remote {
    
      
        
        public void giveNeighborsList(Graph remoteGraph) throws RemoteException;
        public void notifyConnection(String name) throws RemoteException;
        
        
        public Graph getGraph() throws RemoteException;
        public ArrayList<String> getConnectedClients() throws RemoteException;
        public ArrayList<String> getDirectlyConnectedClients() throws RemoteException;
        public boolean propagateUser(String cl)throws RemoteException;
        public void recieveUser(String cl, KruskalAlgorithm ParaMst)throws RemoteException;
        public void broadcastMsg(String sender, String msg, KruskalAlgorithm paraMst) throws RemoteException;
        
        public void sendMessage(String sender, String receiver, String msg) throws RemoteException;
        public void propagateMsg(String sender, String receiver, String msg, LinkedList<Vertex> path) throws RemoteException;
}
