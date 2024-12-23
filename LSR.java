import java.util.*;

class LinkStateRouting {
    private int numNodes;
    private int[][] costMatrix;
    private int[] shortestPaths;
    private int[] visited;
    private int[] previousNode;

    public LinkStateRouting(int numNodes) {
        this.numNodes = numNodes;
        costMatrix = new int[numNodes][numNodes];
        shortestPaths = new int[numNodes];
        visited = new int[numNodes];
        previousNode = new int[numNodes];
        initializeCostMatrix();
    }

    private void initializeCostMatrix() {
        for (int i = 0; i < numNodes; i++) {
            for (int j = 0; j < numNodes; j++) {
                if (i == j) {
                    costMatrix[i][j] = 0;
                } else {
                    costMatrix[i][j] = Integer.MAX_VALUE;
                }
            }
        }
    }

    public void addLink(int source, int dest, int cost) {
        costMatrix[source][dest] = cost;
        costMatrix[dest][source] = cost;
    }

    public void calculateShortestPaths(int startNode) {
        Arrays.fill(shortestPaths, Integer.MAX_VALUE);
        Arrays.fill(visited, 0);
        Arrays.fill(previousNode, -1);
        shortestPaths[startNode] = 0;

        for (int count = 0; count < numNodes - 1; count++) {
            int u = selectMinNode();
            visited[u] = 1;
            for (int v = 0; v < numNodes; v++) {
                if (visited[v] == 0 && costMatrix[u][v] != Integer.MAX_VALUE) {
                    int newDist = shortestPaths[u] + costMatrix[u][v];
                    if (newDist < shortestPaths[v]) {
                        shortestPaths[v] = newDist;
                        previousNode[v] = u;
                    }
                }
            }
        }
    }

    private int selectMinNode() {
        int min = Integer.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < numNodes; i++) {
            if (visited[i] == 0 && shortestPaths[i] < min) {
                min = shortestPaths[i];
                minIndex = i;
            }
        }
        return minIndex;
    }

    public void printShortestPaths() {
        System.out.println("Shortest Paths from start node:");
        for (int i = 0; i < numNodes; i++) {
            System.out.print("To node " + i + ": ");
            if (shortestPaths[i] == Integer.MAX_VALUE) {
                System.out.println("No path");
            } else {
                System.out.println(shortestPaths[i]);
            }
        }
    }

    public void printPath(int targetNode) {
        if (previousNode[targetNode] == -1) {
            System.out.println("No path");
            return;
        }
        List<Integer> path = new ArrayList<>();
        int currentNode = targetNode;
        while (currentNode != -1) {
            path.add(currentNode);
            currentNode = previousNode[currentNode];
        }
        Collections.reverse(path);
        System.out.print("Path: ");
        for (int node : path) {
            System.out.print(node + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LinkStateRouting lsr = new LinkStateRouting(6);
        lsr.addLink(0, 1, 7);
        lsr.addLink(0, 2, 9);
        lsr.addLink(0, 5, 14);
        lsr.addLink(1, 2, 10);
        lsr.addLink(1, 3, 15);
        lsr.addLink(2, 3, 11);
        lsr.addLink(2, 5, 2);
        lsr.addLink(3, 4, 6);
        lsr.addLink(4, 5, 9);

        lsr.calculateShortestPaths(0);
        lsr.printShortestPaths();
        lsr.printPath(4);
        lsr.printPath(3);
    }
}
