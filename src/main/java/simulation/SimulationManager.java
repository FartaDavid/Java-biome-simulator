package simulation;

import entities.Air;
import entities.Animal;
import entities.Plant;
import entities.Soil;
import entities.Water;
import entities.maturity_and_categoryPlant.Maturity;
import map.Cell;
import map.GameMap;

/**
 * Handles the simulation logic involving interactions between entities.
 */
public final class SimulationManager {
    private final GameMap map;

    public SimulationManager(final GameMap map) {
        this.map = map;
    }

    /**
     * Iterates through the map to handle interactions for scanned objects.
     * Manages plant growth, animal movement, and feeding.
     */
    public void verifyScannedObj() {
        int x = map.getX();
        int y = map.getY();
        boolean[][] verified = new boolean[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {

                Cell currentCell = map.getCell(i, j);
                Air air = currentCell.getAir();
                Plant plant = currentCell.getPlant();
                Animal animal = currentCell.getAnimal();
                Soil soil = currentCell.getSoil();
                Water water = currentCell.getWater();

                if (plant != null && plant.isScanned()) {
                    if (animal != null && animal.isScanned()) {
                        String toxicity = air.airQuality();
                        if (toxicity.equals("poor")) {
                            animal.setStatus("sick");
                        }
                    }
                    if (water != null) {
                        plant.addIndex();
                    }
                    if (soil != null) {
                        plant.addIndex();
                    }

                    if (plant.getMaturity() == Maturity.Dead) {
                        currentCell.setPlant(null);
                    } else {
                        double o2 = air.getOxygenLevel() + plant.genO2();
                        double o2Round = air.roundTwoDecimals(o2);
                        air.setOxygenLevel(o2Round);

                        air.normalizeQuality(air.calculateAirQuality());
                    }
                }

                if (water != null && water.isScanned()) {
                    int timer = water.getTime() - 1;
                    if (timer == 0) {
                        air.addHumidity();
                        air.setHumidity(Math.round(air.getHumidity()
                                * GameMap.ROUNDING_FACTOR) / GameMap.ROUNDING_FACTOR);

                        soil.addWaterRetention();
                        soil.setWaterRetention(Math.round(soil.getWaterRetention()
                                * GameMap.ROUNDING_FACTOR) / GameMap.ROUNDING_FACTOR);
                        soil.calculateQuality();

                        air.normalizeQuality(air.calculateAirQuality());

                        water.setTime(GameMap.TIMER_RESET_VALUE);
                    } else {
                        water.setTime(timer);
                    }
                }

                if (animal != null && animal.isScanned()) {
                    if (verified[i][j]) {
                        continue;
                    }

                    verified[i][j] = true;
                    boolean predator = (animal.getType().equals("Carnivores")
                            || animal.getType().equals("Parasites"));

                    animal.setK(0);

                    if (plant != null && plant.isScanned()) {
                        animal.eatPlant(plant);
                        currentCell.setPlant(null);
                        animal.setK(animal.getK() + 1);
                    }
                    if (water != null && water.isScanned()) {
                        animal.drinkWater(water);
                        if (water.getMass() == 0) {
                            currentCell.setWater(null);
                        }
                        animal.setK(animal.getK() + 1);
                    }

                    if (air.isToxic()) {
                        animal.setK(0);
                        animal.setStatus("sick");
                    }
                    soil.addOrganicMatter(animal.getK());
                    soil.calculateQuality();

                    animal.setTimer(animal.getTimer() - 1);

                    if (animal.getTimer() == 0) {
                        // Pass 'map' instead of 'this' because 'this' is now SimulationManager
                        Cell bestCell = animal.move(map, i, j, predator);

                        if (predator) {
                            animal.setTimer(GameMap.TIMER_RESET_VALUE);
                            if (bestCell.getAnimal() != null && bestCell != currentCell) {
                                bestCell.getSoil().addOrganicMatter(1);
                                bestCell.getSoil().calculateQuality();

                                animal.eatAnimal(bestCell.getAnimal());
                            }
                            bestCell.setAnimal(animal);
                            verified[bestCell.getX()][bestCell.getY()] = true;

                            if (currentCell != bestCell) {
                                currentCell.setAnimal(null);
                            }
                        } else {
                            animal.setTimer(GameMap.TIMER_RESET_VALUE);

                            bestCell.setAnimal(animal);
                            verified[bestCell.getX()][bestCell.getY()] = true;

                            if (currentCell != bestCell) {
                                currentCell.setAnimal(null);
                            }
                        }
                    }
                }
            }
        }
    }
}
