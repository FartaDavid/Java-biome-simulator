package Entities.SoilType;

import Entities.Soil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class GrasslandSoil extends Soil {
    double rootDensity;

    public GrasslandSoil(String type, String name, double mass, double nitrogen, double waterRetention,
                      double soilpH, double organicMatter, double rootDensity) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.rootDensity = rootDensity;

        double quality = nitrogen * 1.3 + organicMatter * 1.5 + rootDensity * 0.8;
        quality = Math.max(0, Math.min(100, quality)); // normalizez scorul

        quality = (double) Math.round(quality * 100.0) / 100; // rotunjesc scorul

        super.setQuality(quality);

        double blockProb = ((50.0 - rootDensity) + waterRetention * 0.5) / 75.0 * 100.0;

        super.setBlockProbability(blockProb);

    }
}
