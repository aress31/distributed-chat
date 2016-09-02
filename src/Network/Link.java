/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Network;

import java.io.Serializable;

/**
 *
 * @author Ezzat
 */
public class Link implements Serializable {
    
    private String idL; 
    private transient Router source;
    private final String nameSource;
    private String nameDest;
    private transient RouterInterface interfaceDest;
    private int weight; 
    private String intf;
    

    public Link(String idL, Router source, String nameDest, int weight, String intf) {
        this.idL = idL;
        this.source = source;
        this.nameSource = this.source.getNameR();
        this.nameDest = nameDest;
        this.weight = weight;
        this.intf = intf;
        this.interfaceDest = null;
    }

    public String getIdL() {
        return idL;
    }

    public String getNameSource() {
        return nameSource;
    }

    public Router getSource() {
        return source;
    }

    public String getNameDest() {
        return nameDest;
    }

    public RouterInterface getInterfaceDest() {
        return interfaceDest;
    }

    public int getWeight() {
        return weight;
    }

    public String getIntf() {
        return intf;
    }

    public void setIdL(String idL) {
        this.idL = idL;
    }

    public void setSource(Router source) {
        this.source = source;
    }

    public void setNameDest(String nameDest) {
        this.nameDest = nameDest;
    }

    public void setInterfaceDest(RouterInterface interfaceDest) {
        this.interfaceDest = interfaceDest;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    
    
}
