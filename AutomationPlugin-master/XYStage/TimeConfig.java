package XYStage;

import Main.AcquisitionData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TimeConfig{
    private JFrame timeConfigurationFrame = new JFrame("Time Configuration");
    private GridBagConstraints constraints = new GridBagConstraints();
    private JPanel tablePanel = new JPanel(new GridLayout(0,1));
    private JScrollPane infoEntryScrollPanel = new JScrollPane(tablePanel);
    private final String[] timeUnit = {"Seconds", "Minutes", "Hours"};
    private JComboBox<String> timeIntervalUnits = new JComboBox<>(timeUnit);
    private AcquisitionData acquisitionData;

    public TimeConfig(AcquisitionData acquisitionData) {
        this.acquisitionData = acquisitionData;
    }

    public void setUpTimeConfigInterface(){
        timeConfigurationFrame.setLayout(new GridBagLayout());
        timeConfigurationFrame.setSize(600, 300);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Enter Information Here");
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        JButton updatePointsButton = new JButton("Update Points");
        updatePointsButton.addActionListener(e -> updatePointsPerformed(e));
        JButton saveTimeConfiguration = new JButton("Save Time Configuration");
        saveTimeConfiguration.addActionListener(e -> saveTimeConfigurationPerformed(e));
        buttonPanel.add(updatePointsButton);
        buttonPanel.add(saveTimeConfiguration);

        updateTableDisplay();

        constraints.gridy = 0;
        timeConfigurationFrame.add(titleLabel, constraints);

        constraints.gridy = 2;
        timeConfigurationFrame.add(buttonPanel, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        timeConfigurationFrame.add(infoEntryScrollPanel, constraints);

        timeConfigurationFrame.setVisible(true);
    }

    private void updateTableDisplay(){
        acquisitionData.updateTimeArrays();
        for(int i = 0; i < acquisitionData.timeInfo.size(); i++){
            JPanel singleRowPanel = new JPanel();
            String xCord = acquisitionData.pointInformation.get(i).get(0).getText();
            String yCord = acquisitionData.pointInformation.get(i).get(1).getText();
            String zCordMin = acquisitionData.pointInformation.get(i).get(2).getText();
            String zCordMax = acquisitionData.pointInformation.get(i).get(3).getText();
            singleRowPanel.add(new JLabel("Point " + (i + 1) + ": (" + xCord + ", " + yCord + ", " + zCordMin + " to " + zCordMax + ")"));
            singleRowPanel.add(new JLabel(" | Time Interval Between Pictures:"));
            singleRowPanel.add(acquisitionData.timeInfo.get(i));
            acquisitionData.timeUnitSelected.add(new JComboBox<>(timeUnit));
            singleRowPanel.add(acquisitionData.timeUnitSelected.get(i));
            tablePanel.add(singleRowPanel);
        }
    }

    private void updatePointsPerformed(ActionEvent e) {
        tablePanel.removeAll();
        updateTableDisplay();
        infoEntryScrollPanel.revalidate();
        infoEntryScrollPanel.repaint();
    }

    private void saveTimeConfigurationPerformed(ActionEvent e) {
        timeConfigurationFrame.setVisible(false);
    }

    public void redisplayWindow(){
        acquisitionData.updateTimeArrays();
        timeConfigurationFrame.setVisible(true);
    }
}
