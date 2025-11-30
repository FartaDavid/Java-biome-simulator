package environment;

import entities.Air;
import entities.airType.Desert;
import entities.airType.Montan;
import entities.airType.Polar;
import entities.airType.Temperat;
import entities.airType.Tropical;
import fileio.CommandInput;
import map.GameMap;

/**
 * Manages weather changes and updates on the GameMap.
 */
public final class WeatherManager {
    private final GameMap map;

    public WeatherManager(final GameMap map) {
        this.map = map;
    }

    /**
     * Handles weather changes on the map based on commands.
     * Resets previous weather effects before applying new ones.
     *
     * @param command The command input detailing the weather change.
     * @return True if weather changed successfully, false otherwise.
     */
    public boolean changeWeather(final CommandInput command) {
        int x = map.getX();
        int y = map.getY();

        switch (command.getType()) {
            case "rainfall":
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (map.getCell(i, j).getAir().getType().equals("TropicalAir")) {
                            Tropical tropical = (Tropical) map.getCell(i, j).getAir();
                            tropical.setchangeWeather();
                            tropical.rainfall(command.getRainfall());
                            map.getCell(i, j).setAir(tropical);
                        }
                    }
                }
                return true;
            case "polarStorm":
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (map.getCell(i, j).getAir().getType().equals("PolarAir")) {
                            Polar polar = (Polar) map.getCell(i, j).getAir();
                            polar.setchangeWeather();
                            polar.polarStorm(command.getWindSpeed());
                            map.getCell(i, j).setAir(polar);
                        }
                    }
                }
                return true;
            case "newSeason":
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (map.getCell(i, j).getAir().getType().equals("TemperateAir")) {
                            Temperat temperat = (Temperat) map.getCell(i, j).getAir();
                            temperat.setchangeWeather();
                            temperat.newSeason(command.getSeason());
                            map.getCell(i, j).setAir(temperat);
                        }
                    }
                }
                return true;
            case "desertStorm":
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (map.getCell(i, j).getAir().getType().equals("DesertAir")) {
                            Desert desert = (Desert) map.getCell(i, j).getAir();
                            desert.setchangeWeather();
                            desert.desertStorm(command.isDesertStorm());
                            map.getCell(i, j).setAir(desert);
                        }
                    }
                }
                return true;
            case "peopleHiking":
                for (int i = 0; i < x; i++) {
                    for (int j = 0; j < y; j++) {
                        if (map.getCell(i, j).getAir().getType().equals("MountainAir")) {
                            Montan montan = (Montan) map.getCell(i, j).getAir();
                            montan.setchangeWeather();
                            montan.peopleHiking(command.getNumberOfHikers());
                            map.getCell(i, j).setAir(montan);
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
        int x = map.getX();
        int y = map.getY();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Air air = map.getCell(i, j).getAir();
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
}
