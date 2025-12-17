import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class Horse {
    private String name;
    private int happiness; // 0-100
    private int hunger;    // 0-100 (0 = full, 100 = starving)
    private int waterLevel; // 0-100
    private int shelterLevel; // 0-100
    private boolean hasFeed;

    public Horse(String name) {
        this.name = name;
        this.happiness = 100;
        this.hunger = 0;
        this.waterLevel = 0;
        this.shelterLevel = 0;
        this.hasFeed = false;
    }

    public String getName() { return name; }

    public int getHappiness() { return happiness; }
    public int getHunger() { return hunger; }
    public int getWaterLevel() { return waterLevel; }
    public int getShelterLevel() { return shelterLevel; }

    public boolean hasWater() { return waterLevel > 0; }
    public void setWaterLevel(int level) { this.waterLevel = Math.max(0, Math.min(100, level)); }
    public void setHasWater(boolean hasWater) { this.waterLevel = hasWater ? 100 : 0; }

    public boolean hasShelter() { return shelterLevel > 0; }
    public void setShelterLevel(int level) { this.shelterLevel = Math.max(0, Math.min(100, level)); }
    public void setHasShelter(boolean hasShelter) { this.shelterLevel = hasShelter ? 100 : 0; }

    public boolean hasFeed() { return hasFeed; }
    public void setHasFeed(boolean hasFeed) {
        this.hasFeed = hasFeed;
        if (hasFeed) hunger = Math.max(0, hunger - 30); // reduce hunger when fed
    }

    public boolean isCaredFor() {
        return waterLevel <= 20 && shelterLevel <= 20 && hasFeed && hunger <= 20;
    }

    public void decay() {
        // Hunger increases slowly
        hunger = Math.min(100, hunger + 5);
        // Water and shelter deprivation increases over time
        waterLevel = Math.min(100, waterLevel + 5);
        shelterLevel = Math.min(100, shelterLevel + 5);
        // Happiness affected by deprivation levels
        int waterPenalty = waterLevel / 10;
        int shelterPenalty = shelterLevel / 10;
        happiness = Math.max(0, happiness - waterPenalty - shelterPenalty);
        // Additional happiness boost if well cared for
        if (isCaredFor()) happiness = Math.min(100, happiness + 2);
        // Reset feed
        hasFeed = false;
    }

    @Override
    public String toString() { return name; }
}
