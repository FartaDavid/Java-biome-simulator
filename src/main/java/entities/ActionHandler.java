package entities;

import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import java.util.ArrayList;

/**
 * Handles complex logic for specific robot actions like scanning, learning,
 * and improving the environment.
 */
public final class ActionHandler {
    private static final int SCAN_ENERGY_COST = 7;
    private static final int LEARN_ENERGY_COST = 2;
    private static final int IMPROVE_ENERGY_COST = 10;
    private static final double OXYGEN_INCREMENT = 0.3;
    private static final double HUMIDITY_INCREMENT = 0.2;
    private static final double ORGANIC_INCREMENT = 0.3;
    private static final double MOISTURE_INCREMENT = 0.2;

    /**
     * Handles the scan object command.
     * @param ctx The simulation context.
     * @param command The command input.
     * @param commandOutput The output node.
     */
    public void handleScanObject(final SimulationContext ctx,
                                 final CommandInput command,
                                 final ObjectNode commandOutput) {
        Robot robot = ctx.getRobot();
        if (robot.getEnergyPoint() < SCAN_ENERGY_COST) {
            commandOutput.put("message", "ERROR: Not enough energy to perform action");
        } else {
            boolean ok = robot.scanObject(command, commandOutput, ctx.getMap(),
                    robot.getX(), robot.getY());

            if (!ok) {
                commandOutput.put("message", "ERROR: Object not found. Cannot perform action");
            } else {
                robot.setEnergyPoint(robot.getEnergyPoint() - SCAN_ENERGY_COST);
            }
        }
    }

    /**
     * Handles the learn fact command.
     * @param ctx The simulation context.
     * @param command The command input.
     * @param commandOutput The output node.
     */
    public void handleLearnFact(final SimulationContext ctx,
                                final CommandInput command,
                                final ObjectNode commandOutput) {
        Robot robot = ctx.getRobot();
        if (robot.getEnergyPoint() < LEARN_ENERGY_COST) {
            commandOutput.put("message", "ERROR: Not enough battery left. Cannot perform action");
        } else {
            boolean ok = ctx.getMap().learnFact(ctx.getFacts(), command, robot);
            if (ok) {
                commandOutput.put("message",
                        "The fact has been successfully saved in the database.");
                robot.setEnergyPoint(robot.getEnergyPoint() - LEARN_ENERGY_COST);
            } else {
                commandOutput.put("message", "ERROR: Subject not yet saved. Cannot perform action");
            }
        }
    }

    /**
     * Handles the improve environment command.
     * @param ctx The simulation context.
     * @param command The command input.
     * @param commandOutput The output node.
     */
    public void handleImproveEnvironment(final SimulationContext ctx,
                                         final CommandInput command,
                                         final ObjectNode commandOutput) {
        Robot robot = ctx.getRobot();
        if (robot.getEnergyPoint() < IMPROVE_ENERGY_COST) {
            commandOutput.put("message", "ERROR: Not enough battery left. Cannot perform action");
            return;
        }

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
            improvement = "increase humidity";
        }
        if (improvementType.contains("Moisture")) {
            improvement = "increaseMoisture";
        }

        String requiredSubject = "Method to " + improvement;
        ArrayList<Facts> facts = ctx.getFacts();

        for (Facts fact : facts) {
            if (fact.getComponents().equals(component)) {
                for (String subject : fact.getSubjects()) {
                    if (subject.contains(requiredSubject)) {
                        hasFact = true;
                    }
                }
            }
            if (hasFact) {
                break;
            }
        }

        boolean hasItem = robot.isInInventory(component);

        if (!hasItem) {
            commandOutput.put("message", "ERROR: Subject not yet saved. Cannot perform action");
        } else if (!hasFact) {
            commandOutput.put("message", "ERROR: Fact not yet saved. Cannot perform action");
        } else {
            performImprovement(ctx, improvement, component, commandOutput);
            robot.setEnergyPoint(robot.getEnergyPoint() - IMPROVE_ENERGY_COST);
        }
    }

    private void performImprovement(final SimulationContext ctx,
                                    final String improvement,
                                    final String component,
                                    final ObjectNode commandOutput) {
        Robot robot = ctx.getRobot();
        Cell cell = ctx.getMap().getCell(robot.getX(), robot.getY());
        Air air = cell.getAir();
        Soil soil = cell.getSoil();

        if ("plant".equals(improvement) && air != null) {
            air.setOxygenLevel(air.getOxygenLevel() + OXYGEN_INCREMENT);
            air.normalizeQuality(air.calculateAirQuality());
            commandOutput.put("message", "The " + component + " was planted successfully.");
            robot.removeFromInventory(component);
        }
        if ("fertilize".equals(improvement) && soil != null) {
            soil.setOrganicMatter(soil.getOrganicMatter() + ORGANIC_INCREMENT);
            commandOutput.put("message",
                    "The soil was successfully fertilized using " + component);
            robot.removeFromInventory(component);
        }
        if ("increase humidity".equals(improvement) && air != null) {
            air.setHumidity(air.getHumidity() + HUMIDITY_INCREMENT);
            air.normalizeQuality(air.calculateAirQuality());
            commandOutput.put("message",
                    "The humidity was successfully increased using " + component);
            robot.removeFromInventory(component);
        }
        if ("increaseMoisture".equals(improvement) && soil != null) {
            soil.setWaterRetention(soil.getWaterRetention() + MOISTURE_INCREMENT);
            commandOutput.put("message",
                    "The moisture was successfully increased using " + component);
            robot.removeFromInventory(component);
        }
    }
}
