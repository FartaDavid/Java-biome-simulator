package Entities;

import Entities.AirType.Desert;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class Air extends Entities {
    private double humidity;
    private double temperature;
    private double oxygenLevel;
    private double airQuality;
    private double changedAir; // asta o folosesc pentru a salva calitatea la comanda changeaWeather
    private double toxicity;
    private int changeWeather; // timer pentru a schimba la normal calitatea aerului

    public Air(String type, String name, double mass, double humidity, double temperature, double oxygenLevel) {
        super(type, name, mass);
        this.humidity = humidity;
        this.temperature = temperature;
        this.oxygenLevel = oxygenLevel;
    }

    public abstract double calculateAirQuality();

    public void normalizeQuality(double quality) {
        quality = Math.max(0, Math.min(100, quality)); // normalizez scorul
        quality = Math.round(quality * 100.0) / 100.0; // rotunjesc scorul
        if (quality >= 100)
            quality = 100;
        this.airQuality = quality;
    }

    public void getToxicity(double toxicityAQ) {
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // final result toxicity
        toxicityAQ = Math.max(0, Math.min(100, toxicityAQ)); // normalizez scorul
        toxicityAQ = Math.round(toxicityAQ * 100.0) / 100.0; // rotunjesc scorul
        this.toxicity = toxicityAQ;
    }

    public void addHumidity() {
        humidity += 0.1;
    }

    public void setchangeWeather() {
        changeWeather = 2;
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

