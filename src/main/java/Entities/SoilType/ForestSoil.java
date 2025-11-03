package Entities.SoilType;

import Entities.Soil;

public class ForestSoil extends Soil {
    public ForestSoil(String name, double mass, double nitrogen, double waterRetention,
                double soilpH, double organicMatter) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter);
    }

    public String qualitySoil(double leaflitter) {
        double nitrogen = getNitrogen();
        double organicMatter = getOrganicMatter();
        double waterRetention = getWaterRetention();

        double quality = nitrogen * 1.2 + organicMatter * 2 +
                waterRetention * 1.5 + leaflitter * 0.3;
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

    public double blockProbability(double leaflitter) {
        double waterRetention = getWaterRetention();

        return (waterRetention * 0.6 + leaflitter * 0.4) / 80 * 100;
    }
}
