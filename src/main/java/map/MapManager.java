package map;

import fileio.AirInput;
import fileio.AnimalInput;
import fileio.PlantInput;
import fileio.SimulationInput;
import fileio.SoilInput;
import fileio.WaterInput;
import java.util.List;

/**
 * Manages the initialization and population of the game map entities.
 */
public class MapManager {

    /**
     * Populates the game map with entities retrieved from the simulation input.
     * Iterates through lists of soils, plants, animals, water, and air
     * and places them onto the provided map.
     *
     * @param input The simulation input containing territory parameters.
     * @param map   The game map instance to be populated.
     */
    public void setEntitites(final SimulationInput input, final GameMap map) {
        // taking entities from input
        List<SoilInput> soils = input.getTerritorySectionParams().getSoil();
        List<PlantInput> plants = input.getTerritorySectionParams().getPlants();
        List<AnimalInput> animals = input.getTerritorySectionParams().getAnimals();
        List<WaterInput> water = input.getTerritorySectionParams().getWater();
        List<AirInput> air = input.getTerritorySectionParams().getAir();

        MapLoader mapLoader = new MapLoader(map);

        // mapping them onto the map
        for (SoilInput soil : soils) {
            mapLoader.setMapSoil(soil);
        }
        for (PlantInput plant : plants) {
            mapLoader.setMapPlant(plant);
        }
        for (AnimalInput animal : animals) {
            mapLoader.setMapAnimal(animal);
        }
        for (WaterInput waterItem : water) {
            mapLoader.setMapWater(waterItem);
        }
        for (AirInput airItem : air) {
            mapLoader.setMapAir(airItem);
        }
    }
}
