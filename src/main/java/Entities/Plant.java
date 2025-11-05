package Entities;

import Entities.Maturity_CatPlant.Maturity;
import Entities.Maturity_CatPlant.CategoriePlanta;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Plant extends Entities {
    private CategoriePlanta categoriePlanta;
    private Maturity maturity;
    private String type;

    public Plant(String name, double mass, String type) {
        super(name, mass);
        this.type = type;
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