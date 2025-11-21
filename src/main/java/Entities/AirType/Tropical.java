package Entities.AirType;

import Entities.Air;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Tropical extends Air {
    private double co2Level;

    public double calculateAirQuality() {
        return super.getOxygenLevel() * 2 + super.getHumidity() * 0.5 - co2Level * 0.01;
    }

    public Tropical(String type, String name, double mass, double humidity, double temperature, double oxygenLevel, double co2Level) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.co2Level = co2Level;

        normalizeQuality(calculateAirQuality());

        double toxicityAQ = 100.0 * (1.0 - calculateAirQuality() / 82.0);
        getToxicity(toxicityAQ);
    }

    public void Rainfall(double rainfall) {
        double quality = super.getAirQuality();
        quality += rainfall * 0.3;

        super.setChangedAir(quality);
    }
}
