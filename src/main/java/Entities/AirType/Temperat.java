package Entities.AirType;

import Entities.Air;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Temperat extends Air {
    private double pollenLevel;

    public double calculateAirQuality() {
        return super.getOxygenLevel() * 2 + super.getHumidity() * 0.7 - pollenLevel * 0.1;
    }

    public Temperat(String type, String name, double mass, double humidity, double temperature, double oxygenLevel, double pollenLevel) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.pollenLevel = pollenLevel;

        normalizeQuality(calculateAirQuality());

        double toxicityAQ = 100.0 * (1.0 - calculateAirQuality() / 84.0);
        getToxicity(toxicityAQ);

    }

    public void newSeason(String season) {
        double quality = getAirQuality();
        double seasonPenalty = season.equalsIgnoreCase("Spring") ? 15 : 0;
        quality -= seasonPenalty;

        super.setChangedAir(quality);
    }
}
