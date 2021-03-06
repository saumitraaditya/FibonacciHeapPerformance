/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.util.*;

public class FibHeap3 
{
   //the first step is to create a class of elements of this heap.
    public  class Entry
    {
        private int degree = 0; //degree of the node.
        private boolean marked = false;//marked or unmarked for cascading cut.
        private Entry next;//next node
        private Entry prev;//previous node
        private Entry parent;//paremnt node
        private Entry child;//child node
        private int key;//key of the node
        private double value;//value of the node
        //constructor for the node.
        public Entry(int key,Double value)
        {
            this.key = key;
            this.value = value;
            next = this;
            prev = this;
        }
        //method to return key of the node.
        public int retKey()
        {
            return key;
        }
        //method to get value of the node.
        public Double retValue()
        {
            return value;
        }
        
    }
    
    //refrence to the minimum node of the heap.
    private Entry min = null;
    //size of the heap which is initially 0.
    private int size = 0;
    /*Max size of the heap, ie maximum number of entries in the heap*/
    private int Maxsize = 0;
    /*ContainsCheck Array-> have a Array which stores either '0's or '1's corresponding to each entry in the heap, for example
    if the Maxsize of the heap is 10, this array will have 10 elements with 0s and 1s which indicate that the heap at present
    does not or does contains the given entry respectively.*/
    private int[] ContainsCheck;
    /*Array of entries will be used in decreaseKey*/
    private Entry[] EntryArray;
    //Constructor for heap that tells the max size that the heap can ever have which is equal to the number of verices in the graph.
    public  FibHeap3(int Maxsize)
    {
        this.Maxsize = Maxsize;
        ContainsCheck = new int[Maxsize];
        EntryArray = new Entry[Maxsize];
    }
    
    /*method that returns true if a entry with given ID is present in the heap by checking the ContainsCheck Array*/
    public boolean contains(int key)
    {
        return ContainsCheck[key]==1;
    }
    
    //method to insert a node into the heap given its key and value.
    public void insert (int key, Double value)
    {
        //create a new entry element.
        Entry entry = new Entry(key, value);
        //put this singleton node into the top level list.
        min = linkLists(min,entry); 
        //increment the size of heap
        size++;
         /*Add the newly created Entry into EntryArray*/
        EntryArray[key] = entry;
        
        /*set entry corresponding this value to 1 in the Contains Array*/
        ContainsCheck[key] = 1;
        
    }
    //method to check if the heap is empty.
    public boolean isEmpty() 
    {
       return min == null;
    }
    //method that combines two circular linked lists into one at the top level.the parameters passed are references
    //to some node in the two circular lists.
    private Entry linkLists(Entry A, Entry B)
    {
         if (A == null && B == null) { // Both null, resulting list is null.
            return null;
        }
        else if (A != null && B == null) { // B is null, result is A.
            return A;
        }
        else if (A == null && B != null) { // A is null, result is B.
            return B;
        }
         else
        {
            //create a new entry to temporarily hold a node(node referes to reference of a circular list)
            //adjust the references to connect the two list.
            Entry temp = A.next;
            A.next = B.next;
            A.next.prev = A;
            B.next = temp;
            B.next.prev = B;
            //return reference to a node in the newly melded list.
            if (A.retValue() < B.retValue())
                return A;
            else 
                return B;
        }
    }
    
