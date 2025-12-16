public class Horse {
    private String name;
    private boolean hasWater;
    private boolean hasShelter;
    private boolean hasFeed;

    public Horse(String name) {
        this.name = name;
        this.hasWater = false;
        this.hasShelter = false;
        this.hasFeed = false;
    }

    public String getName() {
        return name;
    }

    public boolean hasWater() { return hasWater; }
    public void setHasWater(boolean hasWater) { this.hasWater = hasWater; }

    public boolean hasShelter() { return hasShelter; }
    public void setHasShelter(boolean hasShelter) { this.hasShelter = hasShelter; }

    public boolean hasFeed() { return hasFeed; }
    public void setHasFeed(boolean hasFeed) { this.hasFeed = hasFeed; }

    public boolean isCaredFor() {
        return hasWater && hasShelter && hasFeed;
    }

    @Override
    public String toString() {
        return name;
    }
}