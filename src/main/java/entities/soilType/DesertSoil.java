package entities.soilType;

import entities.Soil;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a Desert Soil type.
 * Characterized by high salinity and specific quality/blocking calculations.
 */
@Getter @Setter
public class DesertSoil extends Soil {
    private static final double NITROGEN_FACTOR = 1.2;
    private static final double WATER_RETENTION_FACTOR = 1.5;
    private static final int SALINITY_FACTOR = 2;
    private static final double MAX_QUALITY = 100.0;
    private static final double ROUNDING_FACTOR = 100.0;
    private static final double BLOCK_PROB_BASE = 100.0;

    private double salinity;

    /**
     * Constructs a new DesertSoil instance.
     * Calculates quality and block probability based on soil properties.
     *
     * @param type           The type of the soil.
     * @param name           The name of the soil.
     * @param mass           The mass of the soil.
     * @param nitrogen       The nitrogen content.
     * @param waterRetention The water retention capability.
     * @param soilpH         The pH level of the soil.
     * @param organicMatter  The organic matter content.
     * @param salinity       The salinity level of the soil.
     */
    public DesertSoil(final String type, final String name, final double mass,
                      final double nitrogen, final double waterRetention, final double soilpH,
                      final double organicMatter, final double salinity) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.salinity = salinity;

        double quality = nitrogen * NITROGEN_FACTOR + waterRetention * WATER_RETENTION_FACTOR
                - salinity * SALINITY_FACTOR;
        quality = Math.max(0, Math.min(MAX_QUALITY, quality)); // normalizez scorul

        // rotunjesc scorul
        quality = (double) Math.round(quality * ROUNDING_FACTOR) / ROUNDING_FACTOR;

        super.setQuality(quality);

        double blockProb = (BLOCK_PROB_BASE - waterRetention + salinity) / BLOCK_PROB_BASE
                * BLOCK_PROB_BASE;

        super.setBlockProbability(blockProb);
    }
}
