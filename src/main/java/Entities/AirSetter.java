package Entities;

import Entities.AirType.*;
import fileio.AirInput;

public class AirSetter {
    public static Air returnAir(AirInput airInput) {

        switch(airInput.getType()) {
            case "TropicalAir":
                return new Tropical(airInput.getType(), airInput.getName(), airInput.getMass(),
                        airInput.getHumidity(), airInput.getTemperature(),
                        airInput.getOxygenLevel(), airInput.getCo2Level()); // Presupunând că ai adăugat co2Level în AirInput

            case "TemperateAir": // Sau "Temperat"
                return new Temperat(airInput.getType(), airInput.getName(), airInput.getMass(),
                        airInput.getHumidity(), airInput.getTemperature(),
                        airInput.getOxygenLevel(), airInput.getPollenLevel()); // La fel

            case "MountainAir":
                return new Montan(airInput.getType(), airInput.getName(), airInput.getMass(),
                        airInput.getHumidity(), airInput.getTemperature(),
                        airInput.getOxygenLevel(), airInput.getAltitude()); // La fel

            case "DesertAir":
                return new Desert(airInput.getType(), airInput.getName(), airInput.getMass(),
                        airInput.getHumidity(), airInput.getTemperature(),
                        airInput.getOxygenLevel(), airInput.getDustParticles()); // La fel

            case "PolarAir":
                return new Polar(airInput.getType(), airInput.getName(), airInput.getMass(),
                        airInput.getHumidity(), airInput.getTemperature(),
                        airInput.getOxygenLevel(), airInput.getIceCrystalConcentration()); // La fel

            default:
                return null;
        }
    }
}