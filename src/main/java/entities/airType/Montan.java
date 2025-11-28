package entities.airType;

import entities.Air;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the Mountain (Montan) air type entity.
 * Handles specific calculations for air quality involving altitude
 * and hiker activity.
 */
@Getter @Setter
public final class Montan extends Air {
    private static final double ALTITUDE_SCALE = 1000.0;
    private static final double ALTITUDE_FACTOR = 0.5;
    private static final double OXYGEN_MULTIPLIER = 2.0;
    private static final double HUMIDITY_FACTOR = 0.6;
    private static final double MAX_PERCENTAGE = 100.0;
    private static final double TOXICITY_DENOMINATOR = 78.0;
    private static final double HIKER_PENALTY = 0.1;
    private static final double TOXICITY = 0.8;

    private double altitude;

    /**
     * Calculates the air quality for the mountain environment.
     * Formula involves oxygen levels adjusted by altitude and humidity.
     *
     * @return The calculated air quality value.
     */
    @Override
    public double calculateAirQuality() {
        double oxygenFactor = super.getOxygenLevel()
                - (altitude / ALTITUDE_SCALE * ALTITUDE_FACTOR);
        return (oxygenFactor * OXYGEN_MULTIPLIER)
                + super.getHumidity() * HUMIDITY_FACTOR;
    }

    @Override
    public double calculateToxicity() {
        double toxicityAQ = MAX_PERCENTAGE
                * (1.0 - getAirQuality() / TOXICITY_DENOMINATOR);
        return toxicityAQ;
    }

    /**
     * Constructs a new Montan air entity.
     * Initializes the air properties and calculates initial toxicity.
     *
     * @param type          The type of the air.
     * @param name          The name of the air zone.
     * @param mass          The mass of the air volume.
     * @param humidity      The humidity level.
     * @param temperature   The temperature level.
     * @param oxygenLevel   The oxygen level.
     * @param altitude      The altitude of the mountain air.
     */
    public Montan(final String type, final String name, final double mass,
                  final double humidity, final double temperature,
                  final double oxygenLevel, final double altitude) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.altitude = altitude;

        normalizeQuality(calculateAirQuality());

        getToxicity(calculateToxicity());
    }

    /**
     * Simulates the impact of hikers on air quality.
     * Reduces air quality based on the number of hikers.
     *
     * @param numberOfHikers The number of hikers present.
     */
    public void peopleHiking(final double numberOfHikers) {
        double quality = super.getAirQuality();
        quality -= numberOfHikers * HIKER_PENALTY;

        super.setChangedAir(quality);
    }

    public boolean isToxic() {
        return super.getToxicity() > TOXICITY * TOXICITY_DENOMINATOR;
    }
}
