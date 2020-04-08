import java.util.*;

public class FibbTree {

    private Node maxPtr;

    FibbTree() {
        maxPtr = new Node(-9999);
    }

    public void insert(Node n) {

        if (maxPtr.getRightSib() == null) { // if the tree is empty
            maxPtr = n; // new node is maxptr
            maxPtr.setLeftSib(maxPtr); // set both siblings to itself
            maxPtr.setRightSib(maxPtr);
        } else if (maxPtr.getLeftSib().equals(maxPtr) && maxPtr.getRightSib().equals(maxPtr)) { // only maxptr
            // this else if may not be necessary.
            // fibb heap with only maxptr and one node is a cycle
            n.setLeftSib(maxPtr);
            n.setRightSib(maxPtr);
            maxPtr.setRightSib(n);
            maxPtr.setLeftSib(n);
        } else { // tree has at least 2 nodes
            n.setLeftSib(maxPtr); // insert to the right of the minptr
            n.setRightSib(maxPtr.getRightSib()); // set new node right sib to minptr old right sib
            maxPtr.getRightSib().setLeftSib(n); // connect minptr's old right to new node
            maxPtr.setRightSib(n); // set new node as right sibling of minptr
        }

        // if the new node is larger, it is now maxptr
        if (maxPtr.getData() < n.getData()) {
            maxPtr = n;
        }
    }

    /**
     * @param a the first node to meld
     * @param b the second node to meld
     */
    public void meld(Node a, Node b) {

        if (a.getLeftSib().equals(a)) { // if a does does not have any siblings
            a.setLeftSib(b);
            a.setRightSib(b.getRightSib());
            b.getRightSib().setLeftSib(a);
            b.setRightSib(a);
        } else if (b.getLeftSib().equals(b)) { // if a does does not have any siblings
            b.setLeftSib(a);
            b.setRightSib(a.getRightSib());
            a.getRightSib().setLeftSib(b);
            a.setRightSib(b);
        } else {
            a.getLeftSib().setLeftSib(b.getRightSib());
            b.getRightSib().setRightSib(a.getLeftSib());
            a.setRightSib(b);
            b.setLeftSib(a);

        }

        // find the new maxPtr
        Node curr = a;
        do {
            if (curr.getData() > maxPtr.getData()) {
                maxPtr = curr;
            }
            curr = curr.getRightSib();
        } while (!curr.equals(a));

    }

    /**
     * @param theNode the node to remove
     */
    public void remove(Node theNode) {

        // remove theNode from its doubly linked sibling list
        theNode.getLeftSib().setRightSib(theNode.getRightSib());
        theNode.getRightSib().setLeftSib(theNode.getLeftSib());

        // change parent's child pointer if necessary
        if (theNode.getParent() != null) {
            if (theNode.getParent().getChild().equals(theNode)) {
                theNode.getParent().setChild(theNode.getRightSib());
            }
        }

        Node tempChild = theNode.getChild();

        // set parent field of theNode's children to null
        if (theNode.getChild() != null) {

            Node curr = tempChild;
            // traverse through child linked list and set all to null
            do {
                curr.setParent(null);
                curr = tempChild.getRightSib();
            } while (!curr.equals(tempChild));

            meld(maxPtr, tempChild); // combine top level list and children list using meld
        }

    }

    /**
     * @param n      the node to change value of
     * @param scalar how much to increase the data of node n
     */
    public void increaseKey(Node n, int scalar) {

        int temp = n.getData(); // current data
        n.setData(temp + scalar); // increase data by scalar

        Node curr = n;

        boolean recheck = true;
        Node p;

        // cascading cut
        if (curr.getParent() != null) {
            if (curr.getData() > curr.getParent().getData()) {
                while (recheck) {
                    if (curr.getParent() != null) { // already at the top level of the list
                        p = curr.getParent();

                        if (!curr.getLeftSib().equals(curr)) { // node has siblings
                            curr.getRightSib().setLeftSib(curr.getLeftSib());
                            curr.getLeftSib().setRightSib(curr.getRightSib());
                            p.setChild(curr.getLeftSib()); // just in case
                        } else {
                            p.setChild(null); // only child gone
                        }

                        p.decreaseDegree();
                        curr.setParent(null);
                        insert(curr);

                        curr = p;

                        // checking parent, loop will repeat if parent childcut is true
                        if (curr.getChildCut()) {
                            recheck = true;
                        } else {
                            curr.setChildCut(true);
                            recheck = false;
                        }
                    }
                }
            }
        }

        // we reached the top level
        if ((curr.getParent() == null) && (curr.getData() > maxPtr.getData())) {
            maxPtr = curr;
        }

    }

