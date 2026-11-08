package app.a_star;

import java.util.ArrayList;
import java.util.List;

public class CityNode {
    private int row;
    private int coloumn;
    private List<CityNode> neighbours;

    public CityNode (int row, int coloumn) {
        this.row = row;
        this.coloumn = coloumn;
        this.neighbours = new ArrayList<>();
    }

    int getRow() {
        return row;
    }
    int getColoumn() {
        return coloumn;
    }
    public List<CityNode> getNeighbours() {
        return neighbours;
    }
    public void addNeighbour(CityNode neighbour) {
        neighbours.add(neighbour);
    }
}
