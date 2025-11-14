package Entities.AirType;

import Entities.Air;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Montan extends Air {
    private double altitude;

    public Montan(String type, String name, double mass, double humidity, double temperature, double oxygenLevel, double altitude) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.altitude = altitude;

        double oxygenFactor = oxygenLevel - (altitude / 1000 * 0.5);

        double quality = (oxygenFactor * 2) + humidity * 0.6;
        quality = Math.max(0, Math.min(100, quality)); // normalizez scorul
        quality = Math.round(quality * 100.0) / 100.0; // rotunjesc scorul
        super.setAirQuality(quality);
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
