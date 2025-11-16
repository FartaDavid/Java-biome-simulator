package Entities;

import Entities.AirType.*;
import Entities.SoilType.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;

import java.util.ArrayList;
import java.util.Map;

public class CommandManager {
    private final ObjectMapper MAPPER;

    public CommandManager(ObjectMapper MAPPER) {
        this.MAPPER = MAPPER;
    }

    public void commandManage(ArrayList<CommandInput> commandInput, ArrayNode output, Robot robot, GameMap map) {

        boolean simStarted = false;

        for(CommandInput command : commandInput) {
            ObjectNode commandOutput = MAPPER.createObjectNode();

            commandOutput.put("command", command.command);

            switch(command.command) {
                case "startSimulation":
                    commandOutput.put("message", "Simulation has started.");
                    simStarted = true;
                    break;
                case "endSimulation":
                    commandOutput.put("message", "Simulation has ended.");
                    simStarted = false;
                    break;
                case "printEnvConditions":
                    if (simStarted)
                        this.PrintEnvCond(commandOutput, robot, map);
                    else
                        commandOutput.put("message","ERROR: Simulation not started. Cannot perform action");
                    break;
                case "printMap":
                    if (simStarted)
                        this.PrintMap(commandOutput, map);
                    else
                        commandOutput.put("message", "ERROR: Simulation not started. Cannot perform action");
                    break;
                case "moveRobot":
                    if (simStarted) {
                        robot.moveRobot(map, commandOutput);
                    }
                    else
                        commandOutput.put("message", "ERROR: Simulation not started. Cannot perform action");
            }
            commandOutput.put("timestamp", command.timestamp);
            output.add(commandOutput);
        }
    }

    public void PrintEnvCond(ObjectNode commandOutput, Robot robot, GameMap map) {
        int x = robot.getX();
        int y = robot.getY();
        Cell cell = map.getCell(x, y);

        ObjectNode output = MAPPER.createObjectNode();

        Soil soil = cell.getSoil();
        if (soil != null) {
            ObjectNode soilNode = MAPPER.createObjectNode();
            // Adaptează gettere-le la denumirile din clasele tale
            soilNode.put("type", soil.getType());
            soilNode.put("name", soil.getName());
            soilNode.put("mass", soil.getMass());
            soilNode.put("nitrogen", soil.getNitrogen());
            soilNode.put("waterRetention", soil.getWaterRetention());
            soilNode.put("soilpH", soil.getSoilpH());
            soilNode.put("organicMatter", soil.getOrganicMatter());
            soilNode.put("soilQuality", soil.getQuality());
            if (soil.getType().equals("SwampSoil")) {
                SwampSoil swampSoil = (SwampSoil) soil;
                soilNode.put("waterLogging", swampSoil.getWaterLogging());
            }
            if (soil.getType().equals("TundraSoil")) {
                TundraSoil tundraSoil = (TundraSoil)soil;
                soilNode.put("permafrostDepth", tundraSoil.getPermafrostDepth());
            }
            if (soil.getType().equals("DesertSoil")) {
                DesertSoil desertSoil = (DesertSoil) soil;
                soilNode.put("salinity", desertSoil.getSalinity());
            }
            if (soil.getType().equals("ForestSoil")) {
                ForestSoil forestSoil = (ForestSoil) soil;
                soilNode.put("leafLitter", forestSoil.getLeaflitter());
            }
            if (soil.getType().equals("GrasslandSoil")) {
                GrasslandSoil grasslandSoil = (GrasslandSoil) soil;
                soilNode.put("rootDensity", grasslandSoil.getRootDensity());
            }
            output.set("soil", soilNode);
        }

        // 3. Adaugă informațiile despre Plantă (doar dacă există)
        // Conform exemplului, cheia este "plants" (plural) chiar dacă e un singur obiect
        Plant plant = cell.getPlant();
        if (plant != null) {
            ObjectNode plantNode = MAPPER.createObjectNode();
            plantNode.put("type", plant.getType());
            plantNode.put("name", plant.getName());
            plantNode.put("mass", plant.getMass());
            output.set("plants", plantNode);
        }

        // 4. Adaugă informațiile despre Animal (doar dacă există)
        // Similar, cheia este "animals" (plural)
        Animal animal = cell.getAnimal();
        if (animal != null) {
            ObjectNode animalNode = MAPPER.createObjectNode();
            animalNode.put("type", animal.getType());
            animalNode.put("name", animal.getName());
            animalNode.put("mass", animal.getMass());
            // Adaugă alte atribute ale animalului dacă este necesar
            output.set("animals", animalNode);
        }

        // 5. Adaugă informațiile despre Sursa de Apă (doar dacă există)
        Water water = cell.getWater();
        if (water != null) {
            ObjectNode waterNode = MAPPER.createObjectNode();
            waterNode.put("type", water.getType());
            waterNode.put("name", water.getName());
            waterNode.put("mass", water.getMass());
            waterNode.put("purity", water.getPurity()); // Asigură-te că acestea sunt String
            waterNode.put("salinity", water.getSalinity()); // Asigură-te că acestea sunt String
            waterNode.put("turbidity", water.getTurbidity());
            waterNode.put("contaminantIndex", water.getContaminantIndex());
            waterNode.put("pH", water.getPH());
            waterNode.put("isFrozen", water.isFrozen());
            output.set("water", waterNode);
        }

        // 6. Adaugă informațiile despre Aer (presupunând că există mereu)
        Air air = cell.getAir();
        if (air != null) {
            ObjectNode airNode = MAPPER.createObjectNode();
            airNode.put("type", air.getType());
            airNode.put("name", air.getName());
            airNode.put("mass", air.getMass());
            airNode.put("humidity", air.getHumidity());
            airNode.put("temperature", air.getTemperature());
            airNode.put("oxygenLevel", air.getOxygenLevel());
            airNode.put("airQuality", air.getAirQuality());
            if (air.getType().equals("DesertAir")) {
                Desert desert = (Desert) air;
                airNode.put("dustParticles", desert.getDustParticles());
            }
            if (air.getType().equals("MountainAir")) {
                Montan montan = (Montan) air;
                airNode.put("altitude", montan.getAltitude());
            }
            if (air.getType().equals("PolarAir")) {
                Polar polar = (Polar) air;
                airNode.put("iceCrystalConcentration", polar.getIceCrystalConcentration());
            }
            if (air.getType().equals("TemperateAir")) {
                Temperat temperat = (Temperat) air;
                airNode.put("pollenLevel", temperat.getPollenLevel());
            }
            if (air.getType().equals("TropicalAir")) {
                Tropical tropical = (Tropical) air;
                airNode.put("co2Level", tropical.getCo2Level());
            }
            output.set("air", airNode);
        }

        commandOutput.set("output", output);
    }

    public void PrintMap(ObjectNode commandOutput, GameMap map) {
        ArrayNode output = MAPPER.createArrayNode();
        int n = map.getX();
        int m = map.getY();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                ObjectNode cellNode = MAPPER.createObjectNode();
                ArrayNode section = MAPPER.createArrayNode();
                section.add(j);
                section.add(i);
                cellNode.put("section", section);
                int nrObj = map.nrObj(j, i);
                cellNode.put("totalNrOfObjects", nrObj);
                Cell cell = map.getCell(j, i);
                Air air = cell.getAir();
                cellNode.put("airQuality", air.airQuality());
                Soil soil = cell.getSoil();
                cellNode.put("soilQuality", soil.qualitySoil());

                output.add(cellNode);
            }
        }
        commandOutput.put("output", output);
    }
}
