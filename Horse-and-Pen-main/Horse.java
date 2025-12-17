import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class Horse {
    private String name;
    private int happiness; // 0-100
    private int hunger;    // 0-100 (0 = full, 100 = starving)
    private boolean hasWater;
    private boolean hasShelter;
    private boolean hasFeed;

    public Horse(String name) {
        this.name = name;
        this.happiness = 100;
        this.hunger = 0;
        this.hasWater = false;
        this.hasShelter = false;
        this.hasFeed = false;
    }

    public String getName() { return name; }

    public int getHappiness() { return happiness; }
    public int getHunger() { return hunger; }

    public boolean hasWater() { return hasWater; }
    public void setHasWater(boolean hasWater) { this.hasWater = hasWater; }

    public boolean hasShelter() { return hasShelter; }
    public void setHasShelter(boolean hasShelter) { this.hasShelter = hasShelter; }

    public boolean hasFeed() { return hasFeed; }
    public void setHasFeed(boolean hasFeed) {
        this.hasFeed = hasFeed;
        if (hasFeed) hunger = Math.max(0, hunger - 30); // reduce hunger when fed
    }

    public boolean isCaredFor() {
        return hasWater && hasShelter && hasFeed && hunger <= 20;
    }

    public void decay() {
        // Hunger increases slowly
        hunger = Math.min(100, hunger + 5);
        // Happiness drops if hungry or uncared for
        if (!isCaredFor()) happiness = Math.max(0, happiness - 5);
        else happiness = Math.min(100, happiness + 2); // happy if cared for
        // Reset temporary resources
        hasWater = false;
        hasShelter = false;
        hasFeed = false;
    }

    @Override
    public String toString() { return name; }
}
