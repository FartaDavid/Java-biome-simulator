package Entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Entities {
    private double mass;
    private String name;

    public Entities(String name, double mass) {
        this.name = name;
        this.mass = mass;
    }
}

