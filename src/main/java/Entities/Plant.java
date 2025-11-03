package Entities;

import Entities.Maturity_CatPlant.Maturity;
import Entities.Maturity_CatPlant.CategoriePlanta;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Plant extends Entities {
    CategoriePlanta categoriePlanta;
    Maturity maturity;

    public Plant(String name, double mass, CategoriePlanta categoriePlanta, Maturity maturity) {
        super(name, mass);
        this.categoriePlanta = categoriePlanta;
        this.maturity = maturity;
    }

    public double genO2() {
        double o2plant = categoriePlanta.getOxigen();
        double o2mat = maturity.getMaturity();
        return o2plant + o2mat;
    }

    public double BlockProbability() {
        return categoriePlanta.getProbability();
    }
}