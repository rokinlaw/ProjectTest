package XYStage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class LaserConfig{
    private JFrame laserConfigurationFrame = new JFrame("Laser Configuration");
    private GridBagConstraints constraints = new GridBagConstraints();
    private ArrayList<ArrayList<JCheckBox>> laserConfiguration = new ArrayList<ArrayList<JCheckBox>>();
    private JPanel tablePanel = new JPanel(new GridLayout(0,1));
    private JScrollPane infoEntryScrollPanel = new JScrollPane(tablePanel);
    private ArrayList<JCheckBox> uniformLaserConfig = new ArrayList<JCheckBox>();
    private AcquisitionData acquisitionData;


    public LaserConfig(AcquisitionData acquisitionData) {
        this.acquisitionData = acquisitionData;
    }

    public void setUpLaserConfigInterface(){
        laserConfigurationFrame.setLayout(new GridBagLayout());
        laserConfigurationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        laserConfigurationFrame.setSize(1200,350);

        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Enter Information Here");
        titlePanel.add(titleLabel);

        updateTableDisplay();

        JPanel buttonPanel = new JPanel();
        JButton updatePointsButton = new JButton("Update Points");
        updatePointsButton.addActionListener(e -> updatePointsPerformed(e));
        JButton saveTimeConfiguration = new JButton("Save");
        saveTimeConfiguration.addActionListener(e -> saveTimeConfigurationPerformed(e));
        buttonPanel.add(updatePointsButton);
        buttonPanel.add(saveTimeConfiguration);

        constraints.gridy = 0;
        laserConfigurationFrame.add(titleLabel, constraints);

        constraints.gridy = 2;
        laserConfigurationFrame.add(buttonPanel, constraints);

        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy = 1;
        constraints.weightx = 0.5;
        constraints.weighty = 0.5;
        laserConfigurationFrame.add(infoEntryScrollPanel, constraints);

        laserConfigurationFrame.setVisible(true);
    }

    public void updateTableDisplay(){
        acquisitionData.updateLaserArrays();
        for(int i = 0; i < acquisitionData.laserSelections.size(); i++) {
            JPanel singleRowPanel = new JPanel();
            ArrayList<JList> subArray = acquisitionData.laserSelections.get(i);
            String xCord = acquisitionData.pointInformation.get(i).get(0).getText();
            String yCord = acquisitionData.pointInformation.get(i).get(1).getText();
            String zCordMin = acquisitionData.pointInformation.get(i).get(2).getText();
            String zCordMax = acquisitionData.pointInformation.get(i).get(3).getText();
            singleRowPanel.add(new JLabel("Point " + (i + 1) + ": (" + xCord + ", " + yCord + ", " + zCordMin + " to " + zCordMax + ")"));
            singleRowPanel.add(new JLabel(" | Laser Configurations:"));
            for(int j = 0; j < subArray.size(); j++){
                singleRowPanel.add(subArray.get(j));
            }
            tablePanel.add(singleRowPanel);
        }
        infoEntryScrollPanel.setPreferredSize(new Dimension(650,250));
    }

    private void updatePointsPerformed(ActionEvent e) {
        tablePanel.removeAll();
        updateTableDisplay();
        infoEntryScrollPanel.revalidate();
        infoEntryScrollPanel.repaint();
    }

    private void saveTimeConfigurationPerformed(ActionEvent e) {
        acquisitionData.saveFinalConfigs();
    }

    public void redisplayWindow(){
        laserConfigurationFrame.setVisible(true);
    }
}
