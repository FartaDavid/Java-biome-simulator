package Entities.Maturity_CatPlant;

public enum Maturity {
    Young(0.2),
    Mature(0.7),
    Old(0.4);

    private final double o2_bonus;

    private Maturity(double o2_bonus) {
        this.o2_bonus = o2_bonus;
    }
    public double getMaturity() {
        return this.o2_bonus;
    }
}
