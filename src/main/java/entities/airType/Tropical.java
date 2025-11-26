package entities.airType;

import entities.Air;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the Tropical air type entity.
 * Handles specific calculations for air quality involving CO2 levels
 * and rainfall events.
 */
@Getter @Setter
public final class Tropical extends Air {
    private static final double OXYGEN_MULTIPLIER = 2.0;
    private static final double HUMIDITY_FACTOR = 0.5;
    private static final double CO2_PENALTY = 0.01;
    private static final double MAX_PERCENTAGE = 100.0;
    private static final double TOXICITY_DENOMINATOR = 82.0;
    private static final double RAINFALL_BONUS = 0.3;
    private static final double TOXICITY = 0.8;

    private double co2Level;

    /**
     * Calculates the air quality for the tropical environment.
     * Formula: Oxygen * 2 + Humidity * 0.5 - CO2 * 0.01.
     *
     * @return The calculated air quality value.
     */
    @Override
    public double calculateAirQuality() {
        return super.getOxygenLevel() * OXYGEN_MULTIPLIER
                + super.getHumidity() * HUMIDITY_FACTOR
                - co2Level * CO2_PENALTY;
    }

    /**
     * Constructs a new Tropical air entity.
     * Initializes the air properties and calculates initial toxicity.
     *
     * @param type        The type of the air.
     * @param name        The name of the air zone.
     * @param mass        The mass of the air volume.
     * @param humidity    The humidity level.
     * @param temperature The temperature level.
     * @param oxygenLevel The oxygen level.
     * @param co2Level    The CO2 level in the air.
     */
    public Tropical(final String type, final String name, final double mass,
                    final double humidity, final double temperature,
                    final double oxygenLevel, final double co2Level) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        // Presupunând că roundTwoDecimals există în clasa părinte Entities
        this.co2Level = super.roundTwoDecimals(co2Level);

        normalizeQuality(calculateAirQuality());

        double toxicityAQ = MAX_PERCENTAGE
                * (1.0 - calculateAirQuality() / TOXICITY_DENOMINATOR);
        getToxicity(toxicityAQ);
    }

    /**
     * Simulates a rainfall event.
     * Improves air quality based on the amount of rainfall.
     *
     * @param rainfall The amount of rain.
     */
    public void rainfall(final double rainfall) {
        double quality = super.getAirQuality();
        quality += rainfall * RAINFALL_BONUS;

        super.setChangedAir(quality);
    }

    public boolean isToxic() {
        return super.getToxicity() > TOXICITY * TOXICITY_DENOMINATOR;
    }
}
