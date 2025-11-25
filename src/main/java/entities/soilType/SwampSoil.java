package entities.soilType;

import entities.Soil;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents the Swamp soil type.
 * Characterized by high water logging which affects quality and block probability.
 */
@Getter @Setter
public final class SwampSoil extends Soil {
    private static final double NITROGEN_FACTOR = 1.1;
    private static final double ORGANIC_MATTER_FACTOR = 2.2;
    private static final double WATER_LOGGING_PENALTY = 5.0;
    private static final double MAX_QUALITY = 100.0;
    private static final double MIN_QUALITY = 0.0;
    private static final double ROUNDING_FACTOR = 100.0;
    private static final double BLOCK_PROBABILITY_FACTOR = 10.0;

    private double waterLogging;

    /**
     * Constructs a new SwampSoil entity.
     * Calculates the initial quality and block probability based on properties.
     *
     * @param type           The type of the soil.
     * @param name           The name of the soil.
     * @param mass           The mass of the soil.
     * @param nitrogen       The nitrogen level.
     * @param waterRetention The water retention capacity.
     * @param soilpH         The pH level of the soil.
     * @param organicMatter  The organic matter content.
     * @param waterLogging   The water logging level.
     */
    public SwampSoil(final String type, final String name, final double mass,
                     final double nitrogen, final double waterRetention,
                     final double soilpH, final double organicMatter,
                     final double waterLogging) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.waterLogging = waterLogging;

        double quality = nitrogen * NITROGEN_FACTOR
                + organicMatter * ORGANIC_MATTER_FACTOR
                - waterLogging * WATER_LOGGING_PENALTY;

        // normalizez scorul
        quality = Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, quality));

        // rotunjesc scorul
        quality = (double) Math.round(quality * ROUNDING_FACTOR) / ROUNDING_FACTOR;

        super.setQuality(quality);

        super.setBlockProbability(waterLogging * BLOCK_PROBABILITY_FACTOR);
    }
}
