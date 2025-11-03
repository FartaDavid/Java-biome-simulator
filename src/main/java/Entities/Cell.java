package Entities;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Cell {
    private Plant plant;
    private Animal animal;
    private Water water;
    private Air air;
    private Soil soil;

    public Cell() {
        plant = null;
        animal = null;
        water = null;
        air = null;
        soil = null;
    }
}
