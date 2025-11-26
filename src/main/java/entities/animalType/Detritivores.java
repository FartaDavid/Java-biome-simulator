package entities.animalType;

import entities.Animal;

/**
 * Represents the Detritivores animal type.
 * Defines specific behavior for attack probability.
 */
public final class Detritivores extends Animal {

    public Detritivores(final String type, final String name, final double mass) {
        super(type, name, mass);
    }

    @Override
    public double attackProbability() {
        return 1.0;
    }
}
