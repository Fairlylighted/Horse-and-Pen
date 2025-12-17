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
        switch (resource) {
            case "water" -> horse.setHasWater(true);
            case "shelter" -> horse.setHasShelter(true);
            case "feed" -> horse.setHasFeed(true);
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

    private void triggerRandomTask() {
        List<Horse> horses = pen.getHorses();
        if (horses.isEmpty()) return;

        Horse horse = horses.get(rand.nextInt(horses.size()));
        List<String> needed = new ArrayList<>();
        if (!horse.hasWater()) needed.add("water");
        if (!horse.hasShelter()) needed.add("shelter");
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
