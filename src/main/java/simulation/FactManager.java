package simulation;

import map.Cell;
import entities.Entities;
import entities.Robot;
import fileio.CommandInput;
import map.GameMap;

import java.util.ArrayList;

/**
 * Handles the logic for learning facts about entities.
 */
public final class FactManager {
    private final GameMap map;

    public FactManager(final GameMap map) {
        this.map = map;
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
        int x = map.getX();
        int y = map.getY();

        for (int i = 0; i < x; i++) {
            for (int j = 0; j < y; j++) {
                Cell currentCell = map.getCell(i, j);
                if (checkEntityForFact(currentCell.getSoil(), component,
                        subject, facts)) {
                    return true;
                }
                if (checkEntityForFact(currentCell.getPlant(), component,
                        subject, facts)) {
                    return true;
                }
                if (checkEntityForFact(currentCell.getWater(), component,
                        subject, facts)) {
                    return true;
                }
                if (checkEntityForFact(currentCell.getAnimal(), component,
                        subject, facts)) {
                    return true;
                }
                if (checkEntityForFact(currentCell.getAir(), component,
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
