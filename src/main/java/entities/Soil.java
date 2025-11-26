package entities;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public abstract class Soil extends Entities {

    /** Increment for water retention. */
    private static final double WATER_INCREMENT = 0.1;
    /** Organic matter addition for k=2. */
    private static final double ORGANIC_ADD_HIGH = 0.8;
    /** Organic matter addition for k=1. */
    private static final double ORGANIC_ADD_LOW = 0.5;
    /** Quality threshold for good soil. */
    private static final double QUALITY_GOOD_THRESHOLD = 70.0;
    /** Quality threshold for moderate soil. */
    private static final double QUALITY_MODERATE_THRESHOLD = 40.0;

    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    private double quality;
    private double blockProbability;

    public Soil(final String type, final String name, final double mass,
                final double nitrogen, final double waterRetention,
                final double soilpH, final double organicMatter) {
        super(type, name, mass);
        this.nitrogen = nitrogen;
        this.waterRetention = waterRetention;
        this.soilpH = soilpH;
        this.organicMatter = organicMatter;
    }

    /**
     * Increases water retention by a fixed value.
     */
    public final void addWaterRetention() {
        waterRetention += WATER_INCREMENT;
    }

    /**
     * Adds organic matter based on factor k.
     * @param k The decision factor (1 or 2).
     */
    public final void addOrganicMatter(final int k) {
        if (k >= 2) {
            this.organicMatter += ORGANIC_ADD_HIGH;
        } else if (k == 1) {
            this.organicMatter += ORGANIC_ADD_LOW;
        }

    }

    /**
     * Calculates the soil quality score.
     * This method is abstract and must be implemented in derived classes.
     */
    public abstract void calculateQuality();

    /**
     * Determines soil quality based on the quality score.
     * @return A string representing the quality ("good", "moderate", "poor").
     */
    public final String qualitySoil() {
        if (quality >= QUALITY_GOOD_THRESHOLD) {
            return "good";
        }
        if (quality < QUALITY_GOOD_THRESHOLD && quality >= QUALITY_MODERATE_THRESHOLD) {
            return "moderate";
        }
        return "poor";
    }
}
