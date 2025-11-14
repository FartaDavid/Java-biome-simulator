package Entities;

import Entities.SoilType.*;
import fileio.SoilInput;

public class SoilSetter {
    public static Soil returnSoil(SoilInput soilInput) {

        switch(soilInput.type) {
            case "SwampSoil":
                // Creează un OBIECT REAL 'SwampSoil'
                // ATENȚIE: Trebuie să folosești constructorul corectat
                // pe care l-am discutat, care acceptă și 'waterLogging'
                return new SwampSoil(soilInput.type, soilInput.name, soilInput.mass, soilInput.nitrogen, soilInput.waterRetention,
                        soilInput.soilpH, soilInput.organicMatter, soilInput.waterLogging);
            case "TundraSoil":
                return new TundraSoil(soilInput.type, soilInput.name, soilInput.mass,
                        soilInput.nitrogen, soilInput.waterRetention,
                        soilInput.soilpH, soilInput.organicMatter,
                        soilInput.permafrostDepth); // La fel aici

            case "DesertSoil":
                return new DesertSoil(soilInput.type, soilInput.name, soilInput.mass, soilInput.nitrogen, soilInput.waterRetention,
                        soilInput.soilpH, soilInput.organicMatter, soilInput.salinity);

            case "ForestSoil":
                return new ForestSoil(soilInput.type, soilInput.name, soilInput.mass, soilInput.nitrogen, soilInput.waterRetention,
                        soilInput.soilpH, soilInput.organicMatter, soilInput.leafLitter);

            case "GrasslandSoil":
                return new GrasslandSoil(soilInput.type, soilInput.name, soilInput.mass, soilInput.nitrogen, soilInput.waterRetention,
                        soilInput.soilpH, soilInput.organicMatter, soilInput.rootDensity);

            default:
                // Oprește-te și aruncă o eroare, sau returnează null/un sol generic
                // dacă e cazul
                return null;
        }
    }
}