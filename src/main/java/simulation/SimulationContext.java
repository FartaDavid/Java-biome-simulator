package simulation;

import com.fasterxml.jackson.databind.ObjectMapper;
import entities.Robot;
import lombok.Getter;
import lombok.Setter;
import map.GameMap;

import java.util.ArrayList;

/**
 * Holds the state of the simulation.
 */
@Getter @Setter
public final class SimulationContext {
    private GameMap map;
    private Robot robot;
    private ArrayList<Facts> facts;
    private boolean simStarted;
    private int charging;
    private int lastTimestamp;
    private final ObjectMapper mapper;

    /**
     * Constructor.
     * @param mapper The object mapper.
     */
    public SimulationContext(final ObjectMapper mapper) {
        this.mapper = mapper;
        this.facts = new ArrayList<>();
        this.simStarted = false;
    }

    /**
     * Resets the simulation state.
     */
    public void reset() {
        this.facts = new ArrayList<>();
        this.simStarted = false;
        this.charging = 0;
        this.lastTimestamp = 0;
        this.map = null;
        this.robot = null;
    }
}
