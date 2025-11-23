package Entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Animal extends Entities {
    private String status = "hungry";
    private int timer = 2;

    public Animal(String type, String name, double mass) {
        super(type, name, mass);
    }

    public abstract double AttackProbability();

    public Cell move(GameMap map, int x, int y, boolean predator) {
        Cell[] cells = new Cell[4];
        int n = map.getX();
        int m = map.getY();

        if (y + 1 < m) {
            cells[0] = map.getCell(x, y + 1);
        }
        if (x + 1 < n) {
            cells[1] = map.getCell(x + 1, y);
        }
        if (y - 1 >= 0) {
            cells[2] = map.getCell(x, y - 1);
        }
        if (x - 1 >= 0) {
            cells[3] = map.getCell(x - 1, y);
        }

        Cell bestcell = null;
        double waterQlt = Double.MIN_VALUE;

        for (int i = 0; i < 4; i++) {
            if (cells[i] != null) {
                if (!predator && cells[i].getAnimal() != null) continue;
                if (cells[i].getPlant() != null && cells[i].getWater() != null) {
                    if (cells[i].getPlant().isScanned() && cells[i].getWater().isScanned()) {
                        if (waterQlt < cells[i].getWater().calcQuality()) {
                            waterQlt = cells[i].getWater().calcQuality();
                            bestcell = cells[i];
                        }
                    }
                }
            }
        }

        if (bestcell != null)
            return bestcell;

        for (int i = 0; i < 4; i++) {
            if (cells[i] != null) {
                if (!predator && cells[i].getAnimal() != null) continue;
                if (cells[i].getPlant() != null) {
                    if (cells[i].getPlant().isScanned()) {
                        return cells[i];
                    }
                }
            }
        }

        if (bestcell != null)
            return bestcell;

        for (int i = 0; i < 4; i++) {
            if (cells[i] != null) {
                if (!predator && cells[i].getAnimal() != null) continue;
                if (cells[i].getWater() != null) {
                    if (cells[i].getWater().isScanned()) {
                        if (cells[i].getWater().getQuality() > waterQlt) {
                            bestcell = cells[i];
                            waterQlt = cells[i].getWater().getQuality();
                        }
                    }
                }
            }
        }

        if (bestcell != null)
            return bestcell;

        if (bestcell == null) {
            for (int i = 0; i < 4; i++) {
                if (cells[i] != null) {
                    if (!predator && cells[i].getAnimal() != null)
                        continue;
                    return cells[i];
                }
            }
        }

        if (bestcell == null) {
            bestcell = map.getCell(x, y);
        }
        return bestcell;
    }

    public void eatAnimal(Animal prey) {
        double mass = super.getMass();
        mass += prey.getMass();
        super.setMass(mass);
    }

    public void drinkWater(Water water) {
        if (!status.equals("sick")) {
            double mass = super.getMass();
            double waterMass = water.getMass();
            double waterToDrink = Math.min(mass * 0.08, waterMass);

            waterMass -= waterToDrink;
            water.setMass(waterMass);

            mass += waterToDrink;
            super.setMass(mass);
            status = "well-fed";
        }
    }

    public Plant eatPlant(Plant plant) {
        if (!status.equals("sick")) {
            double plantMass = plant.getMass();
            double mass = super.getMass();

            mass += plantMass;
            super.setMass(mass);

            plant = null;
            status = "well-fed";
        }
        return plant;
    }
}