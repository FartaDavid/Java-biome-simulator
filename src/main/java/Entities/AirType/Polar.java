package Entities.AirType;

import Entities.Air;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Polar extends Air {
    private double iceCrystalConcentration;

    public Polar(String type, String name, double mass, double humidity, double temperature, double oxygenLevel, double iceCrystalConcentration) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.iceCrystalConcentration = iceCrystalConcentration;

        double quality = oxygenLevel * 2 + 100 - Math.abs(temperature) -
                iceCrystalConcentration * 0.05;
        quality = Math.max(0, Math.min(100, quality)); // normalizez scorul
        quality = Math.round(quality * 100.0) / 100.0; // rotunjesc scorul
        super.setAirQuality(quality);

        double toxicityAQ = 100.0 * (1.0 - quality / 142.0);
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // final result toxicity
        toxicityAQ = Math.max(0, Math.min(100, toxicityAQ)); // normalizez scorul
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // rotunjesc scorul

        super.setToxicity(toxicityAQ);
    }

    public double polarStorm(double windSpeed) {
        double quality = getAirQuality();
        quality -= windSpeed * 0.2;

        return quality;
    }
}
