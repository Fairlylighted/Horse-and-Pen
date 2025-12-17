import java.util.*;

class Pen {
    private int maxCapacity;
    private List<Horse> horses = new ArrayList<>();
    private boolean gateLocked = true;

    public Pen(int maxCapacity) { this.maxCapacity = maxCapacity; }

    public void addHorse(Horse horse) {
        if (horses.size() >= maxCapacity) throw new IllegalStateException("Capacity exceeded");
        if (gateLocked) throw new IllegalStateException("Gate is locked!");
        horses.add(horse);
    }

    public void removeHorse(Horse horse) {
        if (!horses.contains(horse)) throw new IllegalArgumentException("Horse not found");
        if (gateLocked) throw new IllegalStateException("Gate is locked!");
        horses.remove(horse);
    }

    public boolean isSafe() {
        return !gateLocked && horses.size() <= maxCapacity && horses.stream().allMatch(Horse::isCaredFor);
    }

    public void setGateLocked(boolean locked) { gateLocked = locked; }
    public boolean isGateLocked() { return gateLocked; }

    public int getHorseCount() { return horses.size(); }
    public List<Horse> getHorses() { return new ArrayList<>(horses); }
}
