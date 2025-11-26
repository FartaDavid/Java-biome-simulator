package entities.soilType;

import entities.Soil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GrasslandSoil extends Soil {

    /** Weighting factor for nitrogen in quality calculation. */
    private static final double NITROGEN_FACTOR = 1.3;
    /** Weighting factor for organic matter in quality calculation. */
    private static final double ORGANIC_FACTOR = 1.5;
    /** Weighting factor for root density in quality calculation. */
    private static final double ROOT_FACTOR = 0.8;

    /** Maximum allowed value for quality. */
    private static final double MAX_QUALITY = 100.0;
    /** Factor used for rounding to two decimal places. */
    private static final double ROUNDING_PRECISION = 100.0;

    /** Base constant for block probability calculation. */
    private static final double BLOCK_BASE_OFFSET = 50.0;
    /** Water retention weighting in block probability. */
    private static final double WATER_RETENTION_WEIGHT = 0.5;
    /** Divisor for block probability normalization. */
    private static final double BLOCK_NORMALIZER = 75.0;
    /** Multiplier for percentage transformation. */
    private static final double PERCENTAGE_MULTIPLIER = 100.0;

    private double rootDensity;

    public GrasslandSoil(final String type, final String name, final double mass,
                         final double nitrogen, final double waterRetention,
                         final double soilpH, final double organicMatter,
                         final double rootDensity) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.rootDensity = rootDensity;

        calculateQuality();

        double blockProb = ((BLOCK_BASE_OFFSET - rootDensity)
                + waterRetention * WATER_RETENTION_WEIGHT)
                / BLOCK_NORMALIZER * PERCENTAGE_MULTIPLIER;

        super.setBlockProbability(blockProb);
    }

    /** *
     * Calculates the quality score for Grassland Soil.
     * Quality is influenced positively by nitrogen, organic matter,
     * and root density.
     */
    public void calculateQuality() {
        double quality = getNitrogen() * NITROGEN_FACTOR
                + getOrganicMatter() * ORGANIC_FACTOR
                + getRootDensity() * ROOT_FACTOR;

        // Normalize score between 0 and 100
        quality = Math.max(0, Math.min(MAX_QUALITY, quality));

        // Round score
        quality = (double) Math.round(quality * ROUNDING_PRECISION) / ROUNDING_PRECISION;

        super.setQuality(quality);
    }
}
