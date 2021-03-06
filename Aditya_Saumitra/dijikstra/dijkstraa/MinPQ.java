/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 *
 * @author sam
 */
public class MinPQ {
    
     /*I have basically implemented a Min Priority queue using an array in which each opeartion takes O(n) time*/   
    //*******************************************************
    /*the class for node element of the array contains the ID of the node its cost and methods to return
    ID and cost basically the ID will be VertexID and cost its recorded distance from the source node.*/
    public class Node
    {
        int ID;
        Double cost;
        
        public Node(int ID,Double cost)
        {
            this.ID = ID;
            this.cost = cost;
        }
        
        public int retID()
        {
            return this.ID;
        }
        
        public Double retCost()
        {
            return this.cost;
        }
    }
    //*********************************************
    //All the vertex nodes will be stored in an array.
    Node[]container;
    //we record the maximum size of the array which is passed at the time of construction.
    int maxsize;
    //records the current number of nodes in the array.
    int numnodes;
    
    public MinPQ(int maxsize)
    {
        this.maxsize = maxsize;
        numnodes = 0;
        container = new Node[maxsize];
       
    }
    
    //insert a new vertex into the array given its ID and distance from the source.
    public void insert(int ID, Double cost)
    {
        //create a new node to represent the vertex.
        Node temp = new Node(ID,cost);
        int j;
        //if the array is initially empty the inserted element will be the first element.
        if (numnodes == 0)
            container[numnodes++] = temp;
        else
            //else we travere the array in backward fashion till we get a node whose weight is less than weight of new node
            //we also shift each node to next position to its right to make room for the newly created node.
        {
            for (j = numnodes-1;j>=0;j--)
            {
                if (temp.retCost() < container[j].retCost())
                    container[j+1] = container[j];
                else
                    break;
            }
            //now we have the position we insert the newly created node and increment the number of newly created nodes by 1.
            container[j+1] = temp;
            numnodes++;
           
        }
       
    }
    /*this straight-forward tells that the array is empty or not. */
    public boolean isEmpty()
    {
        return numnodes==0;
    }
    
    /*This method scans the array for a node with given ID if its found it returns true 
    else returns false.*/
    public boolean contains(int ID)
    {
        boolean ret = false;
        
        for(int i = 0;i<numnodes;i++)
        {
            if (container[i].retID()==ID)
            {
                ret = true;
                break;
            }
        }
        return ret;
    }
    /*delMin operation takes O(n) time , first node in the array is the minimum node.
    after dleting that node we have to shift all nodes one position left.*/
    public int delMin()
    {
        int retval = container[0].retID();
        for(int i = 0;i<numnodes-1;i++)
            container[i] = container[i+1];
        container[numnodes-1]=null;
        numnodes--;
        return retval;
    }
    
    /*in this operation 
    1. first we find the node whose weight has to be decreased.
    2. delete it from the array, shift all element one position left.
    3. re-insert the deleted node with the new key. */
    public void decreaseKey(int ID, Double cost)
    {
        //fisrt step find the node .
        int pos = -1;//position of node to remove.
        for (int i=0;i<numnodes;i++)
        {
            if(container[i].retID()==ID)
            {
                pos = i;
                break;
            }
        }
        int init = pos;
        while(init < numnodes)
        {
            container[init]=container[init+1];
            init++;
        }
        container[numnodes] = null;
        numnodes--;
        insert(ID,cost);
    }
    
   /*display the queue by travering the array*/ 
    public void display()
    {
        System.out.println("The contents of the queue are as below.");
        for (int i = 0;i<numnodes;i++)
            System.out.println(container[i].retID() + "," + container[i].retCost());
    }
    
    
}
