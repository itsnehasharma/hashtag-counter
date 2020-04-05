public class Node{
   
    private int degree;
    private Node child;
    private Node parent;
    private int data;
    private Node leftSib;
    private Node rightSib;
    private boolean childCut;
   
    Node(int data){

        this.degree = 0;
        this.child = null;
        this.parent = null;
        this.data = data;
        this.leftSib = null;
        this.rightSib = null;
        this.childCut = false;
    }

    /**
     * @return the degree
     */
    public int getDegree() {
        return degree;
    }

    /**
     * @param degree the degree to set
     */
    public void setDegree(int degree) {
        this.degree = degree;
    }

    public void increaseDegree(){
        this.degree = this.degree + 1;
    }


    /**
     * @return the child
     */
    public Node getChild() {
        return child;
    }

    /**
     * @param child the child to set
     */
    public void setChild(Node child) {
        this.child = child;
    }

    /**
     * @return the parent
     */
    public Node getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(Node parent) {
        this.parent = parent;
    }

      /**
     * @return the data
     */
    public int getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(int data) {
        this.data = data;
    }


    /**
     * @return the rightSib
     */
    public Node getRightSib() {
        return rightSib;
    }
    
    /**
     * @param rightSib the rightSib to set
     */
    public void setRightSib(Node rightSib) {
        this.rightSib = rightSib;
    }

     /**
     * @return the leftSib
     */
    public Node getLeftSib() {
        return leftSib;
    }

    /**
     * @param leftSib the leftSib to set
     */
    public void setLeftSib(Node leftSib) {
        this.leftSib = leftSib;
    }

  
    /**
     * @return the childCut
     */
    public boolean getChildCut(){
        return childCut;
    }

    /**
     * @param childCut the childCut to set
     */
    public void setChildCut(boolean childCut) {
        this.childCut = childCut;
    }


    @Override
    public String toString() {
        String str = String.valueOf(this.data);

        return str;

    }

    public void reset(){
        // this.degree = 0;
        // this.child = null;
        this.parent = null;
        this.data = -9999;
        this.leftSib = null;
        this.rightSib = null;
        // this.childCut = false;
    }
    
    
  


}