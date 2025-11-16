package Entities.Maturity_CatPlant;

import lombok.Getter;

@Getter
public enum CategoriePlanta {
    FloweringPlants(6.0, 0.9),
    GymnospermsPlants(0.0, 0.6),
    Ferns(0.0, 0.3),
    Mosses(0.8, 0.4),
    Algae(0.5, 0.2);

    private final double oxigen;
    private final double probability;

    CategoriePlanta(double oxigen, double probability) {
        this.oxigen = oxigen;
        this.probability = probability;
    }

    public static CategoriePlanta getCategoryByType(String type) {
        return CategoriePlanta.valueOf(type);
    }
}