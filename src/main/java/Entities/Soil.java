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

    public Soil(String type, String name, double mass, double nitrogen, double waterRetention,
                double soilpH, double organicMatter) {
        super(type, name, mass);
        this.nitrogen = nitrogen;
        this.waterRetention = waterRetention;
        this.soilpH = soilpH;
        this.organicMatter = organicMatter;
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