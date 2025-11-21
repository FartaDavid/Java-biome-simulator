package Entities.AnimalType;

import Entities.Animal;

public class Detritivores extends Animal {
    public Detritivores(String type, String name, double mass) {
        super(type, name, mass);
    }

    @Override
    public double AttackProbability() {
        return 1.0;
    }
}
