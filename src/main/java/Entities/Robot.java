package Entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Robot {
    private int energyPoint;
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

        for (int i = 0; i < 4; i++) {
            if (cells[i] == null)
                continue;

            int count = 0;
            double sum = 0;

            Soil soil = cells[i].getSoil();
            Air air = cells[i].getAir();
            Animal animal = cells[i].getAnimal();
            Plant plant = cells[i].getPlant();

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

            double mean = Math.abs(sum / count);
            int result = (int)Math.round(mean);

            if (result < maxQlt) {
                maxQlt = result;
                bestCell = cells[i];
            }
        }

        if (maxQlt <= energyPoint) {
            x = bestCell.getX();
            y = bestCell.getY();
            energyPoint -= maxQlt;
            commandOutput.put("message", "The robot has successfully moved to position (" + x + ", " + y + ").");
        }
        else {
            commandOutput.put("message", "ERROR: Not enough battery left. Cannot perform action");
        }
    }
}
