/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calcium2015;

import java.util.ArrayList;

/**
 *
 * @author Владимир
 */
/*
    This class represents an intersection
*/
public class MapNode {
    public int Id;         
    public ArrayList<Integer> Connections;
    
    public MapNode(){
        Connections = new ArrayList<>();
    }

    public void addEdge(int neighboorId) {
        if (Connections.contains(neighboorId) == false){
            Connections.add(neighboorId);
        }        
    }    
}