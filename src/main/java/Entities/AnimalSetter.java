package Entities;

import Entities.AnimalType.*;
import fileio.AnimalInput;

public class AnimalSetter {
    public static Animal returnAnimal(AnimalInput animal) {
        switch(animal.type) {
            case "Carnivore":
                Carnivore carnivore = new Carnivore(animal.name, animal.mass);
                return carnivore;
            case "Detritivores":
                Detritivores detritivores = new Detritivores(animal.name, animal.mass);
                return detritivores;
            case "Herbivores":
                Herbivores herbivores = new Herbivores(animal.name, animal.mass);
                return herbivores;
            case "Omnivores":
                Omnivores omnivores = new Omnivores(animal.name, animal.mass);
                return omnivores;
            case "Parasite":
                Parasite parasite = new Parasite(animal.name, animal.mass);
                return parasite;
            default:
                return null;
        }
    }
}
