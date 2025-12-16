

import javax.swing.*;
import java.awt.*;
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HorsePenGUI().setVisible(true));
    }
}

class HorsePenGUI extends JFrame {
    private HorseCareGame game;
    private JTextField nameField = new JTextField(20);
    private JButton addButton = new JButton("Add Horse");
    private JComboBox<Horse> horseSelector = new JComboBox<>();
    private JButton giveWaterButton = new JButton("Give Water");
    private JButton giveShelterButton = new JButton("Give Shelter");
    private JButton giveFeedButton = new JButton("Give Feed");
    private JButton doTaskButton = new JButton("Do Task");
    private JLabel statusLabel = new JLabel("Pen Status: Safe");
    private JLabel taskLabel = new JLabel("No task");

    public HorsePenGUI() {
        game = new HorseCareGame(this::updateUI);

        setTitle("Horse Pen Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Horse Name:"));
        add(nameField);
        add(addButton);
        add(new JLabel("Select Horse:"));
        add(horseSelector);
        add(giveWaterButton);
        add(giveShelterButton);
        add(giveFeedButton);
        add(taskLabel);
        add(doTaskButton);
        add(statusLabel);

        // Add listeners
        addButton.addActionListener(e -> addHorse());
        giveWaterButton.addActionListener(e -> giveResource("water"));
        giveShelterButton.addActionListener(e -> giveResource("shelter"));
        giveFeedButton.addActionListener(e -> giveResource("feed"));
        doTaskButton.addActionListener(e -> doTask());

        updateUI();
        pack();
    }

    private void addHorse() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a horse name!");
            return;
        }
        try {
            game.addHorse(name);
            Horse newHorse = game.getPen().getHorses().get(game.getPen().getHorseCount() - 1);
            horseSelector.addItem(newHorse);
            JOptionPane.showMessageDialog(this, "Added " + name + " successfully! Now care for it.");
            nameField.setText("");
            updateUI();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void giveResource(String resource) {
        Horse selected = (Horse) horseSelector.getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(this, "Select a horse first!");
            return;
        }
        game.giveResource(selected, resource);
        JOptionPane.showMessageDialog(this, "Gave " + resource + " to " + selected.getName());
        updateUI();
    }

    private void doTask() {
        Horse taskHorse = game.getTaskHorse();
        if (taskHorse == null) {
            JOptionPane.showMessageDialog(this, "No task to do!");
            return;
        }
        game.doTask();
        JOptionPane.showMessageDialog(this, "Thank you, master! " + taskHorse.getName() + " is happy.");
        updateUI();
    }

    private void updateUI() {
        statusLabel.setText(game.getStatusText());
        taskLabel.setText(game.getTaskText());
    }
}