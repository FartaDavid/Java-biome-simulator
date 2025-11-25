package entities.maturity_and_categoryPlant;

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

    CategoriePlanta(final double oxigen, final double probability) {
        this.oxigen = oxigen;
        this.probability = probability;
    }

    /**
     * Returneaza categoria plantei in functie de numele tipului (String).
     *
     * @param type Tipul plantei ca String.
     * @return Valoarea enum corespunzatoare.
     */
    public static CategoriePlanta getCategoryByType(final String type) {
        return CategoriePlanta.valueOf(type);
    }
}
