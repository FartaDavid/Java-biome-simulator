package Entities;

import fileio.*;

import java.util.List;

public class MapManager {

    public void setEntitites(SimulationInput input, GameMap map) {
        // iau entitatile din input
        List<SoilInput> soils = input.getTerritorySectionParams().getSoil();
        List<PlantInput> plants = input.getTerritorySectionParams().getPlants();
        List<AnimalInput> animals = input.getTerritorySectionParams().getAnimals();
        List<WaterInput> water = input.getTerritorySectionParams().getWater();
        List<AirInput> air = input.getTerritorySectionParams().getAir();

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
