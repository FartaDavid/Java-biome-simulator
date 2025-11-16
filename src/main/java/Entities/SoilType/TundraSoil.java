package Entities.SoilType;

import Entities.Soil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TundraSoil extends Soil {
    double permafrostDepth;

    public TundraSoil(String type, String name, double mass, double nitrogen, double waterRetention,
                      double soilpH, double organicMatter, double permafrostDepth) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.permafrostDepth = permafrostDepth;

        double quality = nitrogen * 0.7 + organicMatter * 0.5 - permafrostDepth * 1.5;
        quality = Math.max(0, Math.min(100, quality)); // normalizez scorul

        quality = (double) Math.round(quality * 100.0) / 100; // rotunjesc scorul

        super.setQuality(quality);

        super.setBlockProbability((50.0 - permafrostDepth) / 50.0 * 100.0);
    }
}
