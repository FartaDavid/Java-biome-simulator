package entities.airType;

import entities.Air;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the Desert air type entity.
 * Handles specific calculations for air quality involving dust particles
 * and desert storm events.
 */
@Setter @Getter
public final class Desert extends Air {
    private static final double OXYGEN_MULTIPLIER = 2.0;
    private static final double DUST_COEFFICIENT = 0.2;
    private static final double TEMP_COEFFICIENT = 0.3;
    private static final double MAX_PERCENTAGE = 100.0;
    private static final double TOXICITY_DENOMINATOR = 65.0;
    private static final double STORM_PENALTY = 30.0;
    private static final double TOXICITY = 0.8;

    private double dustParticles;
    private boolean desertStorm = false;

    /**
     * Calculates the air quality for the desert environment.
     * Formula: Oxygen * 2 - Dust * 0.2 - Temperature * 0.3.
     *
     * @return The calculated air quality value.
     */
    @Override
    public double calculateAirQuality() {
        return super.getOxygenLevel() * OXYGEN_MULTIPLIER
                - dustParticles * DUST_COEFFICIENT
                - super.getTemperature() * TEMP_COEFFICIENT;
    }

    /**
     * Constructs a new Desert air entity.
     * Initializes the air properties and calculates initial toxicity.
     *
     * @param type          The type of the air.
     * @param name          The name of the air zone.
     * @param mass          The mass of the air volume.
     * @param humidity      The humidity level.
     * @param temperature   The temperature level.
     * @param oxygenLevel   The oxygen level.
     * @param dustParticles The concentration of dust particles.
     */
    public Desert(final String type, final String name, final double mass,
                  final double humidity, final double temperature,
                  final double oxygenLevel, final double dustParticles) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.dustParticles = dustParticles;

        normalizeQuality(calculateAirQuality());

        double toxicityAQ = MAX_PERCENTAGE
                * (1.0 - calculateAirQuality() / TOXICITY_DENOMINATOR);
        getToxicity(toxicityAQ);
    }

    /**
     * Simulates a desert storm event.
     * Adjusts the air quality if a storm occurs.
     *
     * @param desertstorm A boolean indicating if the storm is active (affects calculation).
     */
    public void desertStorm(final boolean desertstorm) {
        double quality = super.getAirQuality();
        quality -= desertstorm ? STORM_PENALTY : 0;

        this.desertStorm = true;
        super.setChangedAir(quality);
    }

    public boolean isToxic() {
        return super.getToxicity() > TOXICITY * TOXICITY_DENOMINATOR;
    }
}
