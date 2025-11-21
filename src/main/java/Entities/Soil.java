package Entities;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Soil extends Entities {
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;
    private double quality;
    private double blockProbability;

    public Soil(String type, String name, double mass, double nitrogen, double waterRetention,
                double soilpH, double organicMatter) {
        super(type, name, mass);
        this.nitrogen = nitrogen;
        this.waterRetention = waterRetention;
        this.soilpH = soilpH;
        this.organicMatter = organicMatter;
    }

    public void addWaterRetention() {
        waterRetention += 0.1;
    }

    public void addOrganicMatter(int k) {
        if (k == 2) {
            this.organicMatter += 0.8;
        }
        if (k == 1) {
            this.organicMatter += 0.5;
        }
    }

    public String qualitySoil() {
        if (quality >= 70) {
            return "good";
        }
        if (quality < 70 && quality >= 40) {
            return "moderate";
        }
        return "poor";
    }
}