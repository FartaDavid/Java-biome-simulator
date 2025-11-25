package entities.animalType;

import entities.Animal;

/**
 * Represents a Parasite type of Animal.
 * Extends the base Animal class with specific behaviors for eating and organic matter interaction.
 */
public class Parasite extends Animal {

    private static final double STATIC_ATTACK_PROBABILITY = 9.0;
    private static final double ORGANIC_MATTER_INCREMENT = 0.5;

    /**
     * Constructs a new Parasite instance.
     *
     * @param type The type of the parasite.
     * @param name The name of the parasite.
     * @param mass The initial mass of the parasite.
     */
    public Parasite(final String type, final String name, final double mass) {
        super(type, name, mass);
    }

    /**
     * Consumes a specific amount of prey mass.
     * Updates the parasite's mass by adding the prey's mass.
     *
     * @param preyMass The mass of the prey consumed.
     */
    public void eatAnimal(final double preyMass) {
        double animalMass = getMass();
        animalMass += preyMass;
        setMass(animalMass);
    }

    /**
     * Calculates the attack probability for the parasite.
     *
     * @return The probability of a successful attack.
     */
    @Override
    public double attackProbability() {
        return STATIC_ATTACK_PROBABILITY;
    }

    /**
     * Adds a fixed amount of organic matter.
     *
     * @param organicMatter The initial amount of organic matter.
     * @return The calculated organic matter after addition.
     */
    public double addOrganicMatter(final double organicMatter) {
        return organicMatter + ORGANIC_MATTER_INCREMENT;
    }
}
