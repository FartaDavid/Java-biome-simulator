package map;

import entities.Air;
import entities.Animal;
import entities.Plant;
import entities.Robot;
import entities.Soil;
import entities.Water;
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

    public Cell(final int x, final int y) {
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
