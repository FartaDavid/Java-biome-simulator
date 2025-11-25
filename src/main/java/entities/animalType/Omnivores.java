package entities.animalType;

import entities.Animal;

/**
 * Represents the Omnivores animal type.
 * Defines specific behavior for attack probability.
 */
public final class Omnivores extends Animal {
    private static final double ATTACK_CHANCE = 4.0;

    /**
     * Constructs a new Omnivores instance.
     *
     * @param type The type of the animal.
     * @param name The name of the animal.
     * @param mass The mass of the animal.
     */
    public Omnivores(final String type, final String name, final double mass) {
        super(type, name, mass);
    }

    /**
     * Returns the probability of this animal attacking.
     *
     * @return The attack probability value.
     */
    @Override
    public double attackProbability() {
        return ATTACK_CHANCE;
    }
}
