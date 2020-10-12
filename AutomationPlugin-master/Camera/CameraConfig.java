package Camera;

import mmcorej.CMMCore;
import mmcorej.StrVector;

public class CameraConfig {
    private CMMCore core;

    public void showproperty() throws Exception {
        StrVector properties = core.getDevicePropertyNames("Camera");
        for (int i = 0; i < properties.size(); i++) {
            String prop = properties.get(i);
            String val = core.getProperty("Camera", prop);
            System.out.println("Name: " + prop + ", value: " + val);
        }
    }
}
