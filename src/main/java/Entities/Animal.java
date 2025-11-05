package Entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Animal extends Entities {
    private String status;

    public Animal(String name, double mass) {
        super(name, mass);
    }

    public double AttackProbability() {
        return 0;
    }

    public void WaterAndPlant(Water water, Plant plant, Soil soil) {
        double waterMass = water.getMass();
        double plantMass = plant.getMass();

        double waterToDrink = Math.min(super.getMass() * 0.008, waterMass);
        waterMass -= waterToDrink;
        water.setMass(waterMass);

        double mass = super.getMass();
        mass += waterToDrink + plantMass;
        super.setMass(mass);

        double organicMatter = soil.getOrganicMatter();
        organicMatter += 0.8;
        soil.setOrganicMatter(organicMatter);
    }

    public void PlantFirst(Plant plant, Soil soil) {
        double plantMass = plant.getMass();
        double mass = super.getMass();
        mass += plantMass;
        super.setMass(mass);

        double organicMatter = soil.getOrganicMatter();
        organicMatter += 0.5;
        soil.setOrganicMatter(organicMatter);
    }

    public void WaterFirst(Water water, Soil soil) {
        double mass = super.getMass();
        double waterMass = water.getMass();
        double waterToDrink = Math.min(mass * 0.08, waterMass);

        waterMass -= waterToDrink;
        water.setMass(waterMass);

        mass += waterToDrink;
        super.setMass(mass);

        double soilOrganicMatter = soil.getOrganicMatter();
        soilOrganicMatter += 0.5;
        soil.setOrganicMatter(soilOrganicMatter);
    }
}