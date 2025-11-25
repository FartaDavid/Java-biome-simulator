package entities.soilType;

import entities.Soil;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents a Tundra Soil type.
 * Characterized by permafrost depth and specific quality/blocking calculations.
 */
@Getter @Setter
public class TundraSoil extends Soil {

    private static final double NITROGEN_FACTOR = 0.7;
    private static final double ORGANIC_MATTER_FACTOR = 0.5;
    private static final double PERMAFROST_PENALTY = 1.5;
    private static final double MAX_QUALITY = 100.0;
    private static final double ROUNDING_FACTOR = 100.0;
    private static final double MAX_DEPTH_THRESHOLD = 50.0;
    private static final double PERCENTAGE_FACTOR = 100.0;

    private double permafrostDepth;

    /**
     * Constructs a new TundraSoil instance.
     * Calculates quality and block probability based on soil properties and permafrost depth.
     *
     * @param type            The type of the soil.
     * @param name            The name of the soil.
     * @param mass            The mass of the soil.
     * @param nitrogen        The nitrogen content.
     * @param waterRetention  The water retention capability.
     * @param soilpH          The pH level of the soil.
     * @param organicMatter   The organic matter content.
     * @param permafrostDepth The depth of the permafrost layer.
     */
    public TundraSoil(final String type, final String name, final double mass,
                      final double nitrogen, final double waterRetention, final double soilpH,
                      final double organicMatter, final double permafrostDepth) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.permafrostDepth = permafrostDepth;

        double quality = nitrogen * NITROGEN_FACTOR + organicMatter * ORGANIC_MATTER_FACTOR
                - permafrostDepth * PERMAFROST_PENALTY;

        // normalizez scorul
        quality = Math.max(0, Math.min(MAX_QUALITY, quality));

        // rotunjesc scorul
        quality = (double) Math.round(quality * ROUNDING_FACTOR) / ROUNDING_FACTOR;

        super.setQuality(quality);

        double blockProb = (MAX_DEPTH_THRESHOLD - permafrostDepth) / MAX_DEPTH_THRESHOLD
                * PERCENTAGE_FACTOR;
        super.setBlockProbability(blockProb);
    }
}
