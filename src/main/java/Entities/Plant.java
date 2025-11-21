package Entities;

import Entities.Maturity_CatPlant.Maturity;
import Entities.Maturity_CatPlant.CategoriePlanta;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Plant extends Entities {
    private CategoriePlanta categoriePlanta;
    private Maturity maturity = Maturity.Young;
    private double index = 0;
    private int initialTime = 0;

    public Plant(String type, String name, double mass) {
        super(type, name, mass);
        this.categoriePlanta = CategoriePlanta.getCategoryByType(type);
    }

    public double genO2() {
        double o2plant = categoriePlanta.getOxigen();
        double o2mat = maturity.getMaturity();
        return o2plant + o2mat;
    }

    public void addIndex() {
        index += 0.2;
        index = Math.round(index * 100.0) / 100.0;
        if (index >= 1 && index < 2) {
            maturity = Maturity.Mature;
        }
        if (index >= 2 && index < 3) {
            maturity = Maturity.Old;
        }
        if (index >= 3) {
            maturity = Maturity.Dead;
        }
    }

    public double BlockProbability() {
        return categoriePlanta.getProbability();
    }
}