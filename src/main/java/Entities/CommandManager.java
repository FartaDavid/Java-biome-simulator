package Entities;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fileio.CommandInput;

import java.util.ArrayList;

public class CommandManager {
    private final ObjectMapper MAPPER;

    public CommandManager(ObjectMapper MAPPER) {
        this.MAPPER = MAPPER;
    }

    public void commandManage(ArrayList<CommandInput> commandInput, ArrayNode output, Robot robot, GameMap map) {
        for(CommandInput command : commandInput) {
            ObjectNode commandOutput = MAPPER.createObjectNode();

            commandOutput.put("command", command.command);

            switch(command.command) {
                case "startSimulation":
                    commandOutput.put("message", "Simulation has started.");
                    break;
                case "endSimulation":
                    commandOutput.put("message", "Simulation has ended.");
                    break;
                case "printEnvConditions":
                    this.PrintEnvCond(commandOutput, robot, map);
                    break;
            }
            commandOutput.put("timestamp", command.timestamp);
            output.add(commandOutput);
        }
    }

    public void PrintEnvCond(ObjectNode commandOutput, Robot robot, GameMap map) {
        int x = robot.getX();
        int y = robot.getY();
        Cell cell = map.getCell(x, y);


    }
}
