package Entities.AnimalType;

import Entities.Animal;

public class Herbivores extends Animal {
    public Herbivores(String type, String name, double mass) {
        super(type, name, mass);
    }

    @Override
    public double AttackProbability() {
        return 1.5;
    }
}
