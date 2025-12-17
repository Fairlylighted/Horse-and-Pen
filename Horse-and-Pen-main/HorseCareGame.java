import java.util.*;
import javax.swing.Timer;

public class HorseCareGame {
    private Pen pen;
    private Horse taskHorse;
    private String taskResource;
    private Runnable updateUI;
    private Random rand = new Random();

    public HorseCareGame(Runnable updateUI) {
        this.pen = new Pen(5);
        this.updateUI = updateUI;

        // Dynamic tasks every 10s
        Timer taskTimer = new Timer(10000, e -> triggerRandomTask());
        taskTimer.start();

        // Decay horse state every 15s
        Timer decayTimer = new Timer(15000, e -> {
            for (Horse h : pen.getHorses()) h.decay();
            updateUI.run();
        });
        decayTimer.start();
    }

    public Pen getPen() { return pen; }

    public void addHorse(String name) {
        pen.addHorse(new Horse(name));
        updateUI.run();
    }

    public void giveResource(Horse horse, String resource) {
        if ("water".equals(resource)) {
            horse.setWaterLevel(Math.max(0, horse.getWaterLevel() - 30));
        } else if ("shelter".equals(resource)) {
            horse.setShelterLevel(Math.max(0, horse.getShelterLevel() - 30));
        } else if ("feed".equals(resource)) {
            horse.setHasFeed(true);
        }
        updateUI.run();
    }

    public Horse getTaskHorse() { return taskHorse; }
    public String getTaskResource() { return taskResource; }

    public void doTask() {
        if (taskHorse != null && taskResource != null) {
            giveResource(taskHorse, taskResource);
            taskHorse = null;
            taskResource = null;
        }
        updateUI.run();
    }

    public void clearTask() {
        taskHorse = null;
        taskResource = null;
    }

    private void triggerRandomTask() {
        List<Horse> horses = pen.getHorses();
        if (horses.isEmpty()) return;

        Horse horse = horses.get(rand.nextInt(horses.size()));
        List<String> needed = new ArrayList<>();
        if (horse.getWaterLevel() > 50) needed.add("water");
        if (horse.getShelterLevel() > 50) needed.add("shelter");
        if (!horse.hasFeed()) needed.add("feed");

        if (needed.isEmpty()) return;
        taskHorse = horse;
        taskResource = needed.get(rand.nextInt(needed.size()));
        updateUI.run();
    }

    public String getTaskText() {
        if (taskHorse != null && taskResource != null)
            return "Task: " + taskHorse.getName() + " needs " + taskResource + "!";
        return "No task";
    }

    public String getStatusText() {
        boolean safe = pen.isSafe();
        return "Pen Status: " + (safe ? "Safe" : "Unsafe") + " | Gate: " + (pen.isGateLocked() ? "Locked" : "Open");
    }
}
