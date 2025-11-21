package Entities;

import Entities.AnimalType.*;
import fileio.AnimalInput;

public class AnimalSetter {
    public static Animal returnAnimal(AnimalInput animal) {
        switch(animal.getType()) {
            case "Carnivores":
                Carnivore carnivore = new Carnivore(animal.getType(), animal.getName(), animal.getMass());
                return carnivore;
            case "Detritivores":
                Detritivores detritivores = new Detritivores(animal.getType(), animal.getName(), animal.getMass());
                return detritivores;
            case "Herbivores":
                Herbivores herbivores = new Herbivores(animal.getType(), animal.getName(), animal.getMass());
                return herbivores;
            case "Omnivores":
                Omnivores omnivores = new Omnivores(animal.getType(), animal.getName(), animal.getMass());
                return omnivores;
            case "Parasites":
                Parasite parasite = new Parasite(animal.getType(), animal.getName(), animal.getMass());
                return parasite;
            default:
                return null;
        }
    }
}
