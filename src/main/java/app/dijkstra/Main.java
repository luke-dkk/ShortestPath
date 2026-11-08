package dijkstra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class Main {
    public static void main(String[] args) {
        City start = new City("Start");
        City indreBy = new City("Indre By");
        City osterbro = new City("Østerbro");
        City norrebro = new City("Nørrebro");
        City nordvest = new City("Nordvest");
        City vanlose = new City("Vanløse");
        City valby = new City("Valby");
        City vesterbro = new City("Vesterbro");
        City amager = new City("Amager");
        City goal = new City("Goal");

        start.addNeighbour(indreBy, 2);
        indreBy.addNeighbour(osterbro, 5);
        indreBy.addNeighbour(norrebro, 7);
        osterbro.addNeighbour(nordvest, 3);
        osterbro.addNeighbour(indreBy, 5);
        norrebro.addNeighbour(indreBy, 7);
        norrebro.addNeighbour(nordvest, 4);
        nordvest.addNeighbour(osterbro, 3);
        nordvest.addNeighbour(norrebro, 4);
        nordvest.addNeighbour(vanlose, 6);
        vanlose.addNeighbour(valby, 6);
        valby.addNeighbour(vanlose, 6);
        valby.addNeighbour(vesterbro, 4);
        vesterbro.addNeighbour(valby, 4);
        vesterbro.addNeighbour(amager, 8);
        amager.addNeighbour(vesterbro, 8);
        amager.addNeighbour(goal, 3);

        findShortestPath(start, goal);
    }

    private static class NodeWithDist implements Comparable<NodeWithDist> {
        private final City city;
        private final int dist;

        private NodeWithDist(City city, int dist) {
            this.city = city;
            this.dist = dist;
        }

        @Override
        public int compareTo(NodeWithDist other) {
            return Integer.compare(dist, other.dist);
        }
    }

    public static void findShortestPath(City start, City goal) {
        Map<City, Integer> distances = new HashMap<>();
        Map<City, City> previous = new HashMap<>();
        Set<City> visited = new HashSet<>();
        PriorityQueue<NodeWithDist> queue = new PriorityQueue<>();

        distances.put(start, 0);
        queue.add(new NodeWithDist(start, 0));

        while (!queue.isEmpty()) {
            NodeWithDist current = queue.poll();

            if (!visited.add(current.city)) {
                continue;
            }

            if (current.city.equals(goal)) {
                break;
            }

            for (Map.Entry<City, Integer> entry : current.city.getNeighbours().entrySet()) {
                City nextCity = entry.getKey();
                int newDistance = current.dist + entry.getValue();

                if (newDistance < distances.getOrDefault(nextCity, Integer.MAX_VALUE)) {
                    distances.put(nextCity, newDistance);
                    previous.put(nextCity, current.city);
                    queue.add(new NodeWithDist(nextCity, newDistance));
                    System.out.println("Visiting: " + nextCity.getName()
                            + ", Distance: " + newDistance);
                }
            }
        }



        List<String> path = new ArrayList<>();
        City current = goal;
        while (current != null) {
            path.add(current.getName());
            current = previous.get(current);
        }
        Collections.reverse(path);

        System.out.println("Shortest path: " + path);
        System.out.println("Total distance: " + distances.get(goal));
    }
}
