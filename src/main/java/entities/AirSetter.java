package entities;

import entities.airType.Desert;
import entities.airType.Montan;
import entities.airType.Polar;
import entities.airType.Temperat;
import entities.airType.Tropical;
import fileio.AirInput;

/**
 * Utility class for creating specific Air entities based on input data.
 */
public final class AirSetter {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private AirSetter() {
    }

    /**
     * Returns a specific Air object based on the input type.
     *
     * @param airInput The input data containing details about the air.
     * @return An instance of a specific Air subclass (Tropical, Temperat, etc.),
     * or null if the type is not recognized.
     */
    public static Air returnAir(final AirInput airInput) {

        switch (airInput.getType()) {
            case "TropicalAir":
                return new Tropical(airInput.getType(), airInput.getName(),
                        airInput.getMass(), airInput.getHumidity(),
                        airInput.getTemperature(), airInput.getOxygenLevel(),
                        airInput.getCo2Level());

            case "TemperateAir": // Sau "Temperat"
                return new Temperat(airInput.getType(), airInput.getName(),
                        airInput.getMass(), airInput.getHumidity(),
                        airInput.getTemperature(), airInput.getOxygenLevel(),
                        airInput.getPollenLevel());

            case "MountainAir":
                return new Montan(airInput.getType(), airInput.getName(),
                        airInput.getMass(), airInput.getHumidity(),
                        airInput.getTemperature(), airInput.getOxygenLevel(),
                        airInput.getAltitude());

            case "DesertAir":
                return new Desert(airInput.getType(), airInput.getName(),
                        airInput.getMass(), airInput.getHumidity(),
                        airInput.getTemperature(), airInput.getOxygenLevel(),
                        airInput.getDustParticles());

            case "PolarAir":
                return new Polar(airInput.getType(), airInput.getName(),
                        airInput.getMass(), airInput.getHumidity(),
                        airInput.getTemperature(), airInput.getOxygenLevel(),
                        airInput.getIceCrystalConcentration());

            default:
                return null;
        }
    }
}
