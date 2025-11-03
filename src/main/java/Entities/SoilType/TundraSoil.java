package Entities.SoilType;

import Entities.Soil;

public class TundraSoil extends Soil {
    public TundraSoil(String name, double mass, double nitrogen, double waterRetention,
                      double soilpH, double organicMatter) {
        super(name, mass, nitrogen, waterRetention, soilpH, organicMatter);
    }

    public String qualitySoil(double permafrostDepth) {
        double nitrogen = getNitrogen();
        double organicMatter = getOrganicMatter();

        double quality = nitrogen * 1.2 + organicMatter * 2 - permafrostDepth * 1.5;
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

    public double blockProbability(double permafrostDepth) {
        return (50 - permafrostDepth) / 50 * 100;
    }
}
