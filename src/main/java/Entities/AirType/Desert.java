package Entities.AirType;

import Entities.Air;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Desert extends Air {
    private double dustParticles;
    private boolean desertStorm = false;

    public double calculateAirQuality() {
        return super.getOxygenLevel() * 2 - dustParticles * 0.2 - super.getTemperature() * 0.3;
    }

    public Desert(String type, String name, double mass, double humidity, double temperature, double oxygenLevel, double dustParticles) {
        super(type, name, mass, humidity, temperature, oxygenLevel);
        this.dustParticles = dustParticles;

        normalizeQuality(calculateAirQuality());

        double toxicityAQ = 100.0 * (1.0 - calculateAirQuality() / 65.0);
        getToxicity(toxicityAQ);
    }

    public void desertStorm(boolean desertstorm) {
        double quality = super.getAirQuality();
        quality -= desertstorm ? 30 : 0;

        this.desertStorm = true;
        super.setChangedAir(quality);
    }
}
