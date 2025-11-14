package Entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Entities {
    private double mass;
    private String name;
    private String type;

    public Entities(String type, String name, double mass) {
        this.name = name;
        this.mass = mass;
        this.type = type;
    }
}

