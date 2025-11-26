package entities.soilType;

import entities.Soil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ForestSoil extends Soil {
    private static final double NITROGEN_WEIGHT = 1.2;
    private static final int ORGANIC_WEIGHT = 2;
    private static final double WATER_WEIGHT = 1.5;
    private static final double LITTER_WEIGHT = 0.3;
    private static final double MAX_SCORE = 100.0;
    private static final double ROUNDING_PRECISION = 100.0;

    private static final double BLOCK_WATER_WEIGHT = 0.6;
    private static final double BLOCK_LITTER_WEIGHT = 0.4;
    private static final double BLOCK_DIVISOR = 80.0;
    private static final double PERCENTAGE_FACTOR = 100.0;

    private double leaflitter;

    public ForestSoil(final String type, final String name, final double mass,
                      final double nitrogen, final double waterRetention,
                      final double soilpH, final double organicMatter, final double leaflitter) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.leaflitter = leaflitter;

        calculateQuality();

        double blockProb = (waterRetention * BLOCK_WATER_WEIGHT
                + leaflitter * BLOCK_LITTER_WEIGHT)
                / BLOCK_DIVISOR * PERCENTAGE_FACTOR;

        super.setBlockProbability(blockProb);
    }

    /** *
     * Calculates the quality score for Forest Soil.
     * Quality is influenced positively by nitrogen, organic matter,
     * water retention, and leaf litter.
     */
    public void calculateQuality() {
        double quality = getNitrogen() * NITROGEN_WEIGHT
                + getOrganicMatter() * ORGANIC_WEIGHT
                + getWaterRetention() * WATER_WEIGHT
                + getLeaflitter() * LITTER_WEIGHT;

        // Normalize score between 0 and 100
        quality = Math.max(0, Math.min(MAX_SCORE, quality));

        // Round score
        quality = (double) Math.round(quality * ROUNDING_PRECISION) / ROUNDING_PRECISION;

        super.setQuality(quality);
    }
}
