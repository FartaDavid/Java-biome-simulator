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

        double quality = nitrogen * 1.2 + organicMatter * 2 + rootDensity * 0.8;
        quality = Math.max(0, Math.min(100, quality)); // normalizez scorul

        quality = (double) Math.round(quality * 100.0) / 100; // rotunjesc scorul

        super.setQuality(quality);

    }

    public double blockProbability(double rootDensity) {
        double waterRetention = getWaterRetention();

        return ((50 - rootDensity) + waterRetention * 0.5) / 75 * 100;
    }
}
