/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    
    public void displayMsg(String sender, String msg, int type) throws RemoteException;
    public void refreshUsersList() throws RemoteException;
    //public void displayPrivateMsg(String sender, String message) throws RemoteException;
    
}
