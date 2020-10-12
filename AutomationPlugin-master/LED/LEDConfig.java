package LED;

import Main.AcquisitionData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LEDConfig {
    private JFrame ledConfigurationFrame = new JFrame("LED Configuration");
    private GridBagConstraints constraints = new GridBagConstraints();
    private JPanel tablePanel = new JPanel(new GridLayout(0,1));
    private JScrollPane infoEntryScrollPanel = new JScrollPane(tablePanel);
    private AcquisitionData acquisitionData;

    public LEDConfig(AcquisitionData acquisitionData) {
        this.acquisitionData = acquisitionData;
    }

    public void redisplayWindow() {
        acquisitionData.updateTimeArrays();
        ledConfigurationFrame.setVisible(true);
    }

    public void setUpLEDTimeConfigInterface(){
        ledConfigurationFrame.setLayout(new GridBagLayout());
        ledConfigurationFrame.setSize(600, 300);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Enter Information Here");
        titlePanel.add(titleLabel);

        JPanel buttonPanel = new JPanel();
        JButton updatePointsButton = new JButton("Update Points");
        updatePointsButton.addActionListener(e -> {
            try {
                updatePointsPerformed(e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        JButton saveTimeConfiguration = new JButton("Save Configuration");
        saveTimeConfiguration.addActionListener(e -> saveConfigurationPerformed(e));
        buttonPanel.add(updatePointsButton);
        buttonPanel.add(saveTimeConfiguration);

        updateTableDisplay();

        constraints.gridy = 0;
        ledConfigurationFrame.add(titleLabel, constraints);

        constraints.gridy = 2;
        ledConfigurationFrame.add(buttonPanel, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        ledConfigurationFrame.add(infoEntryScrollPanel, constraints);

        ledConfigurationFrame.setVisible(true);

    }

    private void updatePointsPerformed(ActionEvent e) throws Exception {
        tablePanel.removeAll();
        updateTableDisplay();
        infoEntryScrollPanel.revalidate();
        infoEntryScrollPanel.repaint();
    }

    private void saveConfigurationPerformed(ActionEvent e) {
        ledConfigurationFrame.setVisible(false);
    }

    private void updateTableDisplay(){
        acquisitionData.updateTimeArrays();
        acquisitionData.updateledArrays();
        for (int i = 0; i < acquisitionData.timeInfo.size(); i++) {
            JPanel singleRowPanel = new JPanel();
            String xCord = acquisitionData.pointInformation.get(i).get(0).getText();
            String yCord = acquisitionData.pointInformation.get(i).get(1).getText();
            String zCordMin = acquisitionData.pointInformation.get(i).get(2).getText();
            String zCordMax = acquisitionData.pointInformation.get(i).get(3).getText();
            singleRowPanel.add(new JLabel("Point " + (i + 1) + ": (" + xCord + ", " + yCord + ", " + zCordMin + " to " + zCordMax + ")"));
            singleRowPanel.add(new JLabel(" | LED Intensity:"));
            singleRowPanel.add(acquisitionData.ledintensityInfo.get(i));
            tablePanel.add(singleRowPanel);
        }
    }
}
