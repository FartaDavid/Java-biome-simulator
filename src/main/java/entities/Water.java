package entities;

import lombok.Getter;
import lombok.Setter;

/**
 * Represents the Water entity in the simulation.
 * Manages properties like salinity, purity, and quality calculation.
 */
@Getter @Setter
public final class Water extends Entities {
    private static final double MAX_PERCENTAGE = 100.0;
    private static final double IDEAL_PH = 7.5;
    private static final double MAX_SALINITY = 350.0;

    // Weights for quality calculation
    private static final double WEIGHT_PURITY = 0.3;
    private static final double WEIGHT_PH = 0.2;
    private static final double WEIGHT_SALINITY = 0.15;
    private static final double WEIGHT_TURBIDITY = 0.1;
    private static final double WEIGHT_CONTAMINANT = 0.15;
    private static final double WEIGHT_FROZEN = 0.2;

    // Quality thresholds
    private static final double QUALITY_GOOD = 70.0;
    private static final double QUALITY_MODERATE = 40.0;

    private static final int INITIAL_TIME = 2;

    private double salinity;
    private double pH;
    private double purity;
    private double turbidity;
    private double contaminantIndex;
    private boolean isFrozen;
    private int time = INITIAL_TIME; // fac un timer pentru a adauga humidity si waterRetention
    private double quality;

    /**
     * Constructor for Water entity.
     * @param type The type of entity.
     * @param name The name of the water body.
     * @param mass The mass of the water.
     * @param salinity Salinity level.
     * @param pH pH level.
     * @param purity Purity percentage.
     * @param turbidity Turbidity level.
     * @param contaminantIndex Contamination level.
     * @param isFrozen Whether the water is frozen.
     */
    public Water(final String type,
                 final String name,
                 final double mass,
                 final double salinity,
                 final double pH,
                 final double purity,
                 final double turbidity,
                 final double contaminantIndex,
                 final boolean isFrozen) {
        super(type, name, mass);
        this.salinity = salinity;
        this.pH = pH;
        this.purity = purity;
        this.turbidity = turbidity;
        this.contaminantIndex = contaminantIndex;
        this.isFrozen = isFrozen;
        this.quality = calcQuality();
    }

    /**
     * Calculates the overall quality score of the water.
     * @return The calculated quality score.
     */
    public double calcQuality() {
        double purityScore = purity / MAX_PERCENTAGE;
        double phScore = 1 - Math.abs(pH - IDEAL_PH) / IDEAL_PH;
        double salinityScore = 1 - (salinity / MAX_SALINITY);
        double turbidityScore = 1 - (turbidity / MAX_PERCENTAGE);
        double contaminantScore = 1 - (contaminantIndex / MAX_PERCENTAGE);
        double frozenScore = isFrozen ? 1 : 0;

        double waterQuality = (WEIGHT_PURITY * purityScore
                + WEIGHT_PH * phScore
                + WEIGHT_SALINITY * salinityScore
                + WEIGHT_TURBIDITY * turbidityScore
                + WEIGHT_CONTAMINANT * contaminantScore
                + WEIGHT_FROZEN * frozenScore) * MAX_PERCENTAGE;

        return waterQuality;
    }

    /**
     * Determines the quality category based on the quality score.
     * @return A string representing the quality ("Good", "Moderate", or "Poor").
     */
    public String getWaterQuality() {
        if (quality >= QUALITY_GOOD) {
            return "Good";
        }
        if (quality < QUALITY_GOOD && quality >= QUALITY_MODERATE) {
            return "Moderate";
        }
        return "Poor";
    }
}
