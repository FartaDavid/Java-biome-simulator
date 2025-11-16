package Entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Air extends Entities {
    private double humidity;
    private double temperature;
    private double oxygenLevel;
    private double airQuality;
    private double toxicity;

    public Air(String type, String name, double mass, double humidity, double temperature, double oxygenLevel) {
        super(type, name, mass);
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
    }

    public String airQuality() {
        if (airQuality >= 70) {
            return "good";
        }
        if (airQuality < 70 && airQuality >= 40) {
            return "moderate";
        }
        return "poor";
    }
}

