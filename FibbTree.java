public class FibbTree {

    Node maxPtr;

    FibbTree() {
        maxPtr = new Node(-9999);
    }

    public void insert(Node n) {

        // Node n = new Node(data);

        if (maxPtr.getRightSib() == null) { //if the tree is empty
            maxPtr = n;
            maxPtr.setLeftSib(maxPtr); //set both siblings to itself
            maxPtr.setRightSib(maxPtr);
        } else { //tree not empty
            n.setLeftSib(maxPtr); //insert to the right of the minptr
            n.setRightSib(maxPtr.getRightSib()); //set new node right sib to minptr old right sib
            maxPtr.getRightSib().setLeftSib(n); //connect minptr's old right to new node 
            maxPtr.setRightSib(n); //set new node as right sibling of minptr
        }

        //if the new node is larger, it is now maxptr
        if (maxPtr.getData() < n.getData()) { 
            maxPtr = n;
        }
    }

    //needs edits 
    public void increaseKey(Node n, int scalar){
       
        int temp = n.getData(); //current data
        n.setData(temp + scalar); //increase data by scalar

        if (n.getParent() != null){ //if parent not null need to check parent data
            if (n.getParent().getData() < n.getData()){ //if new node is larger
                //remove subtree from the parent
                //if node has a sibling, set parent.child to equal a sibling (just in case, since its arbitrary)
                //otherwise set parent.child = null
                //if parent . childcut is true need to do the same thing - while loop?
                //else set parent. childcut  true
                
                Node curr = n;

                while((curr.getParent() != null) && (curr.getParent().getChildCut() == true)){
                    //perform child cut 
                }

                if(n.getRightSib() != null){
                    n.getParent().setChild(n.getRightSib()); //setting parent's child to another node just in case
                } else {
                    n.getParent().setChild(null); //n was parent's only child 
                }

                insert(n); //insert n into fibb tree




            }
        }




    }
    //THIS DOES NOT ACCOUNT FOR CHEKCING A NEWLY BUILD TREE - FIX 

    // public void pairwiseCombine() {
    //     Node curr = minPtr;
    //     Node treeTable[] = new Node[5];
    //     int tempdegree;
    //     //boolean quit = false;

    //     do {

    //         tempdegree = curr.getDegree();
    //         if (treeTable[tempdegree] == null) { // if the degree does not already exist in the tree
    //             treeTable[tempdegree] = curr; // set it to equal the current node with the degree
    //             curr = minPtr.getRightSib(); // next

    //             // if (curr.equals(minPtr)) { // if next is the minPtr, we've gone full circle
    //             //     quit = true;
    //             // }
    //         } else { // this is where the pairwise combine happens
    //                  // remove the node from the tree by changing the L and R siblings
    //                  // set L.rightsib = me.rightsib
    //             Node tempNode = treeTable[tempdegree]; // figure out if this causes temporary changes
    //             curr.getLeftSib().setRightSib(curr.getRightSib());
    //             // set R.leftsib = me.leftsib
    //             curr.getRightSib().setLeftSib(curr.getLeftSib());

    //             if (tempNode.getChild() == null) { // the tree you're merging with does not have any children
    //                 tempNode.setChild(curr);
    //                 curr.setParent(tempNode);
    //                 tempNode.setDegree(tempNode.getDegree()+1); //increase degree when adding a new child

    //             } else { // the node already has a child, insert it into the child linked list
    //                 curr.setParent(tempNode);
    //                 curr.setLeftSib(tempNode.getChild());
    //                 curr.setRightSib(tempNode.getChild().getRightSib());
    //                 tempNode.getChild().getRightSib().setLeftSib(curr);
    //                 tempNode.getChild().setRightSib(curr);
    //                 tempNode.setDegree(tempNode.getDegree()+1); //increase degree when adding a new child
    //             }

    //             treeTable[tempdegree] = null; //free the space in the tree table
    //         }

    //         curr = curr.getRightSib(); //THIS IS NOT THE RIGHT POINTER

    //     } while (!curr.equals(minPtr));

    // }

    @Override
    public String toString() {

        Node ptr = maxPtr;

        String print = "minptr: " + String.valueOf(maxPtr.getData()) + "\n";

        while (!ptr.getRightSib().equals(maxPtr)) {
            print += String.valueOf(ptr.getRightSib().getData());
            print += " ";
            ptr = ptr.getRightSib();
        }

        return print;
    }

}