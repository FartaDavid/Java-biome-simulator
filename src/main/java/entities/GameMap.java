package entities;

import entities.airType.Desert;
import entities.airType.Montan;
import entities.airType.Polar;
import entities.airType.Temperat;
import entities.airType.Tropical;
import entities.maturity_and_categoryPlant.Maturity;
import fileio.AirInput;
import fileio.AnimalInput;
import fileio.CommandInput;
import fileio.PairInput;
import fileio.PlantInput;
import fileio.SoilInput;
import fileio.WaterInput;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

/**
 * Represents the grid map of the simulation containing all entities.
 */
@Getter @Setter
public final class GameMap {
    private static final double ROUNDING_FACTOR = 100.0;
    private static final int WEATHER_RESET_TIMER = 0;
    private static final int TIMER_RESET_VALUE = 2;
    private static final double OXYGEN_GENERATION_ROUNDING = 100.0; // Implicit in roundTwoDecimals
    private static final double CARNIVORE_ATTACK_CHANCE = 4.0; // Example if needed

    private Cell[][] cell;
    private int x;
    private int y;

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
    }

    /**
     * Populates the map with soil based on input.
     *
     * @param soil The soil input data.
     */
    public void setMapSoil(final SoilInput soil) {
        for (PairInput coordinates : soil.getSections()) {
            Soil realSoil = SoilSetter.returnSoil(soil);
            int coordX = coordinates.getX();
            int coordY = coordinates.getY();
            cell[coordX][coordY].setSoil(realSoil);
        }
    }

    /**
     * Populates the map with plants based on input.
     *
     * @param plant The plant input data.
     */
    public void setMapPlant(final PlantInput plant) {
        for (PairInput coordinates : plant.getSections()) {
            Plant realPlant = new Plant(plant.getType(), plant.getName(), plant.getMass());
            int coordX = coordinates.getX();
            int coordY = coordinates.getY();
            cell[coordX][coordY].setPlant(realPlant);
        }
    }

    /**
     * Populates the map with animals based on input.
     *
     * @param animal The animal input data.
     */
    public void setMapAnimal(final AnimalInput animal) {
        for (PairInput coordinates : animal.getSections()) {
            Animal realAnimal = AnimalSetter.returnAnimal(animal);
            int coordX = coordinates.getX();
            int coordY = coordinates.getY();
            cell[coordX][coordY].setAnimal(realAnimal);
        }
    }

    /**
     * Populates the map with water based on input.
     *
     * @param water The water input data.
     */
    public void setMapWater(final WaterInput water) {
        for (PairInput coordinates : water.sections) {
            Water realWater = new Water(water.type, water.name, water.mass,
                    water.salinity, water.pH, water.purity,
                    water.turbidity, water.contaminantIndex, water.isFrozen);

            int coordX = coordinates.getX();
            int coordY = coordinates.getY();
            cell[coordX][coordY].setWater(realWater);
        }
    }

    /**
     * Populates the map with air based on input.
     *
     * @param air The air input data.
     */
    public void setMapAir(final AirInput air) {
        for (PairInput coordinates : air.getSections()) {
            Air realAir = AirSetter.returnAir(air);
            int coordX = coordinates.getX();
            int coordY = coordinates.getY();
            cell[coordX][coordY].setAir(realAir);
        }
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
     * Handles weather changes on the map based on commands.
     * Resets previous weather effects before applying new ones.
     *
     * @param command The command input detailing the weather change.
     * @return True if weather changed successfully, false otherwise.
     */
    public boolean changeWeather(final CommandInput command) {
        // Reset existing weather on all cells
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Air air = cell[i][j].getAir();
                if (air != null && air.getChangeWeather() > 0) {
                    air.setChangeWeather(WEATHER_RESET_TIMER);
                    air.normalizeQuality(air.calculateAirQuality());
                }
            }
        }

        switch (command.getType()) {
            case "rainfall":
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (cell[i][j].getAir().getType().equals("TropicalAir")) {
                            Tropical tropical = (Tropical) cell[i][j].getAir();
                            tropical.setchangeWeather();
                            tropical.rainfall(command.getRainfall());
                        }
                    }
                }
                return true;
            case "polarStorm":
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (cell[i][j].getAir().getType().equals("PolarAir")) {
                            Polar polar = (Polar) cell[i][j].getAir();
                            polar.setchangeWeather();
                            polar.polarStorm(command.getWindSpeed());
                        }
                    }
                }
                return true;
            case "newSeason":
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (cell[i][j].getAir().getType().equals("TemperateAir")) {
                            Temperat temperat = (Temperat) cell[i][j].getAir();
                            temperat.setchangeWeather();
                            temperat.newSeason(command.getSeason());
                        }
                    }
                }
                return true;
            case "desertStorm":
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (cell[i][j].getAir().getType().equals("DesertAir")) {
                            Desert desert = (Desert) cell[i][j].getAir();
                            desert.setchangeWeather();
                            desert.desertStorm(command.isDesertStorm());
                        }
                    }
                }
                return true;
            case "peopleHiking":
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (cell[i][j].getAir().getType().equals("MountainAir")) {
                            Montan montan = (Montan) cell[i][j].getAir();
                            montan.setchangeWeather();
                            montan.peopleHiking(command.getNumberOfHikers());
                        }
                    }
                }
                return true;
            default:
                return false;
        }
    }

    /**
     * Decrements the weather effect timer for all cells.
     * When the timer expires, air quality is normalized.
     */
    public void verifyWeather() {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Air air = cell[i][j].getAir();
                int count = air.getChangeWeather();

                if (count > 0) {
                    count--;
                    air.setChangeWeather(count);

                    if (count == 0) {
                        air.normalizeQuality(air.calculateAirQuality());
                    }
                }
            }
        }
    }

    /**
     * Iterates through the map to handle interactions for scanned objects.
     * Manages plant growth, animal movement, and feeding.
     */
    public void verifyScannedObj() {
        boolean[][] verified = new boolean[x][y];

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Air air = cell[i][j].getAir();
                Plant plant = cell[i][j].getPlant();
                Animal animal = cell[i][j].getAnimal();
                Soil soil = cell[i][j].getSoil();
                Water water = cell[i][j].getWater();

                if (plant != null && plant.isScanned()) {
                    if (animal != null && animal.isScanned()) {
                        String toxicity = air.airQuality();
                        if (toxicity.equals("poor")) {
                            animal.setStatus("sick");
                        }
                    }
                    if (water != null) {
                        plant.addIndex();
                    }
                    if (soil != null) {
                        plant.addIndex();
                    }

                    if (plant.getMaturity() == Maturity.Dead) {
                        cell[i][j].setPlant(null);
                    } else {
                        double o2 = air.getOxygenLevel() + plant.genO2();
                        double o2Round = air.roundTwoDecimals(o2);
                        air.setOxygenLevel(o2Round);
                        double qlt = air.calculateAirQuality();
                        air.normalizeQuality(qlt);
                    }
                }

                if (water != null && water.isScanned()) {
                    int timer = water.getTime() - 1;
                    if (timer == 0) {
                        air.addHumidity();
                        air.setHumidity(Math.round(air.getHumidity()
                                * ROUNDING_FACTOR) / ROUNDING_FACTOR);

                        soil.addWaterRetention();
                        soil.setWaterRetention(Math.round(soil.getWaterRetention()
                                * ROUNDING_FACTOR) / ROUNDING_FACTOR);

                        double qlt = air.calculateAirQuality();
                        air.normalizeQuality(qlt);

                        water.setTime(TIMER_RESET_VALUE);
                    } else {
                        water.setTime(timer);
                    }
                }

                if (animal != null && animal.isScanned()) {
                    if (verified[i][j]) {
                        continue;
                    }

                    verified[i][j] = true;
                    boolean predator = (animal.getType().equals("Carnivores")
                            || animal.getType().equals("Parasites"));
                    int k = 0;

                    if (plant != null && plant.isScanned()) {
                        animal.eatPlant(plant);
                        cell[i][j].setPlant(null);
                        k++;
                    }
                    if (water != null && water.isScanned()) {
                        animal.drinkWater(water);
                        if (water.getMass() == 0) {
                            cell[i][j].setWater(null);
                        }
                        k++;
                    }

                    soil.addOrganicMatter(k);
                    if (k == 0 && animal.getStatus().equals("well-fed")) {
                        soil.addOrganicMatter(1);
                    }

                    animal.setTimer(animal.getTimer() - 1);

                    if (animal.getTimer() == 0) {
                        Cell bestCell = animal.move(this, i, j, predator);

                        if (predator) {
                            animal.setTimer(TIMER_RESET_VALUE);
                            if (bestCell.getAnimal() != null && bestCell != cell[i][j]) {
                                bestCell.getSoil().addOrganicMatter(1);
                                animal.eatAnimal(bestCell.getAnimal());
                            }
                            bestCell.setAnimal(animal);
                            verified[bestCell.getX()][bestCell.getY()] = true;
                            if (cell[i][j] != bestCell) {
                                cell[i][j].setAnimal(null);
                            }
                        } else {
                            animal.setTimer(TIMER_RESET_VALUE);
                            bestCell.setAnimal(animal);
                            verified[bestCell.getX()][bestCell.getY()] = true;
                            if (cell[i][j] != bestCell) {
                                cell[i][j].setAnimal(null);
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks if a fact can be learned about a specific component.
     * Searches the map and the robot's inventory.
     *
     * @param facts The list of known facts to update.
     * @param command The command containing the component and subject.
     * @param robot The robot entity.
     * @return True if the fact was learned, false otherwise.
     */
    public boolean learnFact(final ArrayList<Facts> facts, final CommandInput command,
                             final Robot robot) {
        String component = command.getComponents();
        String subject = command.getSubject();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                if (checkEntityForFact(cell[i][j].getSoil(), component,
                        subject, facts)) {
                    return true;
                }
                if (checkEntityForFact(cell[i][j].getPlant(), component,
                        subject, facts)) {
                    return true;
                }
                if (checkEntityForFact(cell[i][j].getWater(), component,
                        subject, facts)) {
                    return true;
                }
                if (checkEntityForFact(cell[i][j].getAnimal(), component,
                        subject, facts)) {
                    return true;
                }
                if (checkEntityForFact(cell[i][j].getAir(), component,
                        subject, facts)) {
                    return true;
                }
            }
        }

        // Check robot inventory
        ArrayList<String> robotInventory = robot.getInventory();
        for (String item : robotInventory) {
            if (item.equals(component)) {
                processFactUpdate(facts, component, subject);
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to check a generic entity for fact learning.
     *
     * @param entity The entity to check (Soil, Plant, etc.).
     * @param component The name of the component to look for.
     * @param subject The subject fact to learn.
     * @param facts The list of facts.
     * @return True if fact was learned.
     */
    private boolean checkEntityForFact(final Entities entity, final String component,
                                       final String subject, final ArrayList<Facts> facts) {
        if (entity != null && entity.getName().equals(component) && entity.isScanned()) {
            processFactUpdate(facts, component, subject);
            return true;
        }
        return false;
    }

    /**
     * Updates the facts list with the new subject for a component.
     *
     * @param facts The list of facts.
     * @param component The component name.
     * @param subject The subject to add.
     */
    private void processFactUpdate(final ArrayList<Facts> facts, final String component,
                                   final String subject) {
        Facts foundFact = null;
        for (Facts fact : facts) {
            if (fact.getComponents().equals(component)) {
                foundFact = fact;
                ArrayList<String> subjects = fact.getSubjects();
                subjects.add(subject);
                fact.setSubjects(subjects);
                break;
            }
        }
        if (foundFact == null) {
            foundFact = new Facts();
            foundFact.setComponents(component);
            ArrayList<String> subjects = new ArrayList<>();
            subjects.add(subject);
            foundFact.setSubjects(subjects);
            facts.add(foundFact);
        }
    }
}
