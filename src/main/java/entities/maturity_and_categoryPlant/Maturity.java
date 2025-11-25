package entities.maturity_and_categoryPlant;

/**
 * Enum representing the various maturity stages of a plant.
 * Each stage has a specific oxygen bonus associated with it.
 */
public enum Maturity {
    /** Represents a young plant. */
    Young(0.2),
    /** Represents a mature plant. */
    Mature(0.7),
    /** Represents an old plant. */
    Old(0.4),
    /** Represents a dead plant. */
    Dead(0);

    private final double oxygenBonus;

    /**
     * Constructor for the Maturity enum.
     *
     * @param oxygenBonus The oxygen bonus value associated with the maturity stage.
     */
    Maturity(final double oxygenBonus) {
        this.oxygenBonus = oxygenBonus;
    }

    /**
     * Retrieves the oxygen bonus for this maturity stage.
     *
     * @return The oxygen bonus as a double.
     */
    public double getMaturity() {
        return this.oxygenBonus;
    }

    /**
     * Returns the Maturity enum constant corresponding to the given string type.
     *
     * @param type The name of the maturity stage.
     * @return The Maturity enum constant.
     */
    public static Maturity getMaturityByType(final String type) {
        return Maturity.valueOf(type);
    }
}
