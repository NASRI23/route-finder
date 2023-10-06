public class Node {
    // create private variables for the Node.
    private int id;
    private boolean mark;

    // Node constructor that sets the ID of the Node.
    public Node(int id) {
        this.id = id;
    }

    // a method that takes a boolean value and mark the code either true or false.
    public void markNode(boolean mark) {
        this.mark = mark;
    }

    // return Mark, returns the mark of the node, if not set it will return false.
    public boolean getMark() {
        return this.mark;
    }

    // return the ID of the Node.
    public int getId() {
        return this.id;
    }
}
