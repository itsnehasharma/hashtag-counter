public class FibbTree {

    Node minPtr;

    FibbTree() {
        minPtr = new Node(-9999);
    }

    public void insert(Node n) {

        // Node n = new Node(data);

        if (minPtr.getRightSib() == null) { //if the tree is empty
            minPtr = n;
            minPtr.setLeftSib(minPtr); //set both siblings to itself
            minPtr.setRightSib(minPtr);
        } else { //tree not empty
            n.setLeftSib(minPtr); //insert to the right of the minptr
            n.setRightSib(minPtr.getRightSib()); //set new node right sib to minptr old right sib
            minPtr.getRightSib().setLeftSib(n); //connect minptr's old right to new node 
            minPtr.setRightSib(n); //set new node as right sibling of minptr
        }

        //if the new node is smaller, it is now minptr
        if (minPtr.getData() > n.getData()) { 
            minPtr = n;
        }
    }


    //THIS DOES NOT ACCOUNT FOR CHEKCING A NEWLY BUILD TREE - FIX 

    public void pairwiseCombine() {
        Node curr = minPtr;
        Node treeTable[] = new Node[5];
        int tempdegree;
        //boolean quit = false;

        do {

            tempdegree = curr.getDegree();
            if (treeTable[tempdegree] == null) { // if the degree does not already exist in the tree
                treeTable[tempdegree] = curr; // set it to equal the current node with the degree
                curr = minPtr.getRightSib(); // next

                // if (curr.equals(minPtr)) { // if next is the minPtr, we've gone full circle
                //     quit = true;
                // }
            } else { // this is where the pairwise combine happens
                     // remove the node from the tree by changing the L and R siblings
                     // set L.rightsib = me.rightsib
                Node tempNode = treeTable[tempdegree]; // figure out if this causes temporary changes
                curr.getLeftSib().setRightSib(curr.getRightSib());
                // set R.leftsib = me.leftsib
                curr.getRightSib().setLeftSib(curr.getLeftSib());

                if (tempNode.getChild() == null) { // the tree you're merging with does not have any children
                    tempNode.setChild(curr);
                    curr.setParent(tempNode);
                    tempNode.setDegree(tempNode.getDegree()+1); //increase degree when adding a new child

                } else { // the node already has a child, insert it into the child linked list
                    curr.setParent(tempNode);
                    curr.setLeftSib(tempNode.getChild());
                    curr.setRightSib(tempNode.getChild().getRightSib());
                    tempNode.getChild().getRightSib().setLeftSib(curr);
                    tempNode.getChild().setRightSib(curr);
                    tempNode.setDegree(tempNode.getDegree()+1); //increase degree when adding a new child
                }

                treeTable[tempdegree] = null; //free the space in the tree table
            }

            curr = curr.getRightSib(); //THIS IS NOT THE RIGHT POINTER

        } while (!curr.equals(minPtr));

    }

    @Override
    public String toString() {

        Node ptr = minPtr;

        String print = "minptr: " + String.valueOf(minPtr.getData()) + "\n";

        while (!ptr.getRightSib().equals(minPtr)) {
            print += String.valueOf(ptr.getRightSib().getData());
            print += " ";
            ptr = ptr.getRightSib();
        }

        return print;
    }

}