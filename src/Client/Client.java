/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;


import InterfaceC.Global;
import Network.RouterInterface;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client extends UnicastRemoteObject implements ClientInterface, Serializable {
    
    private String nickname;
    private String urlClient;
    private String connectedRouter;
    private transient RouterInterface routerStub;

    public Client(String nickname, String urlClient, String connectedRouter) throws RemoteException {
        this.nickname = nickname;
        this.urlClient = urlClient;
        this.connectedRouter = connectedRouter;
    }

    public String getUrlClient() {
        return urlClient;
    }
 
    
    
    public String getNickname() {
        return nickname;
    }

    public String getConnectedRouter() {
        return connectedRouter;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public RouterInterface getRouterStub() {
        return routerStub;
    }


    public void setConnectedRouterURL(String connectedRouter) {
        this.connectedRouter = connectedRouter;
    }

    public void setRouterStub(RouterInterface routerStub) {
        this.routerStub = routerStub;
    }
   
    
    public boolean connectToRouter() {

            System.out.println(this.connectedRouter);
            
            String url = null;

            url = "rmi://localhost/"+this.connectedRouter;
            
//                try {
//                    url = "rmi://"+InetAddress.getLocalHost().getHostAddress()+ "/"+this.connectedRouter;
//                } catch (UnknownHostException ex) {
//                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
//                }
        try {
            this.routerStub =  (RouterInterface) Naming.lookup(url);
            System.out.println(this.routerStub);
            String combined = this.nickname + " " + this.connectedRouter;
            return (this.routerStub.propagateUser(combined));
        } catch (NotBoundException | MalformedURLException | RemoteException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
            

        return false;
    }
    
    public void displayMsg(String sender, String message, int type) throws RemoteException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date)+" - "+sender+": "+message);
        Global.mf.setJTextArea1(dateFormat.format(date)+" - "+sender+": "+message+"\n", type);
    }
    
//    public void displayPrivateMsg(String sender, String message) throws RemoteException {
//        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
//        System.out.println(dateFormat.format(date)+" - "+sender+": "+message);
//        Global.mf.setJTextArea1(dateFormat.format(date)+" - "+sender+": "+message);
//    }
    
    public void refreshUsersList() throws RemoteException {
        ArrayList<String> clients = this.routerStub.getConnectedClients();
        ArrayList<String> directClients = this.routerStub.getDirectlyConnectedClients();
       
            Global.mf.setList1(clients, directClients);
        
    }
    
    
    
}
