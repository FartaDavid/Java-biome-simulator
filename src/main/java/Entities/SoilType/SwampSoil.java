package Entities.SoilType;

import Entities.Soil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SwampSoil extends Soil {
    double waterLogging;

    public SwampSoil(String type, String name, double mass, double nitrogen, double waterRetention,
                      double soilpH, double organicMatter, double waterLogging) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.waterLogging = waterLogging;

        double quality = nitrogen * 1.1 + organicMatter * 2.2 - waterLogging * 5;
        quality = Math.max(0, Math.min(100, quality)); // normalizez scorul

        quality = (double) Math.round(quality * 100.0) / 100; // rotunjesc scorul

        super.setQuality(quality);

    }

    public double blockProbability(double waterLogging) {
        return waterLogging * 10;
    }
}
