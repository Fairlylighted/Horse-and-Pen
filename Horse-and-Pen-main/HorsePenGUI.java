import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class HorsePenGUI extends JFrame {
    private HorseCareGame game;
    private JTextField nameField = new JTextField(15);
    private JButton addButton = new JButton("Add Horse");
    private JComboBox<Horse> horseSelector = new JComboBox<>();
    private JButton waterBtn = new JButton("Give Water");
    private JButton shelterBtn = new JButton("Give Shelter");
    private JButton feedBtn = new JButton("Give Feed");
    private JButton doTaskBtn = new JButton("Do Task");
    private JLabel statusLabel = new JLabel();
    private JLabel taskLabel = new JLabel();
    private JPanel horsePanel = new JPanel();
    private JButton toggleGateBtn = new JButton("Toggle Gate");

    private Map<Horse, JProgressBar> happinessBars = new HashMap<>();
    private Map<Horse, JProgressBar> hungerBars = new HashMap<>();

    public HorsePenGUI() {
        game = new HorseCareGame(this::updateUI);

        setTitle("Horse Pen Simulator");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Horse Name:"));
        topPanel.add(nameField);
        topPanel.add(addButton);
        topPanel.add(toggleGateBtn);
        add(topPanel, BorderLayout.NORTH);

        JPanel middlePanel = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel();
        controlPanel.add(new JLabel("Select Horse:"));
        controlPanel.add(horseSelector);
        controlPanel.add(waterBtn);
        controlPanel.add(shelterBtn);
        controlPanel.add(feedBtn);
        controlPanel.add(doTaskBtn);
        middlePanel.add(controlPanel, BorderLayout.NORTH);

        horsePanel.setLayout(new BoxLayout(horsePanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(horsePanel);
        middlePanel.add(scrollPane, BorderLayout.CENTER);

        add(middlePanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(2,1));
        bottomPanel.add(statusLabel);
        bottomPanel.add(taskLabel);
        add(bottomPanel, BorderLayout.SOUTH);

        // Listeners
        addButton.addActionListener(e -> addHorse());
        waterBtn.addActionListener(e -> giveResource("water"));
        shelterBtn.addActionListener(e -> giveResource("shelter"));
        feedBtn.addActionListener(e -> giveResource("feed"));
        doTaskBtn.addActionListener(e -> game.doTask());
        toggleGateBtn.addActionListener(e -> {
            game.getPen().setGateLocked(!game.getPen().isGateLocked());
            updateUI();
        });

        pack();
        setSize(700, 500);
        setVisible(true);
        updateUI();
    }

    private void addHorse() {
        String name = nameField.getText().trim();
        if (name.isEmpty()) return;
        try {
            game.addHorse(name);
            Horse h = game.getPen().getHorses().get(game.getPen().getHorseCount()-1);
            horseSelector.addItem(h);

            JProgressBar happiness = new JProgressBar(0,100);
            happiness.setStringPainted(true);
            happinessBars.put(h, happiness);

            JProgressBar hunger = new JProgressBar(0,100);
            hunger.setStringPainted(true);
            hungerBars.put(h, hunger);

            horsePanel.add(new JLabel(h.getName()));
            horsePanel.add(happiness);
            horsePanel.add(hunger);
            horsePanel.revalidate();
            horsePanel.repaint();

            nameField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
        updateUI();
    }

    private void giveResource(String resource) {
        Horse h = (Horse) horseSelector.getSelectedItem();
        if (h == null) return;
        game.giveResource(h, resource);
        updateUI();
    }

    private void updateUI() {
        taskLabel.setText(game.getTaskText());
        statusLabel.setText(game.getStatusText());

        for (Horse h : game.getPen().getHorses()) {
            JProgressBar hb = happinessBars.get(h);
            JProgressBar hung = hungerBars.get(h);
            if (hb != null) {
                hb.setValue(h.getHappiness());
                hb.setForeground(h.getHappiness() > 50 ? Color.GREEN : Color.RED);
                hb.setString("Happiness: " + h.getHappiness());
            }
            if (hung != null) {
                hung.setValue(h.getHunger());
                hung.setForeground(h.getHunger() < 50 ? Color.GREEN : Color.RED);
                hung.setString("Hunger: " + h.getHunger());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HorsePenGUI::new);
    }
}
