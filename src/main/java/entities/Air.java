package entities;

import lombok.Getter;
import lombok.Setter;

/**
 * Abstract class representing the Air entity.
 * It manages properties like humidity, temperature, oxygen level, and air quality.
 */
@Getter @Setter
public abstract class Air extends Entities {
    private static final double MAX_QUALITY = 100.0;
    private static final double MIN_QUALITY = 0.0;
    private static final double ROUNDING_FACTOR = 100.0;
    private static final double GOOD_QUALITY_THRESHOLD = 70.0;
    private static final double MODERATE_QUALITY_THRESHOLD = 40.0;
    private static final double HUMIDITY_INCREMENT = 0.1;
    private static final int WEATHER_CHANGE_DURATION = 2;

    private double humidity;
    private double temperature;
    private double oxygenLevel;
    private double airQuality;
    // using changedAir to store the modified air quality during weather changes
    private double changedAir;
    private double toxicity;
    private int changeWeather; // timer for weather change

    public Air(final String type, final String name, final double mass, final double humidity,
               final double temperature, final double oxygenLevel) {
        super(type, name, mass);
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
    }

    /**
     * Calculates the air quality based on specific implementation logic.
     *
     * @return the calculated air quality as a double.
     */
    public abstract double calculateAirQuality();

    /**
     * Normalizes the air quality score to be between 0 and 100 and sets it.
     *
     * @param quality the air quality score to be normalized.
     */
    public void normalizeQuality(final double quality) {
        // normalize score
        double localQuality = Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, quality));
        // round score
        localQuality = Math.round(localQuality * ROUNDING_FACTOR) / ROUNDING_FACTOR;
        if (localQuality >= MAX_QUALITY) {
            localQuality = MAX_QUALITY;
        }
        this.airQuality = localQuality;
    }

    /**
     * Calculates and sets the toxicity level based on the provided air quality index.
     *
     * @param toxicityAQ the raw toxicity value to be processed.
     */
    public void getToxicity(final double toxicityAQ) {
        // final result toxicity
        double localToxicity = Math.round(toxicityAQ * ROUNDING_FACTOR) / ROUNDING_FACTOR;
        // normalize score
        localToxicity = Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, localToxicity));
        // rounding score
        localToxicity = Math.round(localToxicity * ROUNDING_FACTOR) / ROUNDING_FACTOR;
        this.toxicity = localToxicity;
    }

    /**
     * Increases the humidity level by a fixed increment.
     */
    public void addHumidity() {
        humidity += HUMIDITY_INCREMENT;
    }

    /**
     * Sets the timer for weather change duration.
     */
    public void setchangeWeather() {
        changeWeather = WEATHER_CHANGE_DURATION;
    }

    /**
     * Determines the qualitative description of the air quality.
     *
     * @return a String representing the quality ("good", "moderate", or "poor").
     */
    public String airQuality() {
        if (airQuality >= GOOD_QUALITY_THRESHOLD) {
            return "good";
        }
        if (airQuality < GOOD_QUALITY_THRESHOLD && airQuality >= MODERATE_QUALITY_THRESHOLD) {
            return "moderate";
        }
        return "poor";
    }

    /**
     * Abstract method to determine if the air is toxic.
     *
     * @return true if the air is toxic, false otherwise.
     */
    public abstract boolean isToxic();
}
