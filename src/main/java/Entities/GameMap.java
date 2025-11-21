package Entities;

import Entities.AirType.*;
import Entities.AnimalType.Carnivore;
import Entities.AnimalType.Parasite;
import Entities.Maturity_CatPlant.Maturity;
import fileio.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter @Setter
public class GameMap {
    private Cell[][] cell;
    private int x;
    private int y;

    public Cell getCell(int x, int y) {
        return cell[x][y];
    }

    public GameMap(int n, int m) {
        cell = new Cell[n][m];
        this.x = n;
        this.y = m;

        for(int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cell[i][j] = new Cell(i, j);
            }
        }
    }

    public void setMapSoil(SoilInput soil) {

        for(PairInput coordinates : soil.getSections()) {
            Soil realSoil = SoilSetter.returnSoil(soil);
            int x = coordinates.getX();
            int y = coordinates.getY();
            cell[x][y].setSoil(realSoil);
        }
    }

    public void setMapPlant(PlantInput plant) {

        for(PairInput coordinates : plant.getSections()) {
            Plant realPlant = new Plant(plant.getType(), plant.getName(), plant.getMass());
            int x = coordinates.getX();
            int y = coordinates.getY();
            cell[x][y].setPlant(realPlant);
        }
    }

    public void setMapAnimal(AnimalInput animal) {

        for(PairInput coordinates : animal.getSections()) {
            Animal realAnimal = AnimalSetter.returnAnimal(animal);
            int x = coordinates.getX();
            int y = coordinates.getY();
            cell[x][y].setAnimal(realAnimal);
        }
    }

    public void setMapWater(WaterInput water) {
        for(PairInput coordinates : water.sections) {
            Water realWater = new Water(water.type, water.name, water.mass, water.salinity, water.pH, water.purity,
                    water.turbidity, water.contaminantIndex, water.isFrozen);

            int x = coordinates.getX();
            int y = coordinates.getY();
            cell[x][y].setWater(realWater);
        }
    }

    public void setMapAir(AirInput air) {
        for(PairInput coordinates : air.getSections()) {
            Air realAir = AirSetter.returnAir(air);
            int x = coordinates.getX();
            int y = coordinates.getY();
            cell[x][y].setAir(realAir);
        }
    }

    public void initializeRobot(Robot robot) {
        cell[0][0].setRobot(robot);
        robot.setX(0);
        robot.setY(0);
    }

    public int nrObj(int i, int j) {
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

    public boolean changeWeather(CommandInput command) {
        switch (command.getType()) {
            case "rainfall":
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (cell[i][j].getAir().getClass() == Tropical.class) {
                            Tropical tropical = (Tropical) cell[i][j].getAir();

                            tropical.setchangeWeather();
                            tropical.Rainfall(command.getRainfall());
                        }
                    }
                }
                return true;
            case "polarStorm":
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (cell[i][j].getAir().getClass() == Polar.class) {
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
                        if (cell[i][j].getAir().getClass() == Temperat.class) {
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
                        if (cell[i][j].getAir().getClass() == Desert.class) {
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
                        if (cell[i][j].getAir().getClass() == Montan.class) {
                            Montan montan = (Montan) cell[i][j].getAir();

                            montan.setchangeWeather();
                            montan.peopleHiking(command.getNumberOfHikers());
                        }
                    }
                }
                return true;
            default: return false;
        }
    }

    public void VerifyWeather(int timestamp) {
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {

                Air air = cell[i][j].getAir();
                int count = air.getChangeWeather();

                if (count - timestamp < 0) {
                    count = 0;
                } else {
                    count -= timestamp;
                    air.setChangeWeather(count);
                }
            }
        }
    }

    public void verifyScannedObj() {
        boolean verified[][] = new boolean[x][y];

        // verific la inceput fiecarei iteratii daca trebuie sa se faca ceva
        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {

                Air air = cell[i][j].getAir();
                Plant plant = cell[i][j].getPlant();
                Animal animal = cell[i][j].getAnimal();
                Soil soil = cell[i][j].getSoil();
                Water water = cell[i][j].getWater();

                if (plant != null) {
                    if (plant.isScanned()) {
                        if (animal != null) {
                            if (animal.isScanned()) {
                                String toxicity = air.airQuality();
                                if (toxicity.equals("poor")) {
                                    animal.setStatus("sick");
                                }
                            }
                        }
                        if (water != null) {
                            plant.addIndex();
                        }
                        if (soil != null) {
                            plant.addIndex();
                        }
                        // verific daca planta este moarta
                        if (plant.getMaturity() == Maturity.Dead)
                            cell[i][j].setPlant(null);
                        else {
                            // trb sa adaug la plant index ca sa creasca planta
                            double o2 = air.getOxygenLevel() + plant.genO2();
                            double o2_round = air.roundTwoDecimals(o2);
                            air.setOxygenLevel(o2_round);

                            double qlt = air.calculateAirQuality();
                            air.normalizeQuality(qlt);

                        }
                    }
                }
                if (water != null) {
                    if (water.isScanned()) {
                        int timer = water.getTime() - 1;
                        if (timer == 0) {
                            air.addHumidity();
                            air.setHumidity(Math.round(air.getHumidity() * 100.0) / 100.0);

                            soil.addWaterRetention();
                            soil.setWaterRetention(Math.round(soil.getWaterRetention() * 100.0) / 100.0);

                            double qlt = air.calculateAirQuality();
                            air.normalizeQuality(qlt);

                            water.setTime(2);
                        }
                        else {
                            water.setTime(timer);
                        }
                    }
                }
                if (animal != null) {
                    if (animal.isScanned()) {
                        if (verified[i][j]) {
                            continue;
                        }

                        verified[i][j] = true;
                        boolean predator = (animal.getClass() == Carnivore.class || animal.getClass() == Parasite.class);
                        int k = 0;

                        if (plant != null) {
                            if (plant.isScanned()) {
                                plant = animal.eatPlant(plant);
                                k++;
                            }
                        }
                        if (water != null) {
                            if (water.isScanned()) {
                                animal.drinkWater(water);
                                k++;
                            }
                        }
                        soil.addOrganicMatter(k);

                        animal.setTimer(animal.getTimer() - 1);

                        if (animal.getTimer() == 0) {
                            Cell bestCell = animal.move(this, i, j, predator);

                            if (predator) {
                                bestCell.setAnimal(animal);
                                cell[i][j].setAnimal(null);
                                verified[bestCell.getX()][bestCell.getY()] = true;
                                bestCell.getSoil().addOrganicMatter(1);
                                bestCell.getAnimal().setTimer(2);
                            }
                            else {
                                animal.setTimer(2);
                                bestCell.setAnimal(animal);
                                verified[bestCell.getX()][bestCell.getY()] = true;
                                cell[i][j].setAnimal(null);
                            }
                        }

                    }
                }
            }
        }
    }

    public boolean learnFact(ArrayList<Facts> facts, CommandInput command) {
        String component = command.getComponents();
        String subject = command.getSubject();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {

                Air air = cell[i][j].getAir();
                Plant plant = cell[i][j].getPlant();
                Animal animal = cell[i][j].getAnimal();
                Soil soil = cell[i][j].getSoil();
                Water water = cell[i][j].getWater();

                if (soil != null) {
                    if (soil.getName().equals(component)) {
                        if (soil.isScanned()) {
                            Facts fact1 = null;
                            for (Facts fact : facts) {
                                if (fact.getComponents().equals(component)) {
                                    fact1 = fact;

                                    ArrayList<String> subjects = fact.getSubjects();
                                    subjects.add(subject);
                                    fact.setSubjects(subjects);
                                    break;
                                }
                            }
                            if (fact1 == null) {
                                fact1 = new Facts();
                                fact1.setComponents(component);
                                ArrayList<String> subjects = new ArrayList<>();
                                subjects.add(subject);
                                fact1.setSubjects(subjects);

                                facts.add(fact1);
                            }
                            return true;
                        }
                    }
                }
                if (plant != null) {
                    if (plant.getName().equals(component)) {
                        if (plant.isScanned()) {
                            Facts fact1 = null;
                            for (Facts fact : facts) {
                                if (fact.getComponents().equals(component)) {
                                    fact1 = fact;

                                    ArrayList<String> subjects = fact.getSubjects();
                                    subjects.add(subject);
                                    fact.setSubjects(subjects);
                                    break;
                                }
                            }
                            if (fact1 == null) {
                                fact1 = new Facts();
                                fact1.setComponents(component);
                                ArrayList<String> subjects = new ArrayList<>();
                                subjects.add(subject);
                                fact1.setSubjects(subjects);

                                facts.add(fact1);
                            }
                            return true;
                        }
                    }
                }
                if (water != null) {
                    if (water.getName().equals(component)) {
                        if (water.isScanned()) {
                            Facts fact1 = null;
                            for (Facts fact : facts) {
                                if (fact.getComponents().equals(component)) {
                                    fact1 = fact;

                                    ArrayList<String> subjects = fact.getSubjects();
                                    subjects.add(subject);
                                    fact.setSubjects(subjects);
                                    break;
                                }
                            }
                            if (fact1 == null) {
                                fact1 = new Facts();
                                fact1.setComponents(component);
                                ArrayList<String> subjects = new ArrayList<>();
                                subjects.add(subject);
                                fact1.setSubjects(subjects);

                                facts.add(fact1);
                            }
                            return true;
                        }
                    }
                }
                if (animal != null) {
                    if (animal.getName().equals(component)) {
                        if (animal.isScanned()) {
                            Facts fact1 = null;
                            for (Facts fact : facts) {
                                if (fact.getComponents().equals(component)) {
                                    fact1 = fact;

                                    ArrayList<String> subjects = fact.getSubjects();
                                    subjects.add(subject);
                                    fact.setSubjects(subjects);
                                    break;
                                }
                            }
                            if (fact1 == null) {
                                fact1 = new Facts();
                                fact1.setComponents(component);
                                ArrayList<String> subjects = new ArrayList<>();
                                subjects.add(subject);
                                fact1.setSubjects(subjects);

                                facts.add(fact1);
                            }
                            return true;
                        }
                    }
                }
                if (air != null) {
                    if (air.getName().equals(component)) {
                        if (air.isScanned()) {
                            Facts fact1 = null;
                            for (Facts fact : facts) {
                                if (fact.getComponents().equals(component)) {
                                    fact1 = fact;

                                    ArrayList<String> subjects = fact.getSubjects();
                                    subjects.add(subject);
                                    fact.setSubjects(subjects);
                                    break;
                                }
                            }
                            if (fact1 == null) {
                                fact1 = new Facts();
                                fact1.setComponents(component);
                                ArrayList<String> subjects = new ArrayList<>();
                                subjects.add(subject);
                                fact1.setSubjects(subjects);

                                facts.add(fact1);
                            }
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}

