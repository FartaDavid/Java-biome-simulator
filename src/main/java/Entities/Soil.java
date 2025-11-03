package Entities;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Soil extends Entities {
    private double nitrogen;
    private double waterRetention;
    private double soilpH;
    private double organicMatter;

    public Soil(String name, double mass, double nitrogen, double waterRetention,
                double soilpH, double organicMatter) {
        super(name, mass);
        this.nitrogen = nitrogen;
        this.waterRetention = waterRetention;
        this.soilpH = soilpH;
        this.organicMatter = organicMatter;
    }
}