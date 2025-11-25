package entities;

import entities.soilType.DesertSoil;
import entities.soilType.ForestSoil;
import entities.soilType.GrasslandSoil;
import entities.soilType.SwampSoil;
import entities.soilType.TundraSoil;
import fileio.SoilInput;

/**
 * Utility class for creating specific Soil entities based on input data.
 */
public final class SoilSetter {

    /**
     * Private constructor to prevent instantiation of utility class.
     */
    private SoilSetter() {
    }

    /**
     * Returns a specific Soil object based on the input type.
     *
     * @param soilInput The input data containing details about the soil.
     * @return An instance of a specific Soil subclass, or null if the type is unknown.
     */
    public static Soil returnSoil(final SoilInput soilInput) {

        switch (soilInput.getType()) {
            case "SwampSoil":
                return new SwampSoil(soilInput.getType(), soilInput.getName(),
                        soilInput.getMass(), soilInput.getNitrogen(),
                        soilInput.getWaterRetention(), soilInput.getSoilpH(),
                        soilInput.getOrganicMatter(), soilInput.getWaterLogging());

            case "TundraSoil":
                return new TundraSoil(soilInput.getType(), soilInput.getName(),
                        soilInput.getMass(), soilInput.getNitrogen(),
                        soilInput.getWaterRetention(), soilInput.getSoilpH(),
                        soilInput.getOrganicMatter(), soilInput.getPermafrostDepth());

            case "DesertSoil":
                return new DesertSoil(soilInput.getType(), soilInput.getName(),
                        soilInput.getMass(), soilInput.getNitrogen(),
                        soilInput.getWaterRetention(), soilInput.getSoilpH(),
                        soilInput.getOrganicMatter(), soilInput.getSalinity());

            case "ForestSoil":
                return new ForestSoil(soilInput.getType(), soilInput.getName(),
                        soilInput.getMass(), soilInput.getNitrogen(),
                        soilInput.getWaterRetention(), soilInput.getSoilpH(),
                        soilInput.getOrganicMatter(), soilInput.getLeafLitter());

            case "GrasslandSoil":
                return new GrasslandSoil(soilInput.getType(), soilInput.getName(),
                        soilInput.getMass(), soilInput.getNitrogen(),
                        soilInput.getWaterRetention(), soilInput.getSoilpH(),
                        soilInput.getOrganicMatter(), soilInput.getRootDensity());

            default:
                // Return null if type is not recognized
                return null;
        }
    }
}
