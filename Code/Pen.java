import java.util.*;

public class Pen {
    private int maxCapacity;
    private List<Horse> horses = new ArrayList<>();
    private boolean gateLocked = true;

    public Pen(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public void addHorse(Horse horse) throws IllegalStateException {
        if (horses.size() >= maxCapacity) throw new IllegalStateException("Capacity exceeded");
        if (gateLocked) throw new IllegalStateException("Gate locked");
        horses.add(horse);
    }

    public void removeHorse(Horse horse) throws IllegalArgumentException {
        if (!horses.contains(horse)) throw new IllegalArgumentException("Horse not found");
        if (gateLocked) throw new IllegalStateException("Gate locked");
        horses.remove(horse);
    }

    public boolean isSafe() {
        return horses.size() <= maxCapacity && horses.stream().allMatch(Horse::isCaredFor) && !gateLocked;
    }

    public void setGateLocked(boolean locked) {
        gateLocked = locked;
    }

    public int getHorseCount() { return horses.size(); }
    public List<Horse> getHorses() { return new ArrayList<>(horses); }
}