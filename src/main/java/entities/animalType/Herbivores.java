package entities.animalType;

import entities.Animal;

/**
 * Represents the Herbivores animal type.
 * Defines specific behavior for attack probability.
 */
public class Herbivores extends Animal {

    /**
     * The attack probability value for herbivores.
     * Removes the 'magic number' error.
     */
    private static final double HERBIVORE_ATTACK_PROBABILITY = 1.5;

    public Herbivores(final String type, final String name, final double mass) {
        super(type, name, mass);
    }

    @Override
    public final double attackProbability() {
        return HERBIVORE_ATTACK_PROBABILITY;
    }
}
