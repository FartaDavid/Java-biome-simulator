package Entities.AirType;

import Entities.Air;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Polar extends Air {
    private double iceCrystalConcentration;

    public double calculateAirQuality() {
        return super.getOxygenLevel() * 2 + 100 - Math.abs(super.getTemperature()) - iceCrystalConcentration * 0.05;
    }

    public Polar(String type, String name, double mass, double humidity, double temperature, double oxygenLevel, double iceCrystalConcentration) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.iceCrystalConcentration = iceCrystalConcentration;

        normalizeQuality(calculateAirQuality());

        double toxicityAQ = 100.0 * (1.0 - calculateAirQuality() / 142.0);
        getToxicity(toxicityAQ);
    }

    public void polarStorm(double windSpeed) {
        double quality = getAirQuality();
        quality -= windSpeed * 0.2;

        super.setChangedAir(quality);
    }
}
