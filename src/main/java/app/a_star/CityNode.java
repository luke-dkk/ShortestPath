package app.a_star;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CityNode {
    private int row;
    private int column;
    private String name;
    private List<CityNode> neighbours;

    public CityNode (int row, int column, String name) {
        this.row = row;
        this.column = column;
        this.name = name;
        this.neighbours = new ArrayList<>();
    }
    public CityNode(String name) {
        this.name = name;
        this.neighbours = new ArrayList<>();
    }



    public List<CityNode> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(CityNode cityNode) {
        neighbours.add(cityNode);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    public String getName() {
        return name;
    }
}
