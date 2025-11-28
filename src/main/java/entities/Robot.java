package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import lombok.Getter;
import lombok.Setter;
import map.Cell;
import map.GameMap;

import java.util.ArrayList;

@Getter @Setter
public final class Robot {
    private static final int NEIGHBORS = 4;
    private static final int LAST_NEIGHBOR_INDEX = 3;

    private int energyPoint;
    private ArrayList<String> inventory = new ArrayList<>();
    private int initialenergyPoints;
    private int x;
    private int y;

    /**
     * Sets the energy points for the robot.
     * @param energyPoint The energy points to set.
     */
    public void setEnergyPoint(final int energyPoint) {
        this.energyPoint = energyPoint;
        this.initialenergyPoints = energyPoint;
    }

    /**
     * Resets the energy points by adding the charge amount.
     * @param timeToCharge The amount of energy to add.
     */
    public void resetEnergyPoint(final int timeToCharge) {
        energyPoint += timeToCharge;
    }

    /**
     * Moves the robot to the best adjacent cell.
     * @param map The game map.
     * @param commandOutput The output node for JSON.
     */
    public void moveRobot(final GameMap map, final ObjectNode commandOutput) {
        Cell[] cells = new Cell[NEIGHBORS];
        int n = map.getX();
        int m = map.getY();

        // putting neighbors in an array
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
            cells[LAST_NEIGHBOR_INDEX] = map.getCell(x - 1, y);
        }

        Cell bestCell = null;
        double maxQlt = Double.MAX_VALUE;

        // moving to the best cell
        for (int i = 0; i < NEIGHBORS; i++) {
            if (cells[i] == null) {
                continue;
            }

            int count = 0;
            double sum = 0;

            Soil soil = cells[i].getSoil();
            Air air = cells[i].getAir();
            Animal animal = cells[i].getAnimal();
            Plant plant = cells[i].getPlant();

            // verify which objects are present
            if (air != null) {
                sum += air.getToxicity();
                count++;
            }
            if (soil != null) {
                sum += soil.getBlockProbability();
                count++;
            }
            if (animal != null) {
                sum += animal.attackProbability();
                count++;
            }
            if (plant != null) {
                sum += plant.blockProbability();
                count++;
            }

            double mean = Math.abs(sum / count);
            int result = (int) Math.round(mean);

            if (result < maxQlt) {
                maxQlt = result;
                bestCell = cells[i];
            }
        }

        if (maxQlt <= this.energyPoint) {
            x = bestCell.getX();
            y = bestCell.getY();
            this.energyPoint -= maxQlt;
            commandOutput.put("message", "The robot has successfully moved to position ("
                    + x + ", " + y + ").");
        } else {
            commandOutput.put("message", "ERROR: Not enough battery left. Cannot perform action");
        }
    }

    /**
     * Scans an object at the given coordinates.
     * @param command The command input.
     * @param output The output node.
     * @param map The game map.
     * @param scanX The x coordinate to scan.
     * @param scanY The y coordinate to scan.
     * @return True if an object was successfully scanned, false otherwise.
     */
    public boolean scanObject(final CommandInput command,
                              final ObjectNode output,
                              final GameMap map,
                              final int scanX,
                              final int scanY) {
        Cell cell = map.getCell(scanX, scanY);
        String color = command.getColor();
        String smell = command.getSmell();
        String sound = command.getSound();

        if (color.equals("none")) {
            if (smell.equals("none")) {
                if (sound.equals("none")) {
                    Water water = cell.getWater();
                    if (water != null) {
                        output.put("message", "The scanned object is water.");
                        water.objectScanned();

                        inventory.add(water.getName());
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else if (!sound.equals("none")) {
            Animal animal = cell.getAnimal();
            if (animal != null) {
                animal.objectScanned();
                output.put("message", "The scanned object is an animal.");

                inventory.add(animal.getName());
                return true;
            } else {
                return false;
            }
        } else {
            // Altfel este PLANTA (nu are sunet, dar are culoare/miros)
            Plant plant = cell.getPlant();
            if (plant != null) {
                output.put("message", "The scanned object is a plant.");
                plant.objectScanned();

                inventory.add(plant.getName());
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * Checks if a component is in the inventory.
     * @param component The component name.
     * @return True if present, false otherwise.
     */
    public boolean isInInventory(final String component) {
        return inventory.contains(component);
    }

    /**
     * Removes a component from the inventory.
     * @param component The component name to remove.
     */
    public void removeFromInventory(final String component) {
        inventory.remove(component);
    }
}
