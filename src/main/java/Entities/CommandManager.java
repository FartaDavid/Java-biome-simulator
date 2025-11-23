package Entities;

import Entities.AirType.*;
import Entities.SoilType.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import fileio.InputLoader;
import fileio.SimulationInput;

import java.util.ArrayList;
import java.util.Map;

public class CommandManager {
    private final ObjectMapper MAPPER;

    public CommandManager(ObjectMapper MAPPER) {
        this.MAPPER = MAPPER;
    }

    public void commandManage(ArrayList<CommandInput> commandInput, ArrayNode output, InputLoader inputLoader) {

        ArrayList<Facts> facts = new ArrayList<>();
        boolean simStarted = false;
        int charging = 0;
        int changeweather = 0;
        int lastTimestamp = 0;
        GameMap map = null;
        Robot robot = null;

        for(CommandInput command : commandInput) {
            ObjectNode commandOutput = MAPPER.createObjectNode();

            int currentTimestamp = command.getTimestamp();

            if (simStarted) {
                for (int i = lastTimestamp + 1; i <= currentTimestamp; i++) {
                    map.VerifyWeather(i); // verifica daca vreun changeweather se schimba
                    map.verifyScannedObj();
                }
            }

            lastTimestamp = currentTimestamp;

            commandOutput.put("command", command.getCommand());

            switch(command.getCommand()) {

                case "startSimulation":

                    if (!simStarted) {
                        SimulationInput input = inputLoader.getSimulations().getFirst();

                        String dim = input.getTerritoryDim();
                        String[] part = dim.split("x");

                        int n = Integer.parseInt(part[0]);
                        int m = Integer.parseInt(part[1]);

                        map = new GameMap(n, m);
                        MapManager mapManager = new MapManager();
                        robot = new Robot();

                        robot.setEnergyPoint(input.getEnergyPoints());
                        map.initializeRobot(robot);
                        mapManager.setEntitites(input, map);
                        commandOutput.put("message", "Simulation has started.");

                        simStarted = true;
                    }
                    else {
                        commandOutput.put("message", "ERROR: Simulation already started. Cannot perform action");
                    }
                    break;

                case "endSimulation":
                    if (simStarted) {
                        commandOutput.put("message", "Simulation has ended.");
                        simStarted = false;
                        inputLoader.getSimulations().removeFirst();
                    }
                    else {
                        commandOutput.put("message", "ERROR: Simulation not started. Cannot perform action");
                    }
                    break;

                case "printEnvConditions":
                    if (charging > command.getTimestamp()) {
                    commandOutput.put("message", "ERROR: Robot still charging. Cannot perform action");
                    charging--;
                    } else if (simStarted) {
                        this.PrintEnvCond(commandOutput, robot, map);
                    } else
                        commandOutput.put("message","ERROR: Simulation not started. Cannot perform action");
                    break;

                case "printMap":
                    if (charging > command.getTimestamp()) {
                    commandOutput.put("message", "ERROR: Robot still charging. Cannot perform action");
                    charging--;
                    } else if (simStarted) {
                        this.PrintMap(commandOutput, map);
                    } else
                        commandOutput.put("message", "ERROR: Simulation not started. Cannot perform action");
                    break;

                case "moveRobot":
                    if (charging > command.getTimestamp()) {
                    commandOutput.put("message", "ERROR: Robot still charging. Cannot perform action");
                    charging--;
                    } else if (simStarted) {
                        robot.moveRobot(map, commandOutput);
                    } else
                        commandOutput.put("message", "ERROR: Simulation not started. Cannot perform action");
                    break;

                case "getEnergyStatus":
                    if (charging > command.getTimestamp()) {
                    commandOutput.put("message", "ERROR: Robot still charging. Cannot perform action");
                    charging--;
                    } else if (simStarted) {
                        commandOutput.put("message", "TerraBot has " + robot.getEnergyPoint() + " energy points left.");
                    } else {
                        commandOutput.put("message","ERROR: Simulation not started. Cannot perform action");
                    }
                    break;

                case "rechargeBattery":
                    if (charging > command.getTimestamp()) {
                        commandOutput.put("message", "ERROR: Robot still charging. Cannot perform action");
                        charging--;
                    } else if (simStarted) {
                        robot.resetEnergyPoint(command.getTimeToCharge());

                        charging = command.getTimeToCharge() + command.getTimestamp();
                        commandOutput.put("message", "Robot battery is charging.");
                    } else {
                        commandOutput.put("message","ERROR: Simulation not started. Cannot perform action");
                    }
                    break;
                case "changeWeatherConditions":
                    if (simStarted) {
                        boolean ok = map.changeWeather(command);
                        if (ok)
                            commandOutput.put("message", "The weather has changed.");
                        else
                            commandOutput.put("message", "ERROR: The weather change does not affect the environment. Cannot perform action");
                    }
                    else {
                        commandOutput.put("message", "ERROR: Simulation not started. Cannot perform action");
                    }
                    break;
                case "scanObject":
                    if (simStarted) {
                        if (charging > command.getTimestamp()) {
                            commandOutput.put("message", "ERROR: Robot still charging. Cannot perform action");
                            charging--;
                        }
                        else if (robot.getEnergyPoint() < 7) {
                            commandOutput.put("message", "ERROR: Not enough energy to perform action");
                        }
                        else {
                            boolean ok = robot.scanObject(command, commandOutput, map, robot.getX(), robot.getY());

                            if (!ok) {
                                commandOutput.put("message", "ERROR: Object not found. Cannot perform action");
                            }
                            else {
                                int energy = robot.getEnergyPoint();
                                energy -= 7;
                                robot.setEnergyPoint(energy);
                            }
                        }
                    }
                    else {
                        commandOutput.put("message", "ERROR: Simulation not started. Cannot perform action");
                    }
                    break;
                case "learnFact":
                    if (simStarted) {
                        if (charging > command.getTimestamp()) {
                            commandOutput.put("message", "ERROR: Robot still charging. Cannot perform action");
                            charging--;
                        }
                        else if (robot.getEnergyPoint() < 2) {
                            commandOutput.put("message", "ERROR: Not enough battery left. Cannot perform action");
                        }
                        else {
                            boolean ok = map.learnFact(facts, command);
                            if (ok) {
                                commandOutput.put("message", "The fact has been successfully saved in the database.");
                                robot.setEnergyPoint(robot.getEnergyPoint() - 2);
                            }
                            else
                                commandOutput.put("message", "ERROR: Subject not yet saved. Cannot perform action");
                        }
                    }
                    else {
                        commandOutput.put("message", "ERROR: Simulation not started. Cannot perform action");
                    }
                    break;
                case "printKnowledgeBase":
                    if (simStarted) {
                        this.printKnowlege(facts, commandOutput);
                    }
                    else {
                        commandOutput.put("message", "ERROR: Simulation not started. Cannot perform action");
                    }
                    break;
                case "improveEnvironment":
                    if (simStarted) {
                        if (charging > command.getTimestamp()) {
                            commandOutput.put("message", "ERROR: Robot still charging. Cannot perform action");
                            charging--;
                        }
                        else if (robot.getEnergyPoint() < 10) {
                            commandOutput.put("message", "ERROR: Not enough battery left. Cannot perform action");
                        }
                        else {
                            this.imrpoveEnvironment(facts, commandOutput, map, command, robot);
                        }
                    }
                    else {
                        commandOutput.put("message", "ERROR: Simulation not started. Cannot perform action");
                    }
                    break;
            }
            commandOutput.put("timestamp", command.getTimestamp());
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

        // adaug detaliile plantei
        Plant plant = cell.getPlant();
        if (plant != null) {
            ObjectNode plantNode = MAPPER.createObjectNode();
            plantNode.put("type", plant.getType());
            plantNode.put("name", plant.getName());
            plantNode.put("mass", plant.getMass());
            output.set("plants", plantNode);
        }

        // adaug detaliile animalului
        Animal animal = cell.getAnimal();
        if (animal != null) {
            ObjectNode animalNode = MAPPER.createObjectNode();
            animalNode.put("type", animal.getType());
            animalNode.put("name", animal.getName());
            animalNode.put("mass", animal.getMass());
            // Adaugă alte atribute ale animalului dacă este necesar
            output.set("animals", animalNode);
        }

        // adaug detaliile despre apa
        Water water = cell.getWater();
        if (water != null) {
            ObjectNode waterNode = MAPPER.createObjectNode();
            waterNode.put("type", water.getType());
            waterNode.put("name", water.getName());
            waterNode.put("mass", water.getMass());
            output.set("water", waterNode);
        }

        // adaug detaliile despre aer
        Air air = cell.getAir();
        if (air != null) {
            ObjectNode airNode = MAPPER.createObjectNode();
            airNode.put("type", air.getType());
            airNode.put("name", air.getName());
            airNode.put("mass", air.getMass());
            airNode.put("humidity", air.getHumidity());
            airNode.put("temperature", air.getTemperature());
            airNode.put("oxygenLevel", air.getOxygenLevel());
            if (air.getChangeWeather() == 0)
                airNode.put("airQuality", air.getAirQuality());
            else
                airNode.put("airQuality", air.getChangedAir());
            if (air.getType().equals("DesertAir")) {
                Desert desert = (Desert) air;
                airNode.put("desertStorm", desert.isDesertStorm());
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

    public void printKnowlege(ArrayList<Facts> facts, ObjectNode commandOutput) {

        // fac un array pt fact-uri
        ArrayNode initial = MAPPER.createArrayNode();

        for (Facts fact1 : facts) {
            // fac un obiect pt fiecare fact in parte
            ObjectNode fact = MAPPER.createObjectNode();
            fact.put("topic", fact1.getComponents());
            // fac un array pt components
            ArrayNode subjects = MAPPER.createArrayNode();

            for (String sub : fact1.getSubjects()) {
                subjects.add(sub);
            }

            fact.put("facts", subjects);
            // adaug la array-ul de fact-uri ce am acumulat
            initial.add(fact);
        }
        commandOutput.put("output", initial);
    }

    public void imrpoveEnvironment(ArrayList<Facts> facts, ObjectNode commandOutput, GameMap map, CommandInput command, Robot robot) {

        String improvementType = command.getImprovementType();
        String component = command.getName();
        String improvement = null;
        boolean hasFact = false;

        if (improvementType.contains("plant")) {
            improvement = "plant";
        }
        if (improvementType.contains("fertilize")) {
            improvement = "fertilize";
        }
        if (improvementType.contains("Humidity")) {
            improvement = "increaseHumidity";
        }
        if (improvementType.contains("Moisture")) {
            improvement = "increaseMoisture";
        }

        String requiredSubject = "Method to " + improvement;

        for (Facts fact : facts) {
            if (fact.getComponents().equals(component)) {
                for (String subject : fact.getSubjects()) {
                    if (subject.contains(requiredSubject)) {
                        hasFact = true;
                    }
                }
            }
            if (hasFact) break;
        }

        boolean hasItem = robot.isInInventory(component);

        if (!hasItem) {
            commandOutput.put("message", "ERROR: Subject not yet saved. Cannot perform action");
        }
        else if (!hasFact) {
            commandOutput.put("message", "ERROR: Fact not yet saved. Cannot perform action");
        }
        else {
            Air air = map.getCell(robot.getX(), robot.getY()).getAir();
            Soil soil = map.getCell(robot.getX(), robot.getY()).getSoil();

            if (improvement.equals("plant")) {
                air.setOxygenLevel(air.getOxygenLevel() + 0.3);
                air.normalizeQuality(air.calculateAirQuality());
                commandOutput.put("message", "The " + component + " was planted successfully.");
                robot.removeFromInventory(component);
            }
            if (improvement.equals("fertilize")) {
                soil.setOrganicMatter(soil.getOrganicMatter() + 0.3);
                commandOutput.put("message", "The soil was successfully fertilized using " + component);
                robot.removeFromInventory(component);
            }
            if (improvement.equals("increaseHumidity")) {
                if (air != null) {
                    air.setHumidity(air.getHumidity() + 0.2);
                    air.normalizeQuality(air.calculateAirQuality());
                    commandOutput.put("message", "The humidity was successfully increased using " + component);
                    robot.removeFromInventory(component);
                }
            }
            if (improvement.equals("increaseMoisture")) {
                if (soil != null) {
                    soil.setWaterRetention(soil.getWaterRetention() + 0.2);
                    commandOutput.put("message", "The moisture was successfully increased using " + component);
                    robot.removeFromInventory(component);
                }
            }

            robot.setEnergyPoint(robot.getEnergyPoint() - 10);
        }
    }
}
