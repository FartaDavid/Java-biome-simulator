package Entities.Maturity_CatPlant;

public enum CategoriePlanta {
    FloweringPlants(6.0, 0.9),
    GymnospermsPlants(0.0, 0.6),
    Ferns(0.0, 0.3),
    Moses(0.8, 0.4),
    Algae(0.5, 0.2);

    private final double oxigen;
    private final double probability;

    private CategoriePlanta(double oxigen, double probability) {
        this.oxigen = oxigen;
        this.probability = probability;
    }
    public double getOxigen() {
        return this.oxigen;
    }

    public double getProbability() {
        return this.probability;
    }
}