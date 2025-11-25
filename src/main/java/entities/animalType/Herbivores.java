package entities.animalType;

import entities.Animal;

public class Herbivores extends Animal {

    /**
     * Valoarea probabilitatii de atac pentru ierbivore.
     * Elimina eroarea de 'Magic Number'.
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
