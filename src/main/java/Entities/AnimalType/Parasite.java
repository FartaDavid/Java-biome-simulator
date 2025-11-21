package Entities.AnimalType;

import Entities.Animal;

public class Parasite extends Animal {
    public Parasite(String type, String name, double mass) {
        super(type, name, mass);
    }

    public void eatAnimal(double preyMass) {
        double animalMass = getMass();
        animalMass += preyMass;
        setMass(animalMass);
    }

    @Override
    public double AttackProbability() {
        return 9.0;
    }

    public double addOrganicMatter(double organicMatter) {
        return organicMatter + 0.5;
    }
}
