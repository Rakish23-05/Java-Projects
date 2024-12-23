import java.util.*;

class DistanceVectorRouting {
    private int numNodes;
    private int[][] distanceTable;
    private int[][] nextHop;

    public DistanceVectorRouting(int numNodes) {
        this.numNodes = numNodes;
        distanceTable = new int[numNodes][numNodes];
        nextHop = new int[numNodes][numNodes];
        initializeTables();
    }

    private void initializeTables() {
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (i == j) {
                    distanceTable[i][j] = 0;
                    nextHop[i][j] = i;
                } else {
                    distanceTable[i][j] = Integer.MAX_VALUE;
                    nextHop[i][j] = -1;
                }
            }
        }
    }

    public void addLink(int source, int dest, int cost) {
        distanceTable[source][dest] = cost;
        distanceTable[dest][source] = cost;
        nextHop[source][dest] = dest;
        nextHop[dest][source] = source;
    }

    public void updateTable() {
        boolean updated;
        do {
            updated = false;
            for (int i = 0; i < numNodes; i++) {
                for (int j = 0; j < numNodes; j++) {
                    if (i != j) {
                        for (int k = 0; k < numNodes; k++) {
                            if (distanceTable[i][k] != Integer.MAX_VALUE && distanceTable[k][j] != Integer.MAX_VALUE) {
                                int newDistance = distanceTable[i][k] + distanceTable[k][j];
                                if (newDistance < distanceTable[i][j]) {
                                    distanceTable[i][j] = newDistance;
                                    nextHop[i][j] = nextHop[i][k];
                                    updated = true;
                                }
                            }
                        }
                    }
                }
            }
        } while (updated);
    }

    public void printDistanceTable() {
        System.out.println("Distance Table:");
        for (int i = 0; i < numNodes; i++) {
            System.out.print("Node " + i + ": ");
            for (int j = 0; j < numNodes; j++) {
                if (distanceTable[i][j] == Integer.MAX_VALUE) {
                    System.out.print("INF ");
                } else {
                    System.out.print(distanceTable[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public void printNextHopTable() {
        System.out.println("Next Hop Table:");
        for (int i = 0; i < numNodes; i++) {
            System.out.print("Node " + i + ": ");
            for (int j = 0; j < numNodes; j++) {
                if (nextHop[i][j] == -1) {
                    System.out.print("INF ");
                } else {
                    System.out.print(nextHop[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public int getNextHop(int source, int dest) {
        return nextHop[source][dest];
    }

    public static void main(String[] args) {
        DistanceVectorRouting dvr = new DistanceVectorRouting(5);
        dvr.addLink(0, 1, 10);
        dvr.addLink(0, 2, 5);
        dvr.addLink(1, 2, 2);
        dvr.addLink(1, 3, 1);
        dvr.addLink(2, 3, 9);
        dvr.addLink(3, 4, 4);

        dvr.updateTable();
        dvr.printDistanceTable();
        dvr.printNextHopTable();
    }
}
