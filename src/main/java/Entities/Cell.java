package Entities;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Cell {
    private int x;
    private int y;
    private Plant plant;
    private Animal animal;
    private Water water;
    private Air air;
    private Soil soil;
    private Robot robot;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        plant = null;
        animal = null;
        water = null;
        air = null;
        soil = null;
        robot = null;
    }
}
