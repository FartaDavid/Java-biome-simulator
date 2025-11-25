package entities.animalType;

import entities.Animal;

/**
 * Represents a Carnivore type of Animal.
 * Extends the base Animal class with specific eating and attack behaviors.
 */
public class Carnivore extends Animal {

    private static final double STATIC_ATTACK_PROBABILITY = 7.0;

    /**
     * Constructs a new Carnivore instance.
     *
     * @param type The type of the carnivore.
     * @param name The name of the carnivore.
     * @param mass The initial mass of the carnivore.
     */
    public Carnivore(final String type, final String name, final double mass) {
        super(type, name, mass);
    }

    /**
     * Consumes a specific amount of prey mass.
     * Updates the carnivore's mass by adding the prey's mass.
     *
     * @param preyMass The mass of the prey consumed.
     */
    public void eatAnimal(final double preyMass) {
        double animalMass = getMass();
        animalMass += preyMass;
        setMass(animalMass);
    }

    /**
     * Calculates the attack probability for the carnivore.
     *
     * @return The probability of a successful attack.
     */
    @Override
    public double attackProbability() {
        return STATIC_ATTACK_PROBABILITY;
    }
}
