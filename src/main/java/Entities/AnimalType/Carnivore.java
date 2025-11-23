package Entities.AnimalType;

import Entities.Animal;

public class Carnivore extends Animal {
    public Carnivore(String type, String name, double mass) {
        super(type, name, mass);
    }

    public void eatAnimal(double preyMass) {
        double animalMass = getMass();
        animalMass += preyMass;
        setMass(animalMass);
    }

    @Override
    public double AttackProbability() {
        return 7.0;
    }
}
