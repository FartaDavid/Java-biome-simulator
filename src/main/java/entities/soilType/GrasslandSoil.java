package entities.soilType;

import entities.Soil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GrasslandSoil extends Soil {

    /** Factor de ponderare pentru azot in calculul calitatii. */
    private static final double NITROGEN_FACTOR = 1.3;
    /** Factor de ponderare pentru materie organica in calculul calitatii. */
    private static final double ORGANIC_FACTOR = 1.5;
    /** Factor de ponderare pentru densitatea radacinilor in calculul calitatii. */
    private static final double ROOT_FACTOR = 0.8;

    /** Valoarea maxima admisa pentru calitate. */
    private static final double MAX_QUALITY = 100.0;
    /** Factor folosit pentru rotunjirea la doua zecimale. */
    private static final double ROUNDING_PRECISION = 100.0;

    /** Constanta de baza pentru calculul probabilitatii de blocaj. */
    private static final double BLOCK_BASE_OFFSET = 50.0;
    /** Ponderarea retentiei de apa in probabilitatea de blocaj. */
    private static final double WATER_RETENTION_WEIGHT = 0.5;
    /** Divizor pentru normalizarea probabilitatii de blocaj. */
    private static final double BLOCK_NORMALIZER = 75.0;
    /** Multiplicator pentru transformarea in procent. */
    private static final double PERCENTAGE_MULTIPLIER = 100.0;

    private double rootDensity;

    public GrasslandSoil(final String type, final String name, final double mass,
                         final double nitrogen, final double waterRetention,
                         final double soilpH, final double organicMatter,
                         final double rootDensity) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.rootDensity = rootDensity;

        double quality = nitrogen * NITROGEN_FACTOR
                + organicMatter * ORGANIC_FACTOR
                + rootDensity * ROOT_FACTOR;

        // Normalizare scor intre 0 si 100
        quality = Math.max(0, Math.min(MAX_QUALITY, quality));

        // Rotunjire scor
        quality = (double) Math.round(quality * ROUNDING_PRECISION) / ROUNDING_PRECISION;

        super.setQuality(quality);

        double blockProb = ((BLOCK_BASE_OFFSET - rootDensity)
                + waterRetention * WATER_RETENTION_WEIGHT)
                / BLOCK_NORMALIZER * PERCENTAGE_MULTIPLIER;

        super.setBlockProbability(blockProb);
    }
}
