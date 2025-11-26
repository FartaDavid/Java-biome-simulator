package entities.setters;

import entities.Animal;
import entities.animalType.Carnivore;
import entities.animalType.Detritivores;
import entities.animalType.Herbivores;
import entities.animalType.Omnivores;
import entities.animalType.Parasite;
import fileio.AnimalInput;

/**
 * Utility class for creating specific Animal entities based on input data.
 */
public final class AnimalSetter {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private AnimalSetter() {
    }

    /**
     * Returns a specific Animal object based on the input type.
     *
     * @param animal The input data containing details about the animal.
     * @return An instance of a specific Animal subclass, or null if the type is unknown.
     */
    public static Animal returnAnimal(final AnimalInput animal) {
        switch (animal.getType()) {
            case "Carnivores":
                return new Carnivore(animal.getType(),
                        animal.getName(), animal.getMass());

            case "Detritivores":
                return new Detritivores(animal.getType(),
                        animal.getName(), animal.getMass());

            case "Herbivores":
                return new Herbivores(animal.getType(),
                        animal.getName(), animal.getMass());

            case "Omnivores":
                return new Omnivores(animal.getType(),
                        animal.getName(), animal.getMass());

            case "Parasites":
                return new Parasite(animal.getType(),
                        animal.getName(), animal.getMass());

            default:
                return null;
        }
    }
}
