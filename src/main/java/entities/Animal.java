package entities;

import lombok.Getter;
import lombok.Setter;
import map.Cell;
import map.GameMap;

/**
 * Abstract class representing an Animal entity.
 * Defines behavior for movement, eating, and drinking.
 */
@Getter @Setter
public abstract class Animal extends Entities {
    private static final int NEIGHBORS_COUNT = 4;
    private static final int LEFT_NEIGHBOR_INDEX = 3;
    private static final double WATER_CONSUMPTION_RATIO = 0.08;

    private String status = "hungry";
    private int timer = 2;
    private int k = 0;

    public Animal(final String type, final String name, final double mass) {
        super(type, name, mass);
    }

    /**
     * Calculates the probability of a successful attack.
     *
     * @return the probability as a double.
     */
    public abstract double attackProbability();

    /**
     * Determines the next cell the animal should move to based on surroundings.
     * This method scans neighbors for food or water depending on the animal type.
     *
     * @param map      The game map.
     * @param x        The current X coordinate.
     * @param y        The current Y coordinate.
     * @param predator Boolean flag indicating if the animal is a predator.
     * @return The Cell object where the animal moves.
     */
    public Cell move(final GameMap map, final int x, final int y, final boolean predator) {
        Cell[] cells = new Cell[NEIGHBORS_COUNT];
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
            cells[LEFT_NEIGHBOR_INDEX] = map.getCell(x - 1, y);
        }

        Cell bestcell = null;
        double waterQlt = Double.MIN_VALUE;

        for (int i = 0; i < NEIGHBORS_COUNT; i++) {
            if (cells[i] != null) {

                if (!predator && cells[i].getAnimal() != null) {
                    continue;
                }

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

        if (bestcell != null) {
            return bestcell;
        }

        for (int i = 0; i < NEIGHBORS_COUNT; i++) {
            if (cells[i] != null) {

                if (!predator && cells[i].getAnimal() != null) {
                    continue;
                }

                if (cells[i].getPlant() != null) {
                    if (cells[i].getPlant().isScanned()) {
                        return cells[i];
                    }
                }

            }
        }

        for (int i = 0; i < NEIGHBORS_COUNT; i++) {
            if (cells[i] != null) {

                if (!predator && cells[i].getAnimal() != null) {
                    continue;
                }

                if (cells[i].getWater() != null) {
                    if (cells[i].getWater().isScanned()) {
                        if (cells[i].getWater().calcQuality() > waterQlt) {
                            bestcell = cells[i];
                            waterQlt = cells[i].getWater().calcQuality();
                        }
                    }
                }

            }
        }

        if (bestcell != null) {
            return bestcell;
        }

        for (int i = 0; i < NEIGHBORS_COUNT; i++) {
            if (cells[i] != null) {

                if (!predator && cells[i].getAnimal() != null) {
                    continue;
                }

                return cells[i];
            }
        }

        return map.getCell(x, y);

    }

    /**
     * Logic for eating another animal (prey).
     * Increases the current animal's mass by the prey's mass.
     *
     * @param prey The animal being eaten.
     */
    public void eatAnimal(final Animal prey) {
        double mass = super.getMass();
        mass += prey.getMass();
        super.setMass(mass);
    }

    /**
     * Logic for drinking water.
     * Consumes a portion of the water mass and increases animal mass.
     *
     * @param water The water object to drink from.
     */
    public void drinkWater(final Water water) {
        if (!status.equals("sick")) {
            double mass = super.getMass();
            double waterMass = water.getMass();
            double waterToDrink = Math.min(mass * WATER_CONSUMPTION_RATIO, waterMass);

            waterMass -= waterToDrink;
            water.setMass(waterMass);

            mass += waterToDrink;
            super.setMass(mass);
            status = "well-fed";
        }
    }

    /**
     * Logic for eating a plant.
     * Increases the animal's mass by the plant's mass.
     *
     * @param plant The plant being eaten.
     */
    public void eatPlant(final Plant plant) {
        if (!status.equals("sick")) {
            double plantMass = plant.getMass();
            double mass = super.getMass();

            mass += plantMass;
            super.setMass(mass);

            status = "well-fed";
        }
    }
}
