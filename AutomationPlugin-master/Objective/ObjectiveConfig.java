package Objective;

import Main.AcquisitionData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ObjectiveConfig {

        private JFrame timeConfigurationFrame = new JFrame("Objective Configuration");
        private GridBagConstraints constraints = new GridBagConstraints();
        private JPanel tablePanel = new JPanel(new GridLayout(0,1));
        private JScrollPane infoEntryScrollPanel = new JScrollPane(tablePanel);
        private AcquisitionData acquisitionData;

        public ObjectiveConfig(AcquisitionData acquisitionData) {
            this.acquisitionData = acquisitionData;
        }

        public void redisplayWindow() {
            acquisitionData.updateTimeArrays();
            timeConfigurationFrame.setVisible(true);
        }

        public void setUpObjectiveConfigInterface(){
            timeConfigurationFrame.setLayout(new GridBagLayout());
            timeConfigurationFrame.setSize(600, 300);

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

        private void updatePointsPerformed(ActionEvent e) throws Exception {
            tablePanel.removeAll();
            updateTableDisplay();
            infoEntryScrollPanel.revalidate();
            infoEntryScrollPanel.repaint();
        }

        private void saveConfigurationPerformed(ActionEvent e) {
            timeConfigurationFrame.setVisible(false);
        }

        private void updateTableDisplay(){
            acquisitionData.updateTimeArrays();
            acquisitionData.updateobjectiveArrays();
            for (int i = 0; i < acquisitionData.timeInfo.size(); i++) {
                JPanel singleRowPanel = new JPanel();
                String xCord = acquisitionData.pointInformation.get(i).get(0).getText();
                String yCord = acquisitionData.pointInformation.get(i).get(1).getText();
                String zCordMin = acquisitionData.pointInformation.get(i).get(2).getText();
                String zCordMax = acquisitionData.pointInformation.get(i).get(3).getText();
                singleRowPanel.add(new JLabel("Point " + (i + 1) + ": (" + xCord + ", " + yCord + ", " + zCordMin + " to " + zCordMax + ")"));
                singleRowPanel.add(new JLabel(" | Objective:"));
                singleRowPanel.add(acquisitionData.objectiveInfo.get(i));
                tablePanel.add(singleRowPanel);
            }
        }
    }
