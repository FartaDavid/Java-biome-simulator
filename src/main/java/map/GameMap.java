package map;

import entities.Robot;
import environment.WeatherManager;
import fileio.CommandInput;
import lombok.Getter;
import lombok.Setter;
import simulation.FactManager;
import simulation.Facts;
import simulation.SimulationManager;

import java.util.ArrayList;
/**
 * Represents the grid map of the simulation containing all entities.
 */
@Getter @Setter
public final class GameMap {
    public static final double ROUNDING_FACTOR = 100.0;
    public static final int WEATHER_RESET_TIMER = 0;
    public static final int TIMER_RESET_VALUE = 2;

    private Cell[][] cell;
    private int x;
    private int y;

    // Helper classes instances for managing different aspects of the map
    private final MapLoader mapLoader;
    private final WeatherManager weatherManager;
    private final SimulationManager simulationManager;
    private final FactManager factManager;

    /**
     * Constructor for GameMap.
     *
     * @param n Number of rows.
     * @param m Number of columns.
     */
    public GameMap(final int n, final int m) {
        cell = new Cell[n][m];
        this.x = n;
        this.y = m;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cell[i][j] = new Cell(i, j);
            }
        }

        // Initialize managers
        this.mapLoader = new MapLoader(this);
        this.weatherManager = new WeatherManager(this);
        this.simulationManager = new SimulationManager(this);
        this.factManager = new FactManager(this);
    }

    /**
     * Retrieves the cell at specific coordinates.
     *
     * @param row The row index.
     * @param col The column index.
     * @return The Cell object.
     */
    public Cell getCell(final int row, final int col) {
        return cell[row][col];
    }

    /**
     * Places the robot at the starting position (0,0).
     *
     * @param robot The robot entity.
     */
    public void initializeRobot(final Robot robot) {
        cell[0][0].setRobot(robot);
        robot.setX(0);
        robot.setY(0);
    }

    /**
     * Counts the number of entities (Water, Plant, Animal) in a cell.
     *
     * @param i Row index.
     * @param j Column index.
     * @return The count of objects.
     */
    public int nrObj(final int i, final int j) {
        int count = 0;
        if (cell[i][j].getWater() != null) {
            count++;
        }
        if (cell[i][j].getPlant() != null) {
            count++;
        }
        if (cell[i][j].getAnimal() != null) {
            count++;
        }
        return count;
    }

    /**
     * Changes the weather based on the command input.
     *
     * @param command The command input.
     * @return True if the weather was changed successfully, false otherwise.
     */
    public boolean changeWeather(final CommandInput command) {
        return weatherManager.changeWeather(command);
    }

    /**
     * Verifies the current weather conditions on the map.
     */
    public void verifyWeather() {
        weatherManager.verifyWeather();
    }

    /**
     * Verifies scanned objects on the map.
     */
    public void verifyScannedObj() {
        simulationManager.verifyScannedObj();
    }

    /**
     * Learns a fact based on the command input and robot's inventory.
     *
     * @param facts   The list of known facts.
     * @param command The command input.
     * @param robot   The robot entity.
     * @return True if the fact was learned, false otherwise.
     */
    public boolean learnFact(final ArrayList<Facts> facts, final CommandInput command,
                             final Robot robot) {
        return factManager.learnFact(facts, command, robot);
    }
}
