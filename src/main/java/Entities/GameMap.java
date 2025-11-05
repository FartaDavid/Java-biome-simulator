package Entities;

import fileio.AirInput;
import fileio.PairInput;
import fileio.TerritorySectionParamsInput;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GameMap {
    private Cell[][] cell;

    public GameMap(int n) {
        cell = new Cell[n][n];
    }

    public void setAir(List<AirInput> air) {
        while(!air.isEmpty()) {
            AirInput input = air.getFirst();
            PairInput coord = input.sections.getFirst();
            while()
        }
    }
}
