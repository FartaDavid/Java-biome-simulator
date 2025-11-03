package Entities.AnimalType;

import Entities.Animal;

public class Herbivores extends Animal {
    public Herbivores(String name, double mass, String status) {
        super(name, mass, status);
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
        return 1.5;
    }
}
