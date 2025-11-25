package entities.airType;

import entities.Air;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the Temperate (Temperat) air type entity.
 * Handles calculations involving pollen levels and seasonal changes.
 */
@Getter @Setter
public final class Temperat extends Air {
    private static final double OXYGEN_MULTIPLIER = 2.0;
    private static final double HUMIDITY_COEFFICIENT = 0.7;
    private static final double POLLEN_COEFFICIENT = 0.1;
    private static final double MAX_PERCENTAGE = 100.0;
    private static final double TOXICITY_DENOMINATOR = 84.0;
    private static final double SPRING_PENALTY = 15.0;

    private double pollenLevel;

    /**
     * Calculates the air quality for the temperate environment.
     * Formula: Oxygen * 2 + Humidity * 0.7 - Pollen * 0.1.
     *
     * @return The calculated air quality value.
     */
    @Override
    public double calculateAirQuality() {
        return super.getOxygenLevel() * OXYGEN_MULTIPLIER
                + super.getHumidity() * HUMIDITY_COEFFICIENT
                - pollenLevel * POLLEN_COEFFICIENT;
    }

    /**
     * Constructs a new Temperat air entity.
     * Initializes the air properties and calculates initial toxicity.
     *
     * @param type          The type of the air.
     * @param name          The name of the air zone.
     * @param mass          The mass of the air volume.
     * @param humidity      The humidity level.
     * @param temperature   The temperature level.
     * @param oxygenLevel   The oxygen level.
     * @param pollenLevel   The pollen level in the air.
     */
    public Temperat(final String type, final String name, final double mass,
                    final double humidity, final double temperature,
                    final double oxygenLevel, final double pollenLevel) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.pollenLevel = pollenLevel;

        normalizeQuality(calculateAirQuality());

        double toxicityAQ = MAX_PERCENTAGE
                * (1.0 - calculateAirQuality() / TOXICITY_DENOMINATOR);
        getToxicity(toxicityAQ);
    }

    /**
     * Simulates the change of seasons.
     * Specifically checks for "Spring" to apply a penalty to air quality due to pollen.
     *
     * @param season The name of the new season.
     */
    public void newSeason(final String season) {
        double quality = getAirQuality();
        double seasonPenalty = season.equalsIgnoreCase("Spring") ? SPRING_PENALTY : 0;
        quality -= seasonPenalty;

        super.setChangedAir(quality);
    }
}
