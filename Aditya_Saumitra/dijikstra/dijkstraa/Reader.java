/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Reader {
    
   
    
    	public Graph readf (String file ) throws FileNotFoundException
        {
            Scanner scan = new Scanner(new File(file));
            //ignore first line
            scan.nextLine();
            /* The first two strings are for number of vertices and edges */
            int numNodes = scan.nextInt();
            int numEdges = scan.nextInt();
            Graph G = new Graph(numNodes,numEdges);
            /* Now reading the next lines to get the edges and their respective costs */
            while(scan.hasNextInt())
            {  
                /*  create vertex and edge objects and add them to the Adjacency graph. */
                Vertex y = new Vertex(scan.nextInt() );
                Vertex z = new Vertex(scan.nextInt() );
                /* Adding new edge with vertex1, vertex 2 and its cost */
                Edge e = new Edge(y, z, scan.nextInt() );
                G.AddVertexS(y);
                G.AddVertexS(z);
                G.AddEdgeS(e);
            }
            /* close the input stream */
            scan.close();
            
            return G;
                
        }
    
}
