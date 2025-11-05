package Entities;

import fileio.*;

import java.util.List;

public class MapManager {

    public void setEntitites(SimulationInput input, GameMap map) {
        // iau entitatile din input
        List<SoilInput> soils = input.territorySectionParams.soil;
        List<PlantInput> plants = input.territorySectionParams.plants;
        List<AnimalInput> animals = input.territorySectionParams.animals;
        List<WaterInput> water = input.territorySectionParams.water;
        List<AirInput> air = input.territorySectionParams.air;

        // le pun pe harta pe fiecare
        for(SoilInput soil : soils) {
            map.setMapSoil(soil);
        }
        for(PlantInput plant : plants) {
            map.setMapPlant(plant);
        }
        for(AnimalInput animal : animals) {
            map.setMapAnimal(animal);
        }
        for(WaterInput Water : water) {
            map.setMapWater(Water);
        }
        for(AirInput Air : air) {
            map.setMapAir(Air);
        }
    }
}
