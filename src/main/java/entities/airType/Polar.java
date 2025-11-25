package entities.airType;

import entities.Air;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a type of cold air with polar characteristics.
 * This class is not intended for extension; override with care.
 */
@Setter
@Getter
public class Polar extends Air {

    /** Ice crystal concentration in the polar air mass. */
    private double iceCrystalConcentration;

    // -------------------- CONSTANTE PENTRU MAGIC NUMBERS --------------------
    private static final double BASE_QUALITY_BONUS = 100.0;
    private static final double ICE_CRYSTAL_FACTOR = 0.05;
    private static final double MAX_AQ_VALUE = 142.0;
    private static final double TOXICITY_BASE = 100.0;
    private static final double STORM_PENALTY_FACTOR = 0.2;
    // -------------------------------------------------------------------------

    /**
     * Calculates the air quality for polar-type conditions.
     *
     * @return computed air quality value
     */
    public double calculateAirQuality() {
        return super.getOxygenLevel() * 2
                + BASE_QUALITY_BONUS
                - Math.abs(super.getTemperature())
                - iceCrystalConcentration * ICE_CRYSTAL_FACTOR;
    }

    /**
     * Constructs a Polar air mass instance.
     *
     * @param type                     type of the air mass
     * @param name                     name of the air mass
     * @param mass                     mass value
     * @param humidity                 humidity value
     * @param temperature              temperature value
     * @param oxygenLevel              oxygen level
     * @param iceCrystalConcentration  concentration of ice crystals
     */
    public Polar(final String type,
                 final String name,
                 final double mass,
                 final double humidity,
                 final double temperature,
                 final double oxygenLevel,
                 final double iceCrystalConcentration) {

        super(type, name, mass, humidity, temperature, oxygenLevel);

        this.iceCrystalConcentration = iceCrystalConcentration;

        normalizeQuality(calculateAirQuality());

        double toxicityAQ =
                TOXICITY_BASE * (1.0 - calculateAirQuality() / MAX_AQ_VALUE);

        getToxicity(toxicityAQ);
    }

    /**
     * Applies the effect of a polar storm on current air quality.
     *
     * @param windSpeed intensity of the storm wind
     */
    public void polarStorm(final double windSpeed) {
        double quality = getAirQuality();
        quality -= windSpeed * STORM_PENALTY_FACTOR;

        super.setChangedAir(quality);
    }
}
