import java.util.ArrayList;
import java.util.Iterator;

// class graph that implements GraphADY
public class Graph implements GraphADT {
    // a private ArrayList type Node, and a Private arraylist to store the graph
    // with all the nodes.
    private ArrayList<Node> theNodes;
    private ArrayList<ArrayList<Edge>> myGraph;

    // graph constructor, that takes an integer with the length of the list.
    public Graph(int n) {
        // initialize the list and the Node list.
        myGraph = new ArrayList<ArrayList<Edge>>(n);
        theNodes = new ArrayList<Node>();

        // for loop that adds a new node and sets the ID's to be 0,1,2,3.... the length
        // of the list (n).
        // ot also adds a new graph element with a list of edges inside it.
        for (int i = 0; i < n; i++) {
            theNodes.add(new Node(i));
            myGraph.add(i, new ArrayList<>());
        }
    }

    // getNode method that takes the ID, goes in the list and return the Node with
    // that ID.
    public Node getNode(int id) throws GraphException {
        // creates a variable to hold the value of the Node.
        Node n;
        // try statement that try to get the Node with the ID, if any error happens, it
        // means there's no node with that ID.
        // so it will catch the exception and will turn it into Graph exception.
        try {
            n = theNodes.get(id);
        } catch (Exception e) {
            throw new GraphException("doesn't exist");
        }
        // then it will return the value of N.
        return n;
    }

    // add Edge method that takes, two Nodes. and a string with the type,
    // it create a new String then add it to the list on both, the first and second
    // Nodes.
    public void addEdge(Node nodeu, Node nodev, String edgeType) throws GraphException {
        // it will get the Node's with those ID's from the list, if they don't exist
        // then getNode method will throw an exception.
        getNode(nodeu.getId());
        getNode(nodev.getId());
        // if no exception was thrown then it will add the edge to both nodes.
        myGraph.get(nodeu.getId()).add(new Edge(nodeu, nodev, edgeType));
        myGraph.get(nodev.getId()).add(new Edge(nodev, nodeu, edgeType));
    }

    // incidentEdges that will take a Node as an argument,
    // and return an iterator with all the nodes that have edges with the given
    // Node.
    public Iterator incidentEdges(Node u) throws GraphException {
        // first we use a try statement.
        try {
            // creates an iterator of edges and get the array list and trun it into an
            // Iterator.
            Iterator<Edge> incdIterator = myGraph.get(u.getId()).iterator();
            // then will check if the the iterator stored anything by checking if there's
            // next.
            // if there's no next then it will return null, but if it has Edges in it, then
            // it will return the Iterator.
            if (!incdIterator.hasNext()) {
                return null;
            } else
                return incdIterator;
            // if in any case an exception was thrown it will catch it and throw graph
            // exception.
        } catch (Exception e) {
            throw new GraphException("No edges");
        }

    }

    // getEdge method, that takes two nodes and return the Edge between them.
    public Edge getEdge(Node u, Node v) throws GraphException {
        // calls incidentEdges method with one of the nodes.
        Iterator<Edge> i = incidentEdges(u);
        // then check if the method return null. Which means no Nodes between the two
        // nodes.
        if (i == null) {
            // so it throws graph exception.
            throw new GraphException("empty");
        }
        // then make another Edge variable, in order to travel through the iterator.
        Edge current;
        // while i has next.
        while (i.hasNext()) {
            // current would be equal to the next node in the edges of node u.
            current = i.next();
            // then it will check if the Edge node of u, is similar to node v.
            if (current.secondNode() == v) {
                // if found it will return the node.
                return current;
            }
        }
        // if the loop kept going and nothing was returned then it will throw an
        // exception
        throw new GraphException("empty");
    }

    // Are adjacent method that checks if node U and V are adjacent.
    public boolean areAdjacent(Node u, Node v) throws GraphException {
        // in order to do so, u check if u can get both nodes.
        // then you call getEdge method to check if there's a node between them.
        getNode(u.getId());
        getNode(v.getId());
        try {
            getEdge(u, v);
            // if getEdge catches an exception that means it's false an not adjacent.
        } catch (Exception e) {
            return false;
        }
        // else it's true.
        return true;
    }

}