package Entities.SoilType;

import Entities.Soil;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DesertSoil extends Soil {
    double salinity;

    public DesertSoil(String type, String name, double mass, double nitrogen, double waterRetention,
                      double soilpH, double organicMatter, double salinity) {
        super(type, name, mass, nitrogen, waterRetention, soilpH, organicMatter);
        this.salinity = salinity;

        double quality = nitrogen * 1.2 + waterRetention * 1.5 - salinity * 2;
        quality = Math.max(0, Math.min(100, quality)); // normalizez scorul

        quality = (double) Math.round(quality * 100.0) / 100; // rotunjesc scorul

        super.setQuality(quality);

        double blockProb = (100.0 - waterRetention + salinity) / 100.0 * 100.0;

        super.setBlockProbability(blockProb);
    }
}