    /**
     * @return the node with the highest data value
     */
    public Node removeMax() {

        Node max = maxPtr;
        Node maxRight = maxPtr.getRightSib();
        Node maxLeft = maxPtr.getLeftSib();
        Node child;
        // Node first;

        // removing maxPtr from the top level list
        maxRight.setLeftSib(maxLeft);
        maxLeft.setRightSib(maxRight);
        maxPtr.setLeftSib(maxPtr);
        maxPtr.setRightSib(maxPtr);

        // if maxPtr was the only one in the list
        if (maxLeft.equals(maxPtr)) {

            if (maxPtr.getChild() != null) { // if max had kids
                maxPtr = maxPtr.getChild();

            } else { // empty list

                maxPtr = new Node(-9999);
                // maxPtr = null;
            }
        } else { // maxPtr was not the only node in the top level list

            if ((maxPtr.getChild() != null)) { // if max had kids
                child = maxPtr.getChild();
                // child.setParent(null); //this is causing problems, see cascading cut?
                maxPtr = maxRight; // temp max ptr
                meld(maxPtr, child); // should create a new max ptr?
            } else {
                maxPtr = maxRight; // not true, will need to be fixed with loop
            }
        }

        Node start = maxPtr;
        Node curr = start;
        int maxNum = 0;

        // check for new maxPtr if not empty
        if (maxPtr != null) {
            do {
                if (curr.getData() > maxNum) {
                    maxNum = curr.getData();
                    maxPtr = curr;
                }
                curr = curr.getRightSib();
            } while (!curr.equals(start));
        } else { //empty tree
            maxPtr = new Node(-9999);
        }

        if (maxPtr != null) {
            pairwiseCombine(); //pairwise combine after removing max
        }

        return max;

    }

    /**
     * Performs a pairwise combine on existing fibb heap
     */
    public void pairwiseCombine() {

        Hashtable<Integer, Node> treeTable = new Hashtable<Integer, Node>();

        Node oldPtr = maxPtr;
        Node curr;
        curr = oldPtr;
        Node temp;
        Node child;
        boolean EOL = false; //to check end of original top level list
        boolean recheck = true; //to check whether a newly made tree needs to be rechecked

        while (!EOL || recheck) {

            // curr is out of top level list
            oldPtr = oldPtr.getRightSib();

            if (oldPtr.equals(curr)) { // right sib was itself, we're on the last curr
                EOL = true; //reached end of list
            }

            //removing curr from the top level list
            curr.getRightSib().setLeftSib(curr.getLeftSib());
            curr.getLeftSib().setRightSib(curr.getRightSib());
            curr.setLeftSib(curr);
            curr.setRightSib(curr);

            if (treeTable.containsKey(curr.getDegree())) { // if an entry already exists in the tree table
                // merge the existing tree and current ptr tree and put back in table

                // store existing node
                temp = treeTable.get(curr.getDegree());
                treeTable.remove(curr.getDegree());

                // merge trees
                if (temp.getData() > curr.getData()) {
                    if (temp.getChild() != null) { // insert curr as a child of temp
                        child = temp.getChild();
                        curr.setLeftSib(child);
                        curr.setRightSib(child.getRightSib());
                        child.getRightSib().setLeftSib(curr);
                        child.setRightSib(curr);
                    } else { //temp did not previously have a child 
                        temp.setChild(curr); 
                    }
                    curr.setParent(temp);
                    curr.setChildCut(false);
                    temp.increaseDegree();
                    curr = temp; // final tree stored in curr
                } else {
                    if (curr.getChild() != null) { //insert temp as a child of curr
                        child = curr.getChild();
                        temp.setLeftSib(child);
                        temp.setRightSib(child.getRightSib());
                        child.getRightSib().setLeftSib(temp);
                        child.setRightSib(temp);

                    } else {
                        curr.setChild(temp);
                    }
                    temp.setParent(curr);
                    temp.setChildCut(false);
                    curr.increaseDegree();
                }

                if (!treeTable.containsKey(curr.getDegree())) {
                    treeTable.put(curr.getDegree(), curr);
                    recheck = false;
                    curr = oldPtr;
                    // if (!EOL){
                    // curr = oldPtr;
                    // }

                } else {
                    recheck = true;
                }

            } else { // does not exist in tree

                // put in tree table
                treeTable.put(curr.getDegree(), curr);
                recheck = false;
                // remove the node from the old heap
                curr = oldPtr; // next
            }
        }

        Set<Integer> keySet = treeTable.keySet();

        for (int key : keySet) {
            insert(treeTable.get(key));
        }

    }

    /**
     * @param maxPtr the maxPtr to set
     */
    public void setMaxPtr(Node maxPtr) {
        this.maxPtr = maxPtr;
    }

    /**
     * @return the maxPtr
     */
    public Node getMaxPtr() {
        return maxPtr;
    }

    @Override //for testing purposes
    public String toString() {

        Node ptr = maxPtr;

        String print = "max: " + String.valueOf(maxPtr.getData()) + "\n";

        do {
            print += ptr.getData();
            print += " -> ";
            ptr = ptr.getRightSib();
        } while (!ptr.equals(maxPtr));

        print += ptr;

        return print;
    }

}