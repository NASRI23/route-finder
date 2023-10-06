import java.util.ArrayList;
import java.util.Iterator;
import java.util.Iterator;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files

public class test {
    private static Graph theGraph;
    private static int sNode, eNode, mWidth, mLength, pRoads, cRoads;

    public static void main(String[] args) throws FileNotFoundException, GraphException {

        File myFile = new File("map0");
        Scanner read = new Scanner(myFile);
        read.nextLine();
        sNode = read.nextInt();
        eNode = read.nextInt();
        mWidth = read.nextInt();
        mLength = read.nextInt();
        pRoads = read.nextInt();
        cRoads = read.nextInt();
        theGraph = new Graph(mWidth * mLength);
        int i = -1, j, k;
        String current;
        read.nextLine();

        while (read.hasNextLine()) {
            System.out.println("new line");
            j = 0;
            current = read.nextLine();
            while (j < ((mWidth * 2) - 1)) {
                System.out.println("same line");

                if (current.charAt(j) == '+') {
                    System.out.printf("add node %d \n", i);
                    i++;
                    j++;
                } else if (current.charAt(j) == 'P') {
                    System.out.printf("adds a P between node %d and node %d \n", i, i + 1);
                    addEdgeHelper(i, i + 1, "public");
                    j++;
                } else if (current.charAt(j) == 'V') {
                    System.out.printf("adds a V between node %d and node %d \n", i, i + 1);
                    addEdgeHelper(i, i + 1, "private");
                    j++;
                } else if (current.charAt(j) == 'C') {
                    System.out.printf("adds a C between node %d and node %d \n", i, i + 1);
                    addEdgeHelper(i, i + 1, "construction");
                    j++;
                } else
                    j++;
            }
            if (read.hasNextLine()) {
                System.out.println("new line");
                j = 0;
                k = 0;
                current = read.nextLine();
                while (k < ((mWidth * 2) - 1)) {
                    if (current.charAt(k) == 'P') {
                        System.out.printf("adds a P between node %d and node %d \n", i + j + 1, (i + j + 1) - mWidth);
                        addEdgeHelper(i + j + 1, (i + j + 1) - mWidth, "public");
                        j++;
                        k++;
                    } else if (current.charAt(k) == 'V') {
                        System.out.printf("adds a V between node %d and node %d \n", i + j + 1, (i + j + 1) - mWidth);
                        addEdgeHelper(i + j + 1, (i + j + 1) - mWidth, "private");
                        j++;
                        k++;
                    } else if (current.charAt(k) == 'C') {
                        System.out.printf("adds a C between node %d and node %d \n", i + j + 1, (i + j + 1) - mWidth);
                        addEdgeHelper(i + j + 1, (i + j + 1) - mWidth, "construction");
                        j++;
                        k++;
                    } else if (k % 2 == 0) {
                        j++;
                        k++;
                    } else
                        k++;
                }
            }
        }

        read.close();
        Edge theEdge = (Edge) theGraph.incidentEdges(theGraph.getNode(4)).next();
        System.out.println(theEdge.getType());
        ArrayList<Node> thePath = new ArrayList<Node>();
        ArrayList<Node> thePath1 = new ArrayList<Node>();
        findPathList(sNode, eNode, pRoads, cRoads, thePath);
        int p = thePath.size();
        for (int m = 0; m < p; m++) {
            System.out.println(thePath.get(m).getId());

        }
    }

    private static void addEdgeHelper(int fID, int sID, String type) throws GraphException {
        theGraph.addEdge(theGraph.getNode(fID), theGraph.getNode(sID), type);
    }
    // public static Iterator findPath(int start, int destination, int maxPrivate,
    // int maxConstruction) {

    // ArrayList<Node> thePath = new ArrayList<>();
    // Iterator<Edge> edges = theGraph.incidentEdges(theGraph.getNode(start));
    // int mPr, mCo;
    // }
    private static ArrayList<Node> findPathList(int s, int d, int mP, int mC, ArrayList<Node> path)
            throws GraphException {
        if (s == d)
            return path;
        Iterator<Edge> edges = theGraph.incidentEdges(theGraph.getNode(s));
        if (edges.hasNext()) {
            Edge currentEdge;
            Iterator<Edge> current = edges;
            while (current.hasNext()) {
                currentEdge = current.next();
                if (!currentEdge.secondNode().getMark() && currentEdge.getType() == "public") {
                    if (currentEdge.secondNode().getId() == 12) {
                        System.out.println("");
                    }
                    currentEdge.secondNode().markNode(true);
                    theGraph.getNode(s).markNode(true);
                    path.add(currentEdge.secondNode());
                    if (findPathList(currentEdge.secondNode().getId(), d, mP, mC, path) == null) {
                        currentEdge.secondNode().markNode(false);
                        path.remove(currentEdge.secondNode());
                    } else
                        return path;
                }
            }
            current = theGraph.incidentEdges(theGraph.getNode(s));
            ;
            while (current.hasNext()) {
                currentEdge = current.next();
                if (!currentEdge.secondNode().getMark() && currentEdge.getType() == "private" && mP > 0) {
                    currentEdge.secondNode().markNode(true);
                    theGraph.getNode(s).markNode(true);
                    path.add(currentEdge.secondNode());
                    if (findPathList(currentEdge.secondNode().getId(), d, mP - 1, mC, path) == null) {
                        currentEdge.secondNode().markNode(false);
                        path.remove(currentEdge.secondNode());
                    } else
                        return path;
                }
            }
            current = theGraph.incidentEdges(theGraph.getNode(s));
            ;
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
        return null;
    }
}