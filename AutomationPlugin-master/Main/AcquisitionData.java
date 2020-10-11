package Main;

import javax.swing.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import mmcorej.CMMCore;
import org.micromanager.Studio;
import org.micromanager.internal.MMStudio;
import org.micromanager.internal.logging.LogFileManager;

public class AcquisitionData {
    private CMMCore core;

    //Array list storing point information
    public ArrayList<ArrayList<JTextField>> pointInformation = new ArrayList<ArrayList<JTextField>>();


    //Array lists storing time configuration information
    private String[] timeUnits = {"Seconds", "Minutes", "Hours"};
    protected ArrayList<Integer> timeIntervals = new ArrayList<Integer>();
    public ArrayList<JTextField> timeInfo = new ArrayList<JTextField>();
    public ArrayList<JComboBox> timeUnitSelected = new ArrayList<JComboBox>();
    protected JTextField timeIntervalBetweenShots = new JTextField("Time Interval Between Shots", 15);
    protected JComboBox<String> unitsForInterval = new JComboBox<>(timeUnits);
    protected JTextField totalExperimentTime = new JTextField("Total Experiment Time", 15);
    protected JComboBox<String> unitsForExperimentTime = new JComboBox<>(timeUnits);
    protected JTextField exposureTime = new JTextField("Exposure Time(ms)", 15);

    //LED related configurations
    protected ArrayList<Integer> ledintensity = new ArrayList<Integer>();
    public ArrayList<JTextField> ledintensityInfo = new ArrayList<JTextField>();

    //Objective related configurations
    protected ArrayList<Integer> objectivename = new ArrayList<Integer>();
    public ArrayList<JTextField> objectiveInfo = new ArrayList<JTextField>();

    //Array lists storing laser configuration information
    public ArrayList<ArrayList<JList>> laserSelections = new ArrayList<ArrayList<JList>>();
    protected int numOfLaserStages = 2;

    public void updateTimeArrays(){
        int difference = pointInformation.size() - timeInfo.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                timeInfo.add(new JTextField(4));
                timeInfo.get(timeInfo.size() - 1).setText("0");
                timeIntervals.add(0);

            }
        } else if(difference < 0) {
            for (int i = timeInfo.size(); i > pointInformation.size(); i--) {
                timeInfo.remove(i - 1);
                timeIntervals.remove(i - 1);
            }
        }
    }

    public void updateLaserArrays(){
        String[] laserConfigurations = {" Laser 1 ", " Laser 2 ", " Laser 3 ", " Laser 4 "};
        int difference = pointInformation.size() - laserSelections.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                ArrayList<JList> laserSelectionForPoint = new ArrayList<JList>();
                for(int j = 0; j < numOfLaserStages; j++) {
                    JList laserSelection = new JList(laserConfigurations);
                    laserSelection.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
                    laserSelection.setLayoutOrientation(JList.HORIZONTAL_WRAP);
                    laserSelection.setVisibleRowCount(-4);
                    laserSelectionForPoint.add(laserSelection);
                }
                laserSelections.add(laserSelectionForPoint);
            }
        } else if(difference < 0) {
            for (int i = laserSelections.size(); i > pointInformation.size(); i--) {
                laserSelections.remove(i - 1);
            }
        }
    }

    public void updateledArrays(){
        int difference = pointInformation.size() - ledintensityInfo.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                ledintensityInfo.add(new JTextField(4));
                ledintensityInfo.get(ledintensityInfo.size() - 1).setText("0");
                ledintensity.add(0);
            }
        } else if(difference < 0) {
            for (int i = ledintensityInfo.size(); i > pointInformation.size(); i--) {
                ledintensityInfo.remove(i - 1);
            }
        }
        for(int i = 0; i < timeInfo.size(); i++) {
            ledintensity.set(i, Integer.parseInt(ledintensityInfo.get(i).getText()));
        }
    }

    public void updateobjectiveArrays(){
        int difference = pointInformation.size() - objectiveInfo.size();
        if(difference > 0){
            for(int i = 0; i < difference; i++){
                objectiveInfo.add(new JTextField(4));
                objectiveInfo.get(objectiveInfo.size() - 1).setText("0");
                objectivename.add(0);
            }
        } else if(difference < 0) {
            for (int i = objectiveInfo.size(); i > pointInformation.size(); i--) {
                objectiveInfo.remove(i - 1);
            }
        }
        for(int i = 0; i < objectiveInfo.size(); i++) {
            objectivename.set(i, Integer.parseInt(objectiveInfo.get(i).getText()));
        }
    }

    public void setUpUniversalTextFields(){
        timeIntervalBetweenShots.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(timeIntervalBetweenShots.getText().equals("Time Interval Between Shots")){
                    timeIntervalBetweenShots.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(timeIntervalBetweenShots.getText().equals("")){
                    timeIntervalBetweenShots.setText("Time Interval Between Shots");
                }
            }
        });

        totalExperimentTime.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(totalExperimentTime.getText().equals("Total Experiment Time")){
                    totalExperimentTime.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(totalExperimentTime.getText().equals("")){
                    totalExperimentTime.setText("Total Experiment Time");
                }
            }
        });

        exposureTime.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(exposureTime.getText().equals("Exposure Time(ms)")){
                    exposureTime.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(exposureTime.getText().equals("")){
                    exposureTime.setText("Exposure Time(ms)");
                }
            }

        });
    }

    public void saveFinalConfigs(){
        updateTimeArrays();
        for(int i = 0; i < timeInfo.size(); i++){
            if(timeUnitSelected.get(i).getSelectedItem().equals("Seconds")){
                timeIntervals.set(i,Integer.parseInt(timeInfo.get(i).getText()));
            } else if (timeUnitSelected.get(i).getSelectedItem().equals("Minutes")){
                timeIntervals.set(i,Integer.parseInt(timeInfo.get(i).getText()) * 60);
            } else{
                timeIntervals.set(i,Integer.parseInt(timeInfo.get(i).getText()) * 60 * 60);
            }
        }
    }
}
