package Entities.AirType;

import Entities.Air;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Temperat extends Air {
    private double pollenLevel;

    public Temperat(String type, String name, double mass, double humidity, double temperature, double oxygenLevel, double pollenLevel) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.pollenLevel = pollenLevel;

        double quality = oxygenLevel * 2 + humidity * 0.7 - pollenLevel * 0.1;
        quality = Math.max(0, Math.min(100, quality)); // normalizez scorul
        quality = Math.round(quality * 100.0) / 100.0; // rotunjesc scorul
        super.setAirQuality(quality);

        double toxicityAQ = 100.0 * (1.0 - quality / 84.0);
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // final result toxicity
        toxicityAQ = Math.max(0, Math.min(100, toxicityAQ)); // normalizez scorul
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // rotunjesc scorul

        super.setToxicity(toxicityAQ);

    }

    public double newSeason(String season) {
        double quality = getAirQuality();
        double seasonPenalty = season.equalsIgnoreCase("Spring") ? 15 : 0;
        quality -= seasonPenalty;

        return quality;
    }
}
