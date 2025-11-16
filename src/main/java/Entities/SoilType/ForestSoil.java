package Entities.SoilType;

import Entities.Soil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ForestSoil extends Soil {
    double leaflitter;

    public ForestSoil(String type, String name, double mass, double nitrogen, double waterRetention,
                double soilpH, double organicMatter, double leaflitter) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.leaflitter = leaflitter;

        double quality = nitrogen * 1.2 + organicMatter * 2 +
                waterRetention * 1.5 + leaflitter * 0.3;
        quality = Math.max(0, Math.min(100, quality)); // normalizez scorul

        quality = (double) Math.round(quality * 100.0) / 100; // rotunjesc scorul

        super.setQuality(quality);

        double blockProb = (waterRetention * 0.6 + leaflitter * 0.4) / 80.0 * 100.0;

        super.setBlockProbability(blockProb);
    }
}
