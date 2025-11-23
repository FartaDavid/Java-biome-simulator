package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class Robot {
    private int energyPoint;
    private ArrayList<String> inventory = new ArrayList<>();
    private int initialenergyPoints;
    private int x;
    private int y;

    public void setEnergyPoint(int energyPoint) {
        this.energyPoint = energyPoint;
        this.initialenergyPoints = energyPoint;
    }

    public void resetEnergyPoint(int timeToCharge) {
        energyPoint += timeToCharge;
    }

    public void moveRobot(GameMap map, ObjectNode commandOutput) {
        Cell[] cells = new Cell[4];
        int n = map.getX();
        int m = map.getY();

        // pun fiecare celula intr-un vector
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

        Cell bestCell = null;
        double maxQlt = Integer.MAX_VALUE;

        // parcurg vectorul pentru a afla cea mai buna celula
        for (int i = 0; i < 4; i++) {
            if (cells[i] == null)
                continue;

            int count = 0;
            double sum = 0;

            Soil soil = cells[i].getSoil();
            Air air = cells[i].getAir();
            Animal animal = cells[i].getAnimal();
            Plant plant = cells[i].getPlant();

            // verific pericolele
            if (air != null) {
                sum += air.getToxicity();
                count++;
            }
            if (soil != null) {
                sum += soil.getBlockProbability();
                count++;
            }
            if (animal != null) {
                sum += animal.AttackProbability();
                count++;
            }
            if (plant != null) {
                sum += plant.BlockProbability();
                count++;
            }

            double a = Math.abs(sum / count);
            int result = (int) Math.round(a);

            if (result < maxQlt) {
                maxQlt = result;
                bestCell = cells[i];
            }
        }

        if (maxQlt <= this.energyPoint) {
            x = bestCell.getX();
            y = bestCell.getY();
            this.energyPoint -= maxQlt;
            commandOutput.put("message", "The robot has successfully moved to position (" + x + ", " + y + ").");
        } else {
            commandOutput.put("message", "ERROR: Not enough battery left. Cannot perform action");
        }
    }

    public boolean scanObject(CommandInput command, ObjectNode output, GameMap map, int x, int y) {
        Cell cell = map.getCell(x, y);
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
                    } else
                        return false;
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
        }
        // Altfel este PLANTA (nu are sunet, dar are culoare/miros)
        else {
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

    public boolean isInInventory(String component) {
        return inventory.contains(component);
    }

    public void removeFromInventory(String component) {
        inventory.remove(component);
    }
}

