package entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;
import fileio.InputLoader;
import fileio.SimulationInput;
import java.util.ArrayList;

/**
 * Manager class for handling simulation commands (Refactored).
 */
public final class CommandManager {
    private final SimulationContext ctx;
    private final EnvironmentReporter reporter;
    private final ActionHandler actionHandler;

    /**
     * Constructor.
     * @param mapper The JSON object mapper.
     */
    public CommandManager(final ObjectMapper mapper) {
        this.ctx = new SimulationContext(mapper);
        this.reporter = new EnvironmentReporter();
        this.actionHandler = new ActionHandler();
    }

    /**
     * Main method to manage commands.
     * @param commandInput List of commands.
     * @param output Output JSON array.
     * @param inputLoader Data loader.
     */
    public void commandManage(final ArrayList<CommandInput> commandInput,
                              final ArrayNode output,
                              final InputLoader inputLoader) {
        ctx.reset();

        for (CommandInput command : commandInput) {
            ObjectNode commandOutput = ctx.getMapper().createObjectNode();
            int currentTimestamp = command.getTimestamp();

            // Update weather and objects between timestamps
            if (ctx.isSimStarted()) {
                for (int i = ctx.getLastTimestamp() + 1; i <= currentTimestamp; i++) {
                    ctx.getMap().verifyWeather();
                    ctx.getMap().verifyScannedObj();
                }
            }

            ctx.setLastTimestamp(currentTimestamp);
            commandOutput.put("command", command.getCommand());

            processCommand(command, commandOutput, inputLoader);

            commandOutput.put("timestamp", command.getTimestamp());
            output.add(commandOutput);
        }
    }

    private void processCommand(final CommandInput command,
                                final ObjectNode commandOutput,
                                final InputLoader inputLoader) {

        String cmdType = command.getCommand();

        // Handle Lifecycle Commands directly
        if ("startSimulation".equals(cmdType)) {
            handleStartSimulation(commandOutput, inputLoader);
            return;
        }
        if ("endSimulation".equals(cmdType)) {
            handleEndSimulation(commandOutput, inputLoader);
            return;
        }

        // Check constraints
        if (ctx.getCharging() > command.getTimestamp()) {
            commandOutput.put("message",
                    "ERROR: Robot still charging. Cannot perform action");
            ctx.setCharging(ctx.getCharging() - 1);
            return;
        }
        if (!ctx.isSimStarted()) {
            commandOutput.put("message",
                    "ERROR: Simulation not started. Cannot perform action");
            return;
        }

        // Delegate to appropriate handlers
        switch (cmdType) {
            case "printEnvConditions":
                reporter.printEnvCond(ctx, commandOutput);
                break;
            case "printMap":
                reporter.printMap(ctx, commandOutput);
                break;
            case "moveRobot":
                ctx.getRobot().moveRobot(ctx.getMap(), commandOutput);
                break;
            case "getEnergyStatus":
                commandOutput.put("message",
                        "TerraBot has " + ctx.getRobot().getEnergyPoint() + " energy points left.");
                break;
            case "rechargeBattery":
                ctx.getRobot().resetEnergyPoint(command.getTimeToCharge());
                ctx.setCharging(command.getTimeToCharge() + command.getTimestamp());
                commandOutput.put("message", "Robot battery is charging.");
                break;
            case "changeWeatherConditions":
                boolean ok = ctx.getMap().changeWeather(command);
                if (ok) {
                    commandOutput.put("message", "The weather has changed.");
                } else {
                    commandOutput.put("message",
                            "ERROR: The weather change does not affect the environment. "
                                    + "Cannot perform action");
                }
                break;
            case "scanObject":
                actionHandler.handleScanObject(ctx, command, commandOutput);
                break;
            case "learnFact":
                actionHandler.handleLearnFact(ctx, command, commandOutput);
                break;
            case "printKnowledgeBase":
                reporter.printKnowledge(ctx, commandOutput);
                break;
            case "improveEnvironment":
                actionHandler.handleImproveEnvironment(ctx, command, commandOutput);
                break;
            default:
                break;
        }
    }

    private void handleStartSimulation(final ObjectNode commandOutput,
                                       final InputLoader inputLoader) {
        if (!ctx.isSimStarted()) {
            SimulationInput input = inputLoader.getSimulations().getFirst();
            String[] part = input.getTerritoryDim().split("x");
            int n = Integer.parseInt(part[0]);
            int m = Integer.parseInt(part[1]);

            GameMap map = new GameMap(n, m);
            MapManager mapManager = new MapManager();
            Robot robot = new Robot();

            robot.setEnergyPoint(input.getEnergyPoints());
            map.initializeRobot(robot);
            mapManager.setEntitites(input, map);

            ctx.setMap(map);
            ctx.setRobot(robot);
            ctx.setSimStarted(true);

            commandOutput.put("message", "Simulation has started.");
        } else {
            commandOutput.put("message",
                    "ERROR: Simulation already started. Cannot perform action");
        }
    }

    private void handleEndSimulation(final ObjectNode commandOutput,
                                     final InputLoader inputLoader) {
        if (ctx.isSimStarted()) {
            commandOutput.put("message", "Simulation has ended.");
            ctx.setSimStarted(false);
            inputLoader.getSimulations().removeFirst();
            ctx.getFacts().clear();
        } else {
            commandOutput.put("message",
                    "ERROR: Simulation not started. Cannot perform action");
        }
    }
}
