package Entities;

public class Map {
    private Cell[][] cell;

    public Map(int n) {
        cell = new Cell[n][n];
    }
}
