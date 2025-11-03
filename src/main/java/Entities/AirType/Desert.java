package Entities.AirType;

import Entities.Air;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Desert extends Air {
    private double dustParticles;

    public Desert(String name, double mass, double humidity, double temperature, double oxygenLevel) {
        super(name, mass, humidity, temperature, oxygenLevel);
    }

    public String airQuality() {
        double oxygenLevel = getOxygenLevel();
        double temperature = getTemperature();

        double quality = oxygenLevel * 2 - dustParticles * 0.2 - temperature * 0.3;
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
        double toxicityAQ = 100 * (1 - quality / 65);
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // final result toxicity
        toxicityAQ = Math.max(0, Math.min(100, toxicityAQ)); // normalizez scorul
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // rotunjesc scorul

        return toxicityAQ;
    }

    public double desertStorm(boolean desertstorm) {
        double quality = super.getAirQuality();
        quality -= desertstorm ? 30 : 0;

        return quality;
    }
}
