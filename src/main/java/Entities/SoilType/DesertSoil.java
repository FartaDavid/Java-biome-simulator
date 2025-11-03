package Entities.SoilType;

import Entities.Soil;

public class DesertSoil extends Soil {
    public DesertSoil(String name, double mass, double nitrogen, double waterRetention,
                      double soilpH, double organicMatter) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter);
    }

    public String qualitySoil(double salinity) {
        double nitrogen = getNitrogen();
        double waterRetention = getWaterRetention();

        double quality = nitrogen * 1.2 + waterRetention * 1.5 - salinity * 2;
        quality = Math.max(0, Math.min(100, quality)); // normalizez scorul

        quality = (double) Math.round(quality * 100.0) / 100; // rotunjesc scorul

        if (quality >= 70) {
            return "Good";
        }
        if (quality < 70 && quality >= 40) {
            return "Moderate";
        }
        return "Poor";
    }

    public double blockProbability(double salinity) {
        double waterRetention = getWaterRetention();

        return (100 - waterRetention + salinity) / 100 * 100;
    }
}
