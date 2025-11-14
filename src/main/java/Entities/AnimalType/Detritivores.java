package Entities.AnimalType;

import Entities.Animal;

public class Detritivores extends Animal {
    public Detritivores(String type, String name, double mass) {
        super(type, name, mass);
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
        return 1.0;
    }
}
