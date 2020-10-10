package LED;

import mmcorej.CMMCore;
import org.micromanager.Studio;
import org.micromanager.internal.MMStudio;
import org.micromanager.internal.logging.LogFileManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LEDInterface {
    private final Logger logger = Logger.getLogger(this.getClass().getName());
    //Initializing all core components of the class that will allow for manipulation of the microscope
    private CMMCore core;
    private MMStudio gui;
    private FileHandler fh;
    //Initializing additional configuration classes to allow to more personalized configurations
    private LEDAcquisitionData acquisitionData = new LEDAcquisitionData();
    private LEDTimeConfig timeConfig = new LEDTimeConfig(acquisitionData);
    private boolean timeConfigShown = false;
    static final int MIN = 0;
    static final int MAX = 2100;

    //Initializing final entry class
    private LEDExecutor executor;

    //Initializing all components necessary to setup the main frame.
    private JFrame LEDmainFrame = new JFrame("LED");
    private GridBagConstraints constraints = new GridBagConstraints();
    private JPanel tablePanel = new JPanel(new GridLayout(0, 1));

    public void setupLEDInterface(Studio app) {
        //Setting up behavior of the main frame
        LEDmainFrame.setLayout(new GridBagLayout());
        LEDmainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        LEDmainFrame.setSize(1000, 300);

        //Setting up core components
        gui = (MMStudio) app;
        core = gui.getCMMCore();
        executor = new LEDExecutor(acquisitionData, core);

        //Setting up title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("LED Control");
        titlePanel.add(titleLabel);

        //Setting up off button
        JPanel ONPanel = new JPanel();
        JButton onButton = new JButton("ON");
        onButton.addActionListener(e -> {
            try {
                onButtonPerformed(e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        ONPanel.add(onButton);

        //Setting up off button
        JPanel OFFPanel = new JPanel();
        JButton offButton = new JButton("OFF");
        offButton.addActionListener(e -> {
            try {
                offButtonPerformed(e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        OFFPanel.add(offButton);

        //Setting up intensity function

        JPanel IntensityPanel = new JPanel();
        JSlider intensityslider = new JSlider(SwingConstants.HORIZONTAL, MIN, MAX, 0);
        intensityslider.addChangeListener((e -> {
            try {
                intensitychangePerformed(e);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }));
        intensityslider.setMajorTickSpacing(10);
        intensityslider.setPaintTicks(true);
        Hashtable labelTable = new Hashtable();
        labelTable.put( new Integer( MIN ), new JLabel("0") );
        labelTable.put( new Integer( MAX ), new JLabel("2100") );
        intensityslider.setLabelTable(labelTable);
        intensityslider.setPaintLabels(true);
        IntensityPanel.add(intensityslider);
        JLabel intensitylabel = new JLabel("Intensity");
        IntensityPanel.add(intensitylabel);

        //Adding all components onto main frame
        constraints.gridwidth = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridy = 1;
        constraints.gridx = 5;
        LEDmainFrame.getContentPane().add(titlePanel, constraints);

        constraints.gridx = 5;
        constraints.gridy = 2;
        constraints.weighty = 0.1;
        constraints.insets = new Insets(10, 10, 10, 10);
        LEDmainFrame.getContentPane().add(ONPanel, constraints);

        constraints.gridx = 5;
        constraints.gridy = 3;
        constraints.weighty = 0.1;
        constraints.insets = new Insets(10, 10, 10, 10);
        LEDmainFrame.getContentPane().add(OFFPanel, constraints);

        constraints.gridx = 5;
        constraints.gridy = 4;
        constraints.weighty = 0.1;
        constraints.insets = new Insets(20, 20, 20, 20);
        LEDmainFrame.getContentPane().add(IntensityPanel, constraints);

        LEDmainFrame.setVisible(true);
    }

    private void intensitychangePerformed(ChangeEvent e) throws Exception {
        JSlider source = (JSlider)e.getSource();
        if (!source.getValueIsAdjusting()) {
            int val = (int)source.getValue();
            core.setProperty("DiaLamp", "Intensity", val);
        }

    }

    private void onButtonPerformed(ActionEvent e) throws Exception {
        core.setProperty("DiaLamp", "State", "1");
    }

    private void offButtonPerformed(ActionEvent e) throws Exception {
        core.setProperty("DiaLamp", "State", "0");
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
