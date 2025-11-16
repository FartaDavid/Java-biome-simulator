package Entities;

import Entities.SoilType.*;
import fileio.SoilInput;

public class SoilSetter {
    public static Soil returnSoil(SoilInput soilInput) {

        switch(soilInput.getType()) {
            case "SwampSoil":

                return new SwampSoil(soilInput.getType(), soilInput.getName(), soilInput.getMass(), soilInput.getNitrogen(), soilInput.getWaterRetention(),
                        soilInput.getSoilpH(), soilInput.getOrganicMatter(), soilInput.getWaterLogging());
            case "TundraSoil":
                return new TundraSoil(soilInput.getType(), soilInput.getName(), soilInput.getMass(),
                        soilInput.getNitrogen(), soilInput.getWaterRetention(),
                        soilInput.getSoilpH(), soilInput.getOrganicMatter(),
                        soilInput.getPermafrostDepth()); // La fel aici

            case "DesertSoil":
                return new DesertSoil(soilInput.getType(), soilInput.getName(), soilInput.getMass(), soilInput.getNitrogen(), soilInput.getWaterRetention(),
                        soilInput.getSoilpH(), soilInput.getOrganicMatter(), soilInput.getSalinity());

            case "ForestSoil":
                return new ForestSoil(soilInput.getType(), soilInput.getName(), soilInput.getMass(), soilInput.getNitrogen(), soilInput.getWaterRetention(),
                        soilInput.getSoilpH(), soilInput.getOrganicMatter(), soilInput.getLeafLitter());

            case "GrasslandSoil":
                return new GrasslandSoil(soilInput.getType(), soilInput.getName(), soilInput.getMass(), soilInput.getNitrogen(), soilInput.getWaterRetention(),
                        soilInput.getSoilpH(), soilInput.getOrganicMatter(), soilInput.getRootDensity());

            default:
                // Oprește-te și aruncă o eroare, sau returnează null/un sol generic
                // dacă e cazul
                return null;
        }
    }
}