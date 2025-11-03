package Entities.AirType;

import Entities.Air;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Montan extends Air {
    private double altitude;

    public Montan(String name, double mass, double humidity, double temperature, double oxygenLevel) {
        super(name, mass, humidity, temperature, oxygenLevel);
    }

    public String airQuality(double oxygenFactor) {
        double oxygenLevel = getOxygenLevel();
        double humidity = getHumidity();

        double quality = oxygenLevel - (altitude / 1000 * 0.5) * (oxygenFactor * 2) + humidity * 0.6;
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
        double toxicityAQ = 100 * (1 - quality / 78);
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // final result toxicity
        toxicityAQ = Math.max(0, Math.min(100, toxicityAQ)); // normalizez scorul
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // rotunjesc scorul

        return toxicityAQ;
    }

    public double peopleHiking(double numberOfHikers) {
        double quality = super.getAirQuality();
        quality -= numberOfHikers * 0.1;

        return quality;
    }
}
