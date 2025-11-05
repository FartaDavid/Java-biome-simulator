package Entities.AnimalType;

import Entities.Animal;

public class Carnivore extends Animal {
    public Carnivore(String name, double mass) {
        super(name, mass);
    }

    public void eatAnimal(double preyMass) {
        double animalMass = getMass();
        animalMass += preyMass;
        setMass(animalMass);
    }

    public void drinkWater(double waterToDrink) {
        double animalMass = getMass();
        animalMass += waterToDrink;
        setMass(animalMass);
    }

    public void eatPlant(double plantMass) {
        double animalMass = getMass();
        animalMass += plantMass;
        setMass(animalMass);
    }

    @Override
    public double AttackProbability() {
        return 7.0;
    }

    public double addOrganicMatter(double organicMatter) {
        return organicMatter + 0.5;
    }
}
