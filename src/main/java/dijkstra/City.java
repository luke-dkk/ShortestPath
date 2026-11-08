package app.dijkstra;

import java.util.HashMap;
import java.util.Map;

public class City {
    private String name;
    private Map<City, Integer> neighbour;

    public City(String name) {
        this.name = name;
        this.neighbour = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public Map <City, Integer> getNeighbours() {
        return neighbour;
    }
    public void addNeighbour(City city, int distance) {
        neighbour.put(city, distance);
    }
}
