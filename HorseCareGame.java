
import java.util.List;
import java.util.Random;
import javax.swing.Timer;

public class HorseCareGame{
    private Pen pen;
    private Timer timer;
    private Horse taskHorse;
    private String taskResource;
    private Runnable updateUI;

    public HorseCareGame(Runnable updateUI) {
        this.pen = new Pen(3);
        this.updateUI = updateUI;
        this.timer = new Timer(30000, e -> triggerRandomTask());
        timer.start();
        pen.setGateLocked(false);
    }

    public void addHorse(String name) throws IllegalStateException {
        Horse horse = new Horse(name);
        pen.addHorse(horse);
    }

    public void giveResource(Horse horse, String resource) {
        switch (resource) {
            case "water" -> horse.setHasWater(true);
            case "shelter" -> horse.setHasShelter(true);
            case "feed" -> horse.setHasFeed(true);
        }
    }

    public void doTask() {
        if (taskHorse != null && taskResource != null) {
            giveResource(taskHorse, taskResource);
            taskHorse = null;
            taskResource = null;
        }
    }

    private void triggerRandomTask() {
        List<Horse> horses = pen.getHorses();
        if (horses.isEmpty()) return;

        Random rand = new Random();
        Horse horse = horses.get(rand.nextInt(horses.size()));

        // Choose a resource the horse doesn't have
        List<String> needed = new java.util.ArrayList<>();
        if (!horse.hasWater()) needed.add("water");
        if (!horse.hasShelter()) needed.add("shelter");
        if (!horse.hasFeed()) needed.add("feed");

        if (needed.isEmpty()) return; // Horse is fully cared for, no task

        taskHorse = horse;
        taskResource = needed.get(rand.nextInt(needed.size()));
        updateUI.run();
    }

    public Pen getPen() {
        return pen;
    }

    public Horse getTaskHorse() {
        return taskHorse;
    }

    public String getTaskResource() {
        return taskResource;
    }

    public String getTaskText() {
        if (taskHorse != null && taskResource != null) {
            return "Task: " + taskHorse.getName() + " needs " + taskResource + "!";
        }
        return "No task";
    }

    public String getStatusText() {
        boolean safe = pen.isSafe();
        int caredCount = (int) pen.getHorses().stream().filter(Horse::isCaredFor).count();
        return "Pen Status: " + (safe ? "Safe" : "Unsafe") + " (Horses: " + pen.getHorseCount() + ", Cared: " + caredCount + ")";
    }
}