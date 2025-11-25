package entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Entities {

    /**
     * Factorul de multiplicare pentru rotunjirea la doua zecimale.
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
     * Marcheaza entitatea ca fiind scanata.
     * Metoda este finala pentru a respecta regula de Design for Extension.
     */
    public final void objectScanned() {
        scanned = true;
    }

    protected final double roundTwoDecimals(final double value) {
        return Math.round(value * ROUNDING_FACTOR) / ROUNDING_FACTOR;
    }
}
