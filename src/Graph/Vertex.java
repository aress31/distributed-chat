/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @authors Ezzat, Alexandre & Simon
 */

public class Vertex implements Serializable {

    //private String id;
    private String name;
    
    
    

    public Vertex(String name) {
        this.name = name;
    }

    

    public String getName() {
        return name;
    }
    
    
    
} 