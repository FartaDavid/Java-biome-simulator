package entities;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Soil extends Entities {

    /** Increment pentru retentia apei. */
    private static final double WATER_INCREMENT = 0.1;
    /** Ados de materie organica pentru k=2. */
    private static final double ORGANIC_ADD_HIGH = 0.8;
    /** Ados de materie organica pentru k=1. */
    private static final double ORGANIC_ADD_LOW = 0.5;
    /** Pragul de calitate pentru sol bun. */
    private static final double QUALITY_GOOD_THRESHOLD = 70.0;
    /** Pragul de calitate pentru sol moderat. */
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
     * Creste retentia apei cu o valoare fixa.
     */
    public final void addWaterRetention() {
        waterRetention += WATER_INCREMENT;
    }

    /**
     * Adauga materie organica in functie de factorul k.
     * @param k Factorul de decizie (1 sau 2).
     */
    public final void addOrganicMatter(final int k) {
        if (k == 2) {
            this.organicMatter += ORGANIC_ADD_HIGH;
        }
        if (k == 1) {
            this.organicMatter += ORGANIC_ADD_LOW;
        }
    }

    /**
     * Determina calitatea solului bazata pe scorul de calitate.
     * @return Un string reprezentand calitatea ("good", "moderate", "poor").
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
