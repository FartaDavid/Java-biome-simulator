package Entities.SoilType;

import Entities.Soil;

public class SwampSoil extends Soil {
    public SwampSoil(String name, double mass, double nitrogen, double waterRetention,
                      double soilpH, double organicMatter) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter);
    }

    public String qualitySoil(double waterLogging) {
        double nitrogen = getNitrogen();
        double organicMatter = getOrganicMatter();

        double quality = nitrogen * 1.1 + organicMatter * 2.2 - waterLogging * 5;
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

    public double blockProbability(double waterLogging) {
        return waterLogging * 10;
    }
}
