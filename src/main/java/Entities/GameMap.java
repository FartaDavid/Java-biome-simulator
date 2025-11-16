package Entities;

import fileio.*;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GameMap {
    private Cell[][] cell;
    private int x;
    private int y;

    public Cell getCell(int x, int y) {
        return cell[x][y];
    }

    public GameMap(int n, int m) {
        cell = new Cell[n][m];
        this.x = n;
        this.y = m;

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cell[i][j] = new Cell(i, j);
            }
        }
    }

    public void setMapSoil(SoilInput soil) {
        Soil realSoil = SoilSetter.returnSoil(soil);

        for(PairInput coordinates : soil.sections) {
            int x = coordinates.x;
            int y = coordinates.y;
            cell[x][y].setSoil(realSoil);
        }
    }

    public void setMapPlant(PlantInput plant) {
        Plant realPlant = new Plant(plant.type, plant.name, plant.mass);

        for(PairInput coordinates : plant.sections) {
            int x = coordinates.x;
            int y = coordinates.y;
            cell[x][y].setPlant(realPlant);
        }
    }

    public void setMapAnimal(AnimalInput animal) {
        Animal realAnimal = AnimalSetter.returnAnimal(animal);

        for(PairInput coordinates : animal.sections) {
            int x = coordinates.x;
            int y = coordinates.y;
            cell[x][y].setAnimal(realAnimal);
        }
    }

    public void setMapWater(WaterInput water) {
        Water realWater = new Water(water.type, water.name, water.mass, water.salinity, water.pH, water.purity,
                                    water.turbidity, water.contaminantIndex, water.isFrozen);

        for(PairInput coordinates : water.sections) {
            int x = coordinates.x;
            int y = coordinates.y;
            cell[x][y].setWater(realWater);
        }
    }

    public void setMapAir(AirInput air) {
        Air realAir = AirSetter.returnAir(air);
        for(PairInput coordinates : air.sections) {
            int x = coordinates.x;
            int y = coordinates.y;
            cell[x][y].setAir(realAir);
        }
    }

    public void initializeRobot(Robot robot) {
        cell[0][0].setRobot(robot);
        robot.setX(0);
        robot.setY(0);
    }

    public int nrObj(int i, int j) {
        int count = 0;
        if (cell[i][j].getWater() != null) {
            count++;
        }
        if (cell[i][j].getPlant() != null) {
            count++;
        }
        if (cell[i][j].getAnimal() != null) {
            count++;
        }
        return count;
    }
}
