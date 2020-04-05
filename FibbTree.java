import java.util.*;

public class FibbTree {

    Node maxPtr;

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

    public void meld(Node a, Node b) {

        // FibbTree tempSib = new FibbTree();
        // tempSib.setMaxPtr(b);

        // Node temp = a.getLeftSib();
        if (a.getLeftSib().equals(a)) {
            a.setLeftSib(b);
            a.setRightSib(b.getRightSib());
            b.getRightSib().setLeftSib(a);
            b.setRightSib(a);
        } else if (b.getLeftSib().equals(b)) {
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

        // reset the max ptr
        Node curr = a;

        do {
            if (curr.getData() > maxPtr.getData()) {
                maxPtr = curr;
            }
            curr = curr.getRightSib();
        } while (!curr.equals(a));

    }

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

            meld(maxPtr, tempChild);
        }

        // combine top level list and children list using meld

    }

    // needs edits
    public void increaseKey(Node n, int scalar) {

        int temp = n.getData(); // current data
        n.setData(temp + scalar); // increase data by scalar

        if (n.getParent() != null) { // if parent not null need to check parent data
            if (n.getParent().getData() < n.getData()) { // if new node is larger
                // remove subtree from the parent
                // if node has a sibling, set parent.child to equal a sibling (just in case,
                // since its arbitrary)
                // otherwise set parent.child = null
                // if parent . childcut is true need to do the same thing - while loop?
                // else set parent. childcut true

                Node curr = n;

                while ((curr.getParent() != null) && (curr.getParent().getChildCut() == true)) {
                    // perform child cut
                }

                if (n.getRightSib() != null) {
                    n.getParent().setChild(n.getRightSib()); // setting parent's child to another node just in case
                } else {
                    n.getParent().setChild(null); // n was parent's only child
                }

                insert(n); // insert n into fibb tree
            }
        }
    }

    public Node removeMax() {

        Node max = maxPtr;
        Node maxRight = maxPtr.getRightSib();
        Node maxLeft = maxPtr.getLeftSib();
        Node child;

        // removing maxPtr from the top list
        maxRight.setLeftSib(maxLeft);
        maxLeft.setRightSib(maxRight);

        // maxPtr.setLeftSib(null);
        // maxPtr.setRightSib(null);

        // if maxPtr was the only one in the list
        if (maxLeft.equals(maxPtr)) {
            if (maxPtr.getChild() != null) { // if max had kids
                maxPtr = maxPtr.getChild();

            } else { // empty list
                maxPtr = null;
            }
        } else { // maxPtr was not the only node in the top level list
            if ((maxPtr.getChild() != null)) { // if max had kids
                child = maxPtr.getChild();
                child.setParent(null);
                maxPtr = maxRight; // temp max ptr
                meld(maxPtr, child); // should create a new max ptr?
            } else {
                maxPtr = maxRight; // not true, will need to be fixed with loop
            }
        }

        Node start = maxPtr;
        Node curr = start;
        int maxNum = 0;

        // check for new maxPtr
        do {
            if (curr.getData() > maxNum) {
                maxNum = curr.getData();
                maxPtr = curr;
            }
            curr = curr.getRightSib();
        } while (!curr.equals(start));

        // pairwiseCombine(maxLeft);
        // System.out.println(toString());

        // maxPtr = maxRight; // new arbitrary max ptr.

        if (maxPtr != null) {
            pairwiseCombine();
        }
        return max;

    }

    public void pairwiseCombine() {

        Hashtable<Integer, Node> treeTable = new Hashtable<Integer, Node>();

        Node oldPtr = maxPtr;
        Node temp;
        // Node newPtr;
        Node curr;
        Node child;
        curr = oldPtr;
        boolean EOL = false;
        boolean recheck = true;

        while (!EOL || recheck) {

            // curr is out of top level list
            oldPtr = oldPtr.getRightSib();

            if (oldPtr.equals(curr)) { // right sib was itself, we're on the last curr
                // oldPtr = null; // done
                EOL = true;
            }

            curr.getRightSib().setLeftSib(curr.getLeftSib());
            curr.getLeftSib().setRightSib(curr.getRightSib());
            curr.setLeftSib(curr);
            curr.setRightSib(curr);

            if (treeTable.containsKey(curr.getDegree())) { // if an entry already exists in the tt
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
                    } else {
                        temp.setChild(curr);
                    }
                    curr.setParent(temp);
                    temp.increaseDegree();
                    curr = temp; // final tree stored in curr
                } else {
                    if (curr.getChild() != null) {
                        child = curr.getChild();
                        temp.setLeftSib(child);
                        temp.setRightSib(child.getRightSib());
                        child.getRightSib().setLeftSib(temp);
                        child.setRightSib(temp);

                    } else {
                        curr.setChild(temp);
                    }
                    temp.setParent(curr);
                    curr.increaseDegree();
                }

                if (!treeTable.containsKey(curr.getDegree())) {
                    treeTable.put(curr.getDegree(), curr);
                    recheck = false;
                    curr = oldPtr;
                } else {
                    recheck = true;
                }

            } else { // does not exist in tree

                // put in tree table
                treeTable.put(curr.getDegree(), curr);
                recheck = false;
                // remove the node from the old heap
                // oldPtr = oldPtr.getRightSib(); // move old heap ptr to next
                curr = oldPtr; // next
            }
        }

        // tree table complete
        // Set keySet = treeTable.keySet();

        // Enumeration keys = treeTable.keys();
        Set<Integer> keySet = treeTable.keySet();
        // maxPtr.reset(); // totally new fibb heap

        // maxPtr = treeTable.get(keySet.get(0)); //arbitrary max ptr
        // newPtr = treeTable.get(keySet.get(1));
        // newPtr = maxPtr;
        // System.out.println(treeTable.get(0).getData());
        for (int key : keySet) {
            // System.out.print(key + " ");
            // System.out.println(treeTable.get(key).getData());
            insert(treeTable.get(key));
        }

        // System.out.println(toString());
        // for (int i = 0; i < keys.size(); i++){
        // insert(treeTable.get(keys.get(i)));
        // }

    }

    // uhhhhh please do an entire write of what this should be
    // node A is the one after the minptr since it was deleted
    public void pairwiseCombine(Node a) {

        Hashtable<Integer, Node> treeTable = new Hashtable<Integer, Node>();
        int deg = 0;

        // put the first node into the tree table
        treeTable.put(a.getDegree(), a);
        Node curr = a.getRightSib();
        Node check = curr; // node to check if we are done pairwise combining.

        // need to keep combining until there are no tree table violations
        do {
            if (treeTable.containsKey(curr.getDegree())) {
                int tempDegree = curr.getDegree(); // used to later remove the hashmap entry.
                Node temp = treeTable.get(curr.getDegree());
                // do the pairwise combine

                // compare curr.data and the tree table node.data
                if (curr.getData() > temp.getData()) { // curr data is larger
                    temp.getRightSib().setLeftSib(curr);
                    curr.setRightSib(temp.getRightSib());
                    temp.setLeftSib(null);
                    temp.setRightSib(null);
                    if (curr.getChild() != null) { // if curr already has a child, needs to be inserted
                        temp.setLeftSib(curr.getChild()); // insert node to right of child ptr
                        temp.setRightSib(curr.getChild().getRightSib()); // insert node to left of child's right ptr
                        curr.getChild().getRightSib().setLeftSib(temp); // change child's right ptr's left to be temp
                        curr.getChild().setRightSib(temp); // change child's right to be temp
                    } else { // doesnt have a child
                        curr.setChild(temp);
                        temp.setLeftSib(temp);
                        temp.setRightSib(temp);
                    }
                    deg = curr.getDegree();
                    deg++;
                    curr.setDegree(deg);
                    check = curr; // just changed this one
                } else { // temp data is larger
                    curr.getRightSib().setLeftSib(temp);
                    temp.setRightSib(curr.getRightSib());
                    curr.setLeftSib(null);
                    curr.setRightSib(null);
                    if (temp.getChild() != null) { // if curr already has a child, needs to be inserted
                        curr.setLeftSib(temp.getChild()); // insert node to right of child ptr
                        curr.setRightSib(temp.getChild().getRightSib()); // insert node to left of child's right ptr
                        temp.getChild().getRightSib().setLeftSib(curr); // change child's right ptr's left to be temp
                        temp.getChild().setRightSib(curr); // change child's right to be temp
                    } else { // doesnt have a child
                        temp.setChild(curr);
                        curr.setLeftSib(curr);
                        curr.setRightSib(curr);
                    }
                    deg = temp.getDegree();
                    deg++;
                    temp.setDegree(deg);
                    curr = temp; // now one tree, curr.
                    check = curr; // just changed this one
                }

                // remove the current hashmap entry
                treeTable.remove(tempDegree);
                treeTable.put(curr.getData(), curr);
                // place new hashmap entry
                temp = curr.getRightSib(); // keep curr the same, now move to the next one

            } else { // degree does not already exist - keep moving forward.
                treeTable.put(curr.getDegree(), curr);
                curr = curr.getRightSib();
                // temp = curr.getRightSib();
            }

        } while (!curr.getRightSib().equals(check));

        // now we have a new top level list - time to check the maxptr.

        Node start = curr;
        int max = 0;
        do {
            if (curr.getData() > max) {
                maxPtr = curr;
                max = curr.getData();
            }
            curr = curr.getRightSib();
        } while (!curr.equals(start));

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

    @Override
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