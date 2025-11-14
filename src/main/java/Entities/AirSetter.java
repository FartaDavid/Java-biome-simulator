package Entities;

import Entities.AirType.*;
import fileio.AirInput;

public class AirSetter {
    public static Air returnAir(AirInput airInput) {

        switch(airInput.type) {
            case "TropicalAir":
                return new Tropical(airInput.type, airInput.name, airInput.mass,
                        airInput.humidity, airInput.temperature,
                        airInput.oxygenLevel, airInput.co2Level); // Presupunând că ai adăugat co2Level în AirInput

            case "TemperateAir": // Sau "Temperat"
                return new Temperat(airInput.type, airInput.name, airInput.mass,
                        airInput.humidity, airInput.temperature,
                        airInput.oxygenLevel, airInput.pollenLevel); // La fel

            case "MountainAir":
                return new Montan(airInput.type, airInput.name, airInput.mass,
                        airInput.humidity, airInput.temperature,
                        airInput.oxygenLevel, airInput.altitude); // La fel

            case "DesertAir":
                return new Desert(airInput.type, airInput.name, airInput.mass,
                        airInput.humidity, airInput.temperature,
                        airInput.oxygenLevel, airInput.dustParticles); // La fel

            case "PolarAir":
                return new Polar(airInput.type, airInput.name, airInput.mass,
                        airInput.humidity, airInput.temperature,
                        airInput.oxygenLevel, airInput.iceCrystalConcentration); // La fel

            default:
                return null;
        }
    }
}