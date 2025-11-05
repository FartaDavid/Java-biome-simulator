package Entities;

import Entities.AirType.*;
import fileio.AirInput;

public class AirSetter {
    public static Air returnAir(AirInput input) {
        switch(input.type) {
            case "Desert":
                Desert desert = new Desert(input.name, input.mass, input.humidity, input.temperature, input.oxygenLevel);
                desert.setDustParticles(input.dustParticles);
                return desert;
            case "Montan":
                Montan montan = new Montan(input.name, input.mass, input.humidity, input.temperature, input.oxygenLevel);
                montan.setAltitude(input.altitude);
                return montan;
            case "Polar":
                Polar polar = new Polar(input.name, input.mass, input.humidity, input.temperature, input.oxygenLevel);
                polar.setIceCrystalConcentration(input.iceCrystalConcentration);
                return polar;
            case "Temperat":
                Temperat temperat = new Temperat(input.name, input.mass, input.humidity, input.temperature, input.oxygenLevel);
                temperat.setPollenLevel(input.pollenLevel);
                return temperat;
            case "Tropical":
                Tropical tropical = new Tropical(input.name, input.mass, input.humidity, input.temperature, input.oxygenLevel);
                tropical.setCo2Level(input.co2Level);
                return tropical;
        }
        return null;
    }
}
