package Entities;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Water extends Entities {
    private double salinity;
    private double pH;
    private double purity;
    private double turbidity;
    private double contaminantIndex;
    private boolean isFrozen;

    public Water(String type, String name, double mass, double salinity, double pH, double purity, double turbidity,
                 double contaminantIndex, boolean isFrozen) {
        super(type, name, mass);
        this.salinity = salinity;
        this.pH = pH;
        this.purity = purity;
        this.turbidity = turbidity;
        this.contaminantIndex = contaminantIndex;
        this.isFrozen = isFrozen;
    }

    public String getWaterQuality() {
        double purity_score = purity / 100;
        double pH_score = 1 - Math.abs(pH - 7.5) / 7.5;
        double salinity_score = 1 - (salinity / 350);
        double turbidity_score = 1 - (turbidity / 100);
        double contaminant_score = 1 - (contaminantIndex / 100);
        double frozen_score = isFrozen ? 1 : 0;

        double waterQuality = (0.3 * purity_score
                + 0.2 * pH_score
                + 0.15 * salinity_score
                + 0.1 * turbidity_score
                + 0.15 * contaminant_score
                + 0.2 * frozen_score) * 100;

        if (waterQuality >= 70) {
            return "Good";
        }
        if (waterQuality < 70 && waterQuality >= 40) {
            return "Moderate";
        }
        return "Poor";
    }
}

