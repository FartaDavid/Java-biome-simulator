package Entities.AirType;

import Entities.Air;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Tropical extends Air {
    private double co2Level;

    public Tropical(String type, String name, double mass, double humidity, double temperature, double oxygenLevel, double co2Level) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.co2Level = co2Level;

        double quality = oxygenLevel * 2 + humidity * 0.5 - co2Level * 0.01;
        quality = Math.max(0, Math.min(100, quality)); // normalizez scorul
        quality = Math.round(quality * 100.0) / 100.0; // rotunjesc scorul
        super.setAirQuality(quality);

        double toxicityAQ = 100.0 * (1.0 - quality / 82.0);
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // final result toxicity
        toxicityAQ = Math.max(0, Math.min(100, toxicityAQ)); // normalizez scorul
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // rotunjesc scorul

        super.setToxicity(toxicityAQ);
    }

    public double Rainfall(double rainfall) {
        double quality = super.getAirQuality();
        quality += rainfall * 0.3;

        return quality;
    }
}
