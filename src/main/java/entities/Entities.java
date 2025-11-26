package entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Entities {

    /**
     * The multiplication factor for rounding to two decimal places.
     */
    private static final double ROUNDING_FACTOR = 100.0;

    private double mass;
    private String name;
    private String type;
    private boolean scanned = false;

    public Entities(final String type, final String name, final double mass) {
        this.name = name;
        this.mass = mass;
        this.type = type;
    }

    /**
     * Marks the entity as scanned.
     * This method is final to respect the Design for Extension rule.
     */
    public final void objectScanned() {
        scanned = true;
    }

    /**
     * Rounds a double value to two decimal places.
     * @param value The value to be rounded.
     * @return The rounded value.
     */
    public final double roundTwoDecimals(final double value) {
        return Math.round(value * ROUNDING_FACTOR) / ROUNDING_FACTOR;
    }
}
