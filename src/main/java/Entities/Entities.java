package Entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Entities {
    private double mass;
    private String name;
    private String type;
    private boolean scanned = false;

    public Entities(String type, String name, double mass) {
        this.name = name;
        this.mass = mass;
        this.type = type;
    }

    public void objectScanned() {
        scanned = true;
    }

    protected double roundTwoDecimals(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}

