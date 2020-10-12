package Objective;

import Main.AcquisitionData;
import mmcorej.CMMCore;
import org.micromanager.Studio;
import org.micromanager.internal.MMStudio;
import org.micromanager.internal.logging.LogFileManager;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ObjectiveInterface<bolean> {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    //Initializing all core components of the class that will allow for manipulation of the microscope
    private CMMCore core;
    private MMStudio gui;
    private FileHandler fh;
    //Initializing additional configuration classes to allow to more personalized configurations
    private AcquisitionData acquisitionData = new AcquisitionData();

    //Initializing all components necessary to setup the main frame.
    private JFrame ObjectivemainFrame = new JFrame("Objective_Control");
    private GridBagConstraints constraints = new GridBagConstraints();
    protected ArrayList<Integer> selectedobjective = new ArrayList<Integer>();

    public void setupObjectiveInterface(Studio app){
        //Setting up behavior of the main frame
        ObjectivemainFrame.setLayout(new GridBagLayout());
        ObjectivemainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ObjectivemainFrame.setSize(1000, 300);

        //Setting up core components
        gui = (MMStudio) app;
        core = gui.getCMMCore();

        //Setting up title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Objective Control");
        titlePanel.add(titleLabel);

        JPanel ObjectiveSelectPanel = new JPanel();
        LayoutManager layout3 = new FlowLayout();
        ObjectiveSelectPanel.setLayout(layout3);

        String[] numbers3 = {"1-", "2-", "3-", "4-", "5-","6-"};
        JList<String> objectivelist = new JList<>(numbers3);
        objectivelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        objectivelist.setVisibleRowCount(-1); // to keep all values visible
        objectivelist.setSelectedIndex(0);
        objectivelist.addListSelectionListener(e -> {
            JList list3 = (JList)e.getSource();
            int c;
            c = list3.getSelectedIndex();
            if (c==0) {
                try {
                    core.setProperty("Nosepiece", "State", "0");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (c==1) {
                try {
                    core.setProperty("Nosepiece", "State", "1");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (c==2){
                try {
                    core.setProperty("Nosepiece", "State", "2");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (c==3){
                try {
                    core.setProperty("Nosepiece", "State", "3");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (c==4){
                try {
                    core.setProperty("Nosepiece", "State", "4");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }else if (c==5){
                try {
                    core.setProperty("Nosepiece", "State", "5");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }

            }
        });
        ObjectiveSelectPanel.add(objectivelist);

        //Adding all components onto main frame
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 3;
        constraints.gridy = 1;
        ObjectivemainFrame.getContentPane().add(titlePanel, constraints);


        constraints.gridx = 3;
        constraints.gridy = 3;
        constraints.weighty = 1;
        constraints.insets = new Insets(10, 10, 10, 10);
        ObjectivemainFrame.getContentPane().add(ObjectiveSelectPanel, constraints);

        ObjectivemainFrame.setVisible(true);
    }


    public void setupLogger() {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd_HHmmss");
        try {
            String dirname = LogFileManager.getLogFileDirectory().getAbsolutePath();
            fh = new FileHandler(dirname + "/" + this.getClass().getName() + format.format(Calendar.getInstance().getTime()) + ".log");
        } catch (Exception e) {
            e.printStackTrace();
        }

        fh.setFormatter(new SimpleFormatter());
        logger.addHandler(fh);
    }
}
