package Entities.AirType;

import Entities.Air;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Tropical extends Air {
    private double co2Level;

    public Tropical(String name, double mass, double humidity, double temperature, double oxygenLevel) {
        super(name, mass, humidity, temperature, oxygenLevel);
    }

    public String airQuality() {
        double oxygenLevel = getOxygenLevel();
        double humidity = getHumidity();

        double quality = oxygenLevel * 2 + humidity * 0.5 - co2Level * 0.01;
        quality = Math.max(0, Math.min(100, quality)); // normalizez scorul
        quality = Math.round(quality * 100.0) / 100.0; // rotunjesc scorul
        super.setAirQuality(quality);

        if (quality >= 70) {
            return "Good";
        }
        if (quality < 70 && quality >= 40) {
            return "Moderate";
        }
        return "Poor";
    }

    public double airToxicity() {
        double quality = super.getAirQuality();
        double toxicityAQ = 100 * (1 - quality / 82);
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // final result toxicity
        toxicityAQ = Math.max(0, Math.min(100, toxicityAQ)); // normalizez scorul
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // rotunjesc scorul

        return toxicityAQ;
    }

    public double Rainfall(double rainfall) {
        double quality = super.getAirQuality();
        quality += rainfall * 0.3;

        return quality;
    }
}
