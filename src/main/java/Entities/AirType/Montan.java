package Entities.AirType;

import Entities.Air;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Montan extends Air {
    private double altitude;

    public double calculateAirQuality() {
        double oxygenFactor = super.getOxygenLevel() - (altitude / 1000 * 0.5);
        return (oxygenFactor * 2) + super.getHumidity() * 0.6;
    }

    public Montan(String type, String name, double mass, double humidity, double temperature, double oxygenLevel, double altitude) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.altitude = altitude;

        normalizeQuality(calculateAirQuality());

        double toxicityAQ = 100.0 * (1.0 - calculateAirQuality() / 78.0);
        getToxicity(toxicityAQ);
    }

    public void peopleHiking(double numberOfHikers) {
        double quality = super.getAirQuality();
        quality -= numberOfHikers * 0.1;

        super.setChangedAir(quality);
    }
}
