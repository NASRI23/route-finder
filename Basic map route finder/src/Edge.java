public class Edge {

    // private variables for Edge class.
    private Node fEndpoint, sEndpoint;
    private String type;

    // a constructor that takes two nodes, and a type and initialize an edge to be
    // the edge between the two node.
    public Edge(Node u, Node v, String type) {
        fEndpoint = u;
        sEndpoint = v;
        this.type = type;
    }

    // returns the first node of the edge.
    public Node firstNode() {
        return fEndpoint;
    }

    // returns the second node of the edge.
    public Node secondNode() {
        return sEndpoint;
    }

    // return the type of the edge.
    public String getType() {
        return type;
    }
}
