package map;

import entities.Air;
import entities.Animal;
import entities.Plant;
import entities.Soil;
import entities.Water;
import entities.setters.AirSetter;
import entities.setters.AnimalSetter;
import entities.setters.SoilSetter;
import fileio.AirInput;
import fileio.AnimalInput;
import fileio.PairInput;
import fileio.PlantInput;
import fileio.SoilInput;
import fileio.WaterInput;

/**
 * Handles the population of the GameMap from input data.
 */
public final class MapLoader {
    private final GameMap map;

    public MapLoader(final GameMap map) {
        this.map = map;
    }

    /**
     * Populates the map with soil based on input.
     *
     * @param soil The soil input data.
     */
    public void setMapSoil(final SoilInput soil) {
        for (PairInput coordinates : soil.getSections()) {
            Soil realSoil = SoilSetter.returnSoil(soil);
            int coordX = coordinates.getX();
            int coordY = coordinates.getY();
            map.getCell(coordX, coordY).setSoil(realSoil);
        }
    }

    /**
     * Populates the map with plants based on input.
     *
     * @param plant The plant input data.
     */
    public void setMapPlant(final PlantInput plant) {
        for (PairInput coordinates : plant.getSections()) {
            Plant realPlant = new Plant(plant.getType(), plant.getName(), plant.getMass());
            int coordX = coordinates.getX();
            int coordY = coordinates.getY();
            map.getCell(coordX, coordY).setPlant(realPlant);
        }
    }

    /**
     * Populates the map with animals based on input.
     *
     * @param animal The animal input data.
     */
    public void setMapAnimal(final AnimalInput animal) {
        for (PairInput coordinates : animal.getSections()) {
            Animal realAnimal = AnimalSetter.returnAnimal(animal);
            int coordX = coordinates.getX();
            int coordY = coordinates.getY();
            map.getCell(coordX, coordY).setAnimal(realAnimal);
        }
    }

    /**
     * Populates the map with water based on input.
     *
     * @param water The water input data.
     */
    public void setMapWater(final WaterInput water) {
        for (PairInput coordinates : water.sections) {
            Water realWater = new Water(water.type, water.name, water.mass,
                    water.salinity, water.pH, water.purity,
                    water.turbidity, water.contaminantIndex, water.isFrozen);

            int coordX = coordinates.getX();
            int coordY = coordinates.getY();
            map.getCell(coordX, coordY).setWater(realWater);
        }
    }

    /**
     * Populates the map with air based on input.
     *
     * @param air The air input data.
     */
    public void setMapAir(final AirInput air) {
        for (PairInput coordinates : air.getSections()) {
            Air realAir = AirSetter.returnAir(air);
            int coordX = coordinates.getX();
            int coordY = coordinates.getY();
            map.getCell(coordX, coordY).setAir(realAir);
        }
    }
}
