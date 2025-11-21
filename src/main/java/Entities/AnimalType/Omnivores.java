package Entities.AnimalType;

import Entities.Animal;

public class Omnivores extends Animal {
    public Omnivores(String type, String name, double mass) {
        super(type, name, mass);
    }

    @Override
    public double AttackProbability() {
        return 4.0;
    }
}
