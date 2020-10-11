package Main;

import Main.AcquisitionData;
import mmcorej.CMMCore;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Executor {
    private AcquisitionData acquisitionData;
    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
    private CMMCore core;

    public Executor(AcquisitionData acquisitionData, CMMCore core) {
        this.acquisitionData = acquisitionData;
        this.core = core;
    }

    public void execute(){
        try {
            core.loadDevice("XY Stage", "DemoCamera", "DXYStage");
            core.loadDevice("Z Stage", "DemoCamera","DStage");
            core.initializeAllDevices();
            core.getProperty("DiaLamp", "State");
            core.getProperty("DiaLamp", "Intensity");
            core.getProperty("Nosepiece","State");
        } catch (Exception e) {
            e.printStackTrace();
        }
        Runnable endExecutorTask = () -> {
            executorService.shutdown();
        };
        int totalExperimentTime = Integer.parseInt(acquisitionData.totalExperimentTime.getText());
        for(int i = 0; i < acquisitionData.pointInformation.size(); i++) {
            double x = Double.parseDouble(acquisitionData.pointInformation.get(i).get(0).getText());
            double y = Double.parseDouble(acquisitionData.pointInformation.get(i).get(1).getText());
            double maxZ = Double.parseDouble(acquisitionData.pointInformation.get(i).get(2).getText());
            double minZ = Double.parseDouble(acquisitionData.pointInformation.get(i).get(3).getText());
            double zStepSize = Double.parseDouble(acquisitionData.pointInformation.get(i).get(4).getText());
            int interval = acquisitionData.timeIntervals.get(i);
            int intensity = acquisitionData.ledintensity.get(i);
            int objective = acquisitionData.objectivename.get(i);
            for(double z = maxZ; z >= minZ; z = z - zStepSize){
                scheduleTaskForAPoint(x, y, z, interval, totalExperimentTime, intensity, objective);
            }
        }
        executorService.schedule(endExecutorTask, totalExperimentTime, TimeUnit.SECONDS);
    }

    private void scheduleTaskForAPoint(double x, double y, double z, int interval, int totalTime,int intensity,int objective){
        AtomicInteger cnt = new AtomicInteger(1);
        Runnable runnableTask = () -> {
            try {
                Thread.sleep(Integer.parseInt(acquisitionData.exposureTime.getText()));
                core.setProperty("Nosepiece","State",objective);
                core.setProperty("DiaLamp", "State", "1");
                core.setProperty("DiaLamp", "Intensity", intensity);
                core.setXYPosition(x,y);
                core.setPosition(z);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println("Moved Microscope Stage to (" + x + ", " + y + ", " + z + ") | " + cnt.getAndIncrement() + "/" + "totaltime/interval:" + (totalTime)/interval + " | " + " LEDintensity:"+intensity+"" + " | " + " Objective:"+objective+"");
        };
        executorService.scheduleAtFixedRate(runnableTask, interval, interval, TimeUnit.SECONDS);
    }
}
