import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class MyMap {
    // private variables for the class.
    private Graph theGraph;
    private int sNode, eNode, mWidth, mLength, pRoads, cRoads;

    // MyMap takes a constructor with the File name that will be used to create the
    // map.
    public MyMap(String inputFile) throws MapException {
        // it will try to read the file and scan all lines, if there's any errors it wil
        // catch them and throw map exception.
        try {
            // creates a new file and import the file give in the argument.
            File myFile = new File(inputFile);
            // also creates a scanner to scan the info from the file.
            Scanner read = new Scanner(myFile);

            // skips the first line. then assigns each line or integer to the proper
            // variable.
            read.nextLine();
            sNode = read.nextInt();
            eNode = read.nextInt();
            mWidth = read.nextInt();
            mLength = read.nextInt();
            pRoads = read.nextInt();
            cRoads = read.nextInt();

            // it makes a new graph with the length of (M*L)
            theGraph = new Graph(mWidth * mLength);
            // initialize variables.
            int i = -1, j, k;
            String current;
            read.nextLine();
            // while loop that keeps going as long as there's a next line.
            while (read.hasNextLine()) {
                // j is set to 0
                j = 0;
                // and current is set to the whole line.
                current = read.nextLine();
                // nested loop that goes as long as the chars in the given line. which is the
                // width*2 -1
                while (j < ((mWidth * 2) - 1)) {
                    // if it's a node then you add 1 to i,
                    if (current.charAt(j) == '+') {
                        i++;
                        j++;
                        // else if it's an edge type public then you take the previous node and the next
                        // and link them
                    } else if (current.charAt(j) == 'P') {
                        // add edge helper that is used to do the same job more then once, it's used a
                        // lot in the method.
                        addEdgeHelper(i, i + 1, "public");
                        j++;
                        // else if it's an edge type private then you take the previous node and the
                        // next and link them
                    } else if (current.charAt(j) == 'V') {
                        addEdgeHelper(i, i + 1, "private");
                        j++;
                        // else if it's an edge type constructions then you take the previous node and
                        // the next and link them
                    } else if (current.charAt(j) == 'C') {
                        addEdgeHelper(i, i + 1, "construction");
                        j++;
                    } else // else if it's none, which is a block then it does nothing but incrementing j
                        j++;
                }
                // if you finish the first line, it checks if there's next line.
                if (read.hasNextLine()) {
                    // again sets j to 0 and this time k to 0 too.
                    j = 0;
                    k = 0;
                    current = read.nextLine();
                    // another nested while loop that does the same thing.
                    while (k < ((mWidth * 2) - 1)) {

                        // the difference now is that it links the edge between the node above it and
                        // below it
                        // and it uses k to skip blocks.
                        if (current.charAt(k) == 'P') {
                            addEdgeHelper(i + j + 1, (i + j + 1) - mWidth, "public");
                            j++;
                            k++;
                        } else if (current.charAt(k) == 'V') {
                            addEdgeHelper(i + j + 1, (i + j + 1) - mWidth, "private");
                            j++;
                            k++;
                        } else if (current.charAt(k) == 'C') {
                            addEdgeHelper(i + j + 1, (i + j + 1) - mWidth, "construction");
                            j++;
                            k++;
                        } else if (k % 2 == 0) {
                            j++;
                            k++;
                        } else
                            k++;
                    }
                    // so two loops do basically the same thing, but each is for a different type of
                    // line.
                }
            }
            // when it goes out of the main while loop it closes the scanner.
            read.close();
            // an incase of any exceptions it throws a map exception.
        } catch (Exception e) {
            throw new MapException("File not found!");
        }
    }

    // return methods, each return the variable from the class.
    public Graph getGraph() {
        return theGraph;
    }

    public int getStartingNode() {
        return sNode;
    }

    public int getDestinationNode() {
        return eNode;
    }

    public int maxPrivateRoads() {
        return pRoads;
    }

    public int maxConstructionRoads() {
        return cRoads;
    }

    // find path method that tries to find a path between the first and destination
    // nodes.
    public Iterator findPath(int start, int destination, int maxPrivate, int maxConstruction) throws GraphException {
        // so it takes the ID's and the maximum amount of private and construction roads
        // it can access.

        // creates an arraylist called the path type Node.
        ArrayList<Node> thePath = new ArrayList<>();
        // then add the starting node to the path.
        thePath.add(theGraph.getNode(start));
        // then calls a helper method called find path to return the whole path.
        findPathList(start, destination, maxPrivate, maxConstruction, thePath);

        // the path now is full with the Node you need to take in order to reach the
        // destination
        // so a iterator is created with the path found, and it's returned.
        Iterator<Node> PathFound = thePath.iterator();

        // if path was size one, that means only the starting node was added meaning no
        // path was found, so we make it null.
        if (thePath.size() == 1) {
            PathFound = null;
        }
        return PathFound;

    }

    // addEdgeHelper that adds the edge only by getting, it's ID and the type.
    private void addEdgeHelper(int fID, int sID, String type) throws GraphException {
        // it calls addEdge method from theGraph and use getNode to turn the ID's into
        // node.
        theGraph.addEdge(theGraph.getNode(fID), theGraph.getNode(sID), type);
    }

    // find Path helper.
    // I used a helper, only because I can put the path in the parameters, so when
    // it's called
    // in a recursive call the path would always be taken too.
    private ArrayList<Node> findPathList(int s, int d, int mP, int mC, ArrayList<Node> path)
            throws GraphException {
        // if the starting ID is the same as the destination then it will return the
        // path.
        if (s == d)
            return path;
        // but if not, it will create a new edge iterator called edges.
        Iterator<Edge> edges = theGraph.incidentEdges(theGraph.getNode(s));

        // if the edges at the start has a next
        if (edges.hasNext()) {
            // then it will make a current with all the edges
            Edge currentEdge;
            Iterator<Edge> current = edges;
            // while current has a next.
            while (current.hasNext()) {
                // the currentEdge would be equal to the next in the iterator, just so it
                // doesn't mess with the iterator.
                currentEdge = current.next();
                // if the Node at that edge is not marked and type is public it will take that
                // way.
                if (!currentEdge.secondNode().getMark() && currentEdge.getType() == "public") {
                    // then it will mark it and the current node to true.
                    currentEdge.secondNode().markNode(true);
                    theGraph.getNode(s).markNode(true);
                    // and will add currentEdge to the path.
                    path.add(currentEdge.secondNode());
                    // then it will go into the same method (recursive call). with the current Node
                    // as starting node this time.
                    // if the method return null, meaning that's not the right path, or this path
                    // won't work.
                    // because s==d is never true.
                    if (findPathList(currentEdge.secondNode().getId(), d, mP, mC, path) == null) {
                        // then it will unmark the node
                        currentEdge.secondNode().markNode(false);
                        // and remove it from the path.
                        path.remove(currentEdge.secondNode());
                    } else
                        // but if it returns a value this it works and will return the path.
                        return path;
                }
            }
            // if we got to this point that means no public node was found or worked
            // so we re initialize current and do the same thing again.
            current = theGraph.incidentEdges(theGraph.getNode(s));
            // another while loop that does almost the same thing.
            while (current.hasNext()) {
                currentEdge = current.next();
                // only that it also checks if the max number of private roads is still more
                // than zero.
                if (!currentEdge.secondNode().getMark() && currentEdge.getType() == "private" && mP > 0) {
                    currentEdge.secondNode().markNode(true);
                    theGraph.getNode(s).markNode(true);
                    path.add(currentEdge.secondNode());
                    // also when it call a recursive call it subtract one from mP because it's
                    // taking a private road.
                    if (findPathList(currentEdge.secondNode().getId(), d, mP - 1, mC, path) == null) {
                        currentEdge.secondNode().markNode(false);
                        path.remove(currentEdge.secondNode());
                    } else
                        return path;
                }
            }
            // again same thing but this time with the construction roads.
            current = theGraph.incidentEdges(theGraph.getNode(s));
            while (current.hasNext()) {
                currentEdge = current.next();
                if (!currentEdge.secondNode().getMark() && currentEdge.getType() == "construction" && mC > 0) {
                    currentEdge.secondNode().markNode(true);
                    theGraph.getNode(s).markNode(true);
                    path.add(currentEdge.secondNode());
                    if (findPathList(currentEdge.secondNode().getId(), d, mP, mC - 1, path) == null) {
                        currentEdge.secondNode().markNode(false);
                        path.remove(currentEdge.secondNode());
                    } else
                        return path;
                }
            }
        }
        // if nothing was returned then that path is blocked and there's no where else
        // to go, so it returns null
        return null;
    }
}
