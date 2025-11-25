package entities;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import entities.airType.*;
import entities.soilType.*;
import java.util.ArrayList;

/**
 * Handles the reporting and printing of environment conditions and map state.
 */
public final class EnvironmentReporter {

    /**
     * Prints environmental conditions at the robot's current location.
     * @param ctx The simulation context.
     * @param commandOutput The output node.
     */
    public void printEnvCond(final SimulationContext ctx, final ObjectNode commandOutput) {
        ObjectMapper mapper = ctx.getMapper();
        Robot robot = ctx.getRobot();
        GameMap map = ctx.getMap();

        int x = robot.getX();
        int y = robot.getY();
        Cell cell = map.getCell(x, y);

        ObjectNode output = mapper.createObjectNode();

        // -- Soil Reporting --
        Soil soil = cell.getSoil();
        if (soil != null) {
            ObjectNode soilNode = mapper.createObjectNode();
            soilNode.put("type", soil.getType());
            soilNode.put("name", soil.getName());
            soilNode.put("mass", soil.getMass());
            soilNode.put("nitrogen", soil.getNitrogen());
            soilNode.put("waterRetention", soil.getWaterRetention());
            soilNode.put("soilpH", soil.getSoilpH());
            soilNode.put("organicMatter", soil.getOrganicMatter());
            soilNode.put("soilQuality", soil.getQuality());

            if (soil instanceof SwampSoil) {
                soilNode.put("waterLogging", ((SwampSoil) soil).getWaterLogging());
            } else if (soil instanceof TundraSoil) {
                soilNode.put("permafrostDepth", ((TundraSoil) soil).getPermafrostDepth());
            } else if (soil instanceof DesertSoil) {
                soilNode.put("salinity", ((DesertSoil) soil).getSalinity());
            } else if (soil instanceof ForestSoil) {
                soilNode.put("leafLitter", ((ForestSoil) soil).getLeaflitter());
            } else if (soil instanceof GrasslandSoil) {
                soilNode.put("rootDensity", ((GrasslandSoil) soil).getRootDensity());
            }
            output.set("soil", soilNode);
        }

        // -- Plant Reporting --
        Plant plant = cell.getPlant();
        if (plant != null) {
            ObjectNode plantNode = mapper.createObjectNode();
            plantNode.put("type", plant.getType());
            plantNode.put("name", plant.getName());
            plantNode.put("mass", plant.getMass());
            output.set("plants", plantNode);
        }

        // -- Animal Reporting --
        Animal animal = cell.getAnimal();
        if (animal != null) {
            ObjectNode animalNode = mapper.createObjectNode();
            animalNode.put("type", animal.getType());
            animalNode.put("name", animal.getName());
            animalNode.put("mass", animal.getMass());
            output.set("animals", animalNode);
        }

        // -- Water Reporting --
        Water water = cell.getWater();
        if (water != null) {
            ObjectNode waterNode = mapper.createObjectNode();
            waterNode.put("type", water.getType());
            waterNode.put("name", water.getName());
            waterNode.put("mass", water.getMass());
            output.set("water", waterNode);
        }

        // -- Air Reporting --
        Air air = cell.getAir();
        if (air != null) {
            ObjectNode airNode = mapper.createObjectNode();
            airNode.put("type", air.getType());
            airNode.put("name", air.getName());
            airNode.put("mass", air.getMass());
            airNode.put("humidity", air.getHumidity());
            airNode.put("temperature", air.getTemperature());
            airNode.put("oxygenLevel", air.getOxygenLevel());

            if (air.getChangeWeather() == 0) {
                airNode.put("airQuality", air.getAirQuality());
            } else {
                airNode.put("airQuality", air.getChangedAir());
            }

            if (air instanceof Desert) {
                airNode.put("desertStorm", ((Desert) air).isDesertStorm());
            } else if (air instanceof Montan) {
                airNode.put("altitude", ((Montan) air).getAltitude());
            } else if (air instanceof Polar) {
                airNode.put("iceCrystalConcentration",
                        ((Polar) air).getIceCrystalConcentration());
            } else if (air instanceof Temperat) {
                airNode.put("pollenLevel", ((Temperat) air).getPollenLevel());
            } else if (air instanceof Tropical) {
                airNode.put("co2Level", ((Tropical) air).getCo2Level());
            }
            output.set("air", airNode);
        }

        commandOutput.set("output", output);
    }

    /**
     * Prints the full map status.
     * @param ctx The simulation context.
     * @param commandOutput The output node.
     */
    public void printMap(final SimulationContext ctx, final ObjectNode commandOutput) {
        ObjectMapper mapper = ctx.getMapper();
        GameMap map = ctx.getMap();
        ArrayNode output = mapper.createArrayNode();

        int n = map.getX();
        int m = map.getY();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ObjectNode cellNode = mapper.createObjectNode();
                ArrayNode section = mapper.createArrayNode();
                section.add(j);
                section.add(i);
                cellNode.put("section", section);
                int nrObj = map.nrObj(j, i);
                cellNode.put("totalNrOfObjects", nrObj);

                Cell cell = map.getCell(j, i);
                cellNode.put("airQuality", cell.getAir().airQuality());
                cellNode.put("soilQuality", cell.getSoil().qualitySoil());

                output.add(cellNode);
            }
        }
        commandOutput.put("output", output);
    }

    /**
     * Prints the robot's known facts.
     * @param ctx The simulation context.
     * @param commandOutput The output node.
     */
    public void printKnowledge(final SimulationContext ctx, final ObjectNode commandOutput) {
        ObjectMapper mapper = ctx.getMapper();
        ArrayList<Facts> factsList = ctx.getFacts();
        ArrayNode initial = mapper.createArrayNode();

        for (Facts fact1 : factsList) {
            ObjectNode fact = mapper.createObjectNode();
            fact.put("topic", fact1.getComponents());
            ArrayNode subjects = mapper.createArrayNode();

            for (String sub : fact1.getSubjects()) {
                subjects.add(sub);
            }

            fact.put("facts", subjects);
            initial.add(fact);
        }
        commandOutput.put("output", initial);
    }
}
