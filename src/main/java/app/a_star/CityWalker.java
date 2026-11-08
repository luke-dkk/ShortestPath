package app.a_star;

import java.util.*;

public class CityWalker {
    // 0 = åben, 1 = mur
    static int[][] grid = {
            {0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0},
            {1, 1, 0, 1, 0, 1, 0, 1, 0, 1, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0},
            {1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
            {0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0},
            {1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0},
            {0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 0},
            {0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0},
    };

    static final int ROWS = 12;
    static final int COLUMNS = 12;

    static final String[][] CITY_NAMES = new String[ROWS][COLUMNS];

    static {

        CITY_NAMES[0][0] = "Nordvestby"; // række 0, kolonne 0
        CITY_NAMES[0][2] = "Østby";       // række 0, kolonne 2
        CITY_NAMES[2][4] = "Midtby";      // række 2, kolonne 4
        CITY_NAMES[4][5] = "Vestby";      // række 4, kolonne 5
        CITY_NAMES[6][3] = "Sydby";       // række 6, kolonne 3
        CITY_NAMES[8][3] = "Havneby";     // række 8, kolonne 3
        CITY_NAMES[11][11] = "Sydøstby";  // række 11, kolonne 11
    }

    public static void main(String[] args) {
        CityNode[][] nodes = new CityNode[ROWS][COLUMNS];

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < COLUMNS; column++) {
                if (grid[row][column] == 0) {
                    nodes[row][column] = new CityNode(
                            row,
                            column,
                            CITY_NAMES[row][column]
                    );
                }
            }
        }

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int row = 0; row < ROWS; row++) {
            for (int column = 0; column < ROWS; column++) {
                if (nodes[row][column] == null) {
                    continue;
                }

                for (int[] direction : directions) {
                    int newRow = row + direction[0];
                    int newColumn = column + direction[1];

                    if (newRow >= 0
                            && newRow < ROWS
                            && newColumn >= 0 && newColumn < ROWS
                            && nodes[newRow][newColumn] != null) {
                        nodes[row][column].addNeighbour(
                                nodes[newRow][newColumn]);
                    }
                }
            }
        }
        CityNode source = nodes [0][0];
        CityNode destination = nodes [11][11];

        findShortestPath(source, destination);
    }

    private static int heuristic(CityNode node, CityNode destination) {
        return Math.abs(destination.getRow() - node.getRow())
                + Math.abs(destination.getColumn() - node.getColumn());
    }

    public static void findShortestPath(CityNode source, CityNode destination) {
        Map<CityNode, CityNode> prev = new HashMap<>();
        Map<CityNode, Integer> dist = new HashMap<>();
        Set<CityNode> visited = new HashSet<>();

        PriorityQueue<NodeWithDist> queue = new PriorityQueue<>();
        queue.add(new NodeWithDist(source, 0, heuristic(source, destination)));
        dist.put(source, 0);

        while(!queue.isEmpty()){
            NodeWithDist current = queue.poll();
            if (current.node.equals(destination)) break;
            if (visited.contains(current.node)) continue;
            visited.add(current.node);

            for(CityNode next : current.node.getNeighbours()){
                if (visited.contains(next)) continue;
                int newDist = dist.get(current.node) + 1;
                if (newDist < dist.getOrDefault(next, Integer.MAX_VALUE)) {
                    dist.put(next, newDist);
                    prev.put(next, current.node);
                    queue.add(new NodeWithDist(next, newDist, heuristic(next, destination)));
                }

            }
        }
        List<String> path = new ArrayList<>();
        CityNode step = destination;
        while (step != null) {
            path.add(0, "(" + step.getRow() + "," + step.getColumn() + ")");
            step = prev.get(step);
        }

        System.out.println("Korteste vej: " + path);
        System.out.println("Antal skridt: " + (path.size() - 1));
    }



    private static class NodeWithDist implements Comparable<NodeWithDist> {
        CityNode node;
        int pathCost;
        int totalCost;

        public NodeWithDist(CityNode node, int pathCost, int heuristicCost) {
            this.node = node;
            this.pathCost = pathCost;
            this.totalCost = pathCost + heuristicCost;
        }

        @Override
        public int compareTo(NodeWithDist other) {
            return Integer.compare(this.totalCost, other.totalCost);
        }
    }
}
