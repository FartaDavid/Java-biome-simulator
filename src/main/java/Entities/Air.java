package Entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Air extends Entities {
    private double humidity;
    private double temperature;
    private double oxygenLevel;
    private double airQuality;

    public Air(String name, double mass, double humidity, double temperature, double oxygenLevel) {
        super(name, mass);
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
    }
}

