/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.*;
/*each vertex has an ID and a list of edges from it.
very straight forward class all methods are simple*/
public class Vertex {
    
    private int ID;
    ArrayList<Edge> myEdges;
    
    public Vertex (int ID)
    {
        this.ID = ID;
        myEdges = new ArrayList<Edge>();
    }
    
    public int retID()
    {
        return this.ID;
    }
    //method to check if two vertices are equal by first checking the type
    //than by matching the ID.
    public boolean equals(Object V )
    {  if (V != null && V instanceof Vertex)
    	{return this.retID() == ((Vertex) V).retID();}
    	return false;
    }
    
    
}