    //this method returns the key of the minimum value in the heap.
    public int delMin()
    {
        // Check if the heap is empty.
        if (isEmpty())
            throw new NoSuchElementException("Heap is empty.");
        //decrease size of the heap.
        size--;
        //get reference to the minimum node of the heap.
        Entry retval = min;
        //case.1 there is only one elemt in the heap.
         if (min.next == min) 
             min = null;
         //case.2 simple remove the node from the list, readjust the references.
         else
         {
             min.prev.next = min.next; //skip the node to be deleted.
             min.next.prev = min.prev;
             min = min.next;
         }
         //in this step we have to set the parent field of all the child 
         //nodes of the node to be deleted to null. All of those children will have to be 
         //inserted in the top level list.
         if (retval.child != null) 
         {
            //Keep track of the first visited node. 
            Entry first = retval.child;
            do {
                first.parent = null;
                //traverse the list until return back to first node.
                first = first.next;
            } while (first != retval.child);
        }
         //merge the child list of the node to be deleted into the top-level list.
         min = linkLists(min, retval.child);
         if (min == null) return retval.retKey();
         // our next step is to combine trees of same degree so that
         //their is only a unique tree of a given degree
         // to this end we keep a list in which entry i is either null or contains
         //refrence of the tree with degree 'i'.
         List<Entry> tableTrees = new ArrayList<Entry>();
         //we have to traverse this entire list, to ensure that we are not
         //visiting the same tree more than once we have to keep track of
         //the trees we have visited.
         List<Entry> visit = new ArrayList<Entry>();
         //populate the visit list by adding references of all the nodes at the top-level into the list.
         for (Entry curr = min; visit.isEmpty() || visit.get(0) != curr; curr = curr.next)
            visit.add(curr);
         //traverse this list and perform the combining steps.
         for (Entry curr: visit)
         {
            while (true)
            {
                //have to ensure that the list is large enought to accomodate all the trees in top-level.
                while (curr.degree>=tableTrees.size())
                    tableTrees.add(null);
                //if the array does not have a entry for this degree add it.
                if (tableTrees.get(curr.degree) == null) {
                    tableTrees.set(curr.degree, curr);
                    break;
                }
                //if there is some tree with this degree already present that means we have to do combine operation.
                Entry temp = tableTrees.get(curr.degree);
                tableTrees.set(curr.degree, null);
                //next we compare the root of the trees with same degree
                Entry small = (temp.value < curr.value)? temp : curr;
                Entry large = (temp.value < curr.value)? curr  : temp;
                //merge large into smalls chil list
                //remove the large from top-level list
                large.next.prev = large.prev;
                large.prev.next = large.next;
                //make large singleton
                large.next= large;
                large.prev= large;
                //make the large child of small
                small.child = linkLists(small.child,large);
                //set parent of large to small
                large.parent = small;
                //clear mark of large as it now can lose another child
                large.marked = false;
                //increase the degree of small as it has one more child
                small.degree++;
                //search if their is some other tree with same degree to continue merging.
                curr = small;
            }
            //readjust the reference to min , in case a smaller node is present
             if (curr.value <= min.value) min = curr;
         }
         //set the values of checker arrays accordingly.
         ContainsCheck[retval.retKey()]=0;
         EntryArray[retval.retKey()] = null;
         return retval.retKey();
        
    }
    
    //implements the cascading cut functionality, cuts a node from its parent
    //if the parent was marked than cuts it so and goes on 
    //repeating the process recursively until sees a unmarked node.
    private void cascadingCut(Entry entry)
    {
        entry.marked = false; //since the node was cut , unmark it.
        //check if the node has a parent if not nothing more to do since it is already in top level.
        if (entry.parent == null)
            return;
        //adjust the list references to remove the node
        if (entry.next != entry)
        {
            entry.next.prev = entry.prev;
            entry.prev.next = entry.next;
        }
        //if the removed node is the child reference of a upper level node
        //we have to shift that pointer to the sibling of removed node
        if (entry.parent.child == entry)
        {
            if (entry.next != entry)
            {
                entry.parent.child = entry.next;
            }
            else
            {
               entry.parent.child = null;
            }
        }
        //decrement the degree of the parent since it has lost one child.
        entry.parent.degree--;
        //put the removed node into top level list, before doing so make it singleton.
        entry.next = entry.prev = entry;
        min = linkLists(min,entry);
        //next check if the parent is marked if it is cut it too
        //else mark the parent
        if (entry.parent.marked)
            cascadingCut(entry.parent);
        else
            entry.parent.marked = true;
        //clear the parent reference of the entry as it now is in top-level.
        entry.parent = null;
    }
    
    
    //this method implements the decrease key operation, given the key and value of a node 
    //in the list.
     public void decreaseKey(int key, double new_value) 
     {
         //get the reference to the entry with given key.
         Entry entry = EntryArray[key];
         if (new_value > entry.retValue())
            throw new IllegalArgumentException("New value exceeds old.");
         //change the value of the node
         entry.value = new_value;
         //if the new value of the node is less than its parent perform Cascading cut.
         if(entry.parent != null && entry.value<=entry.parent.value)
             cascadingCut(entry);
         //if the new value of entry is less than value of min node point the min reference to entry
         if (entry.value<=min.value)
             min = entry;
         
     }
}
