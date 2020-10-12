package Objective;

import Objective.ObjectiveInterface;
import org.micromanager.MenuPlugin;
import org.micromanager.Studio;
import org.scijava.plugin.Plugin;
import org.scijava.plugin.SciJavaPlugin;

@Plugin(type = MenuPlugin.class)
public class ObjectivePlugin implements MenuPlugin, SciJavaPlugin {
    private Studio studio;
    ObjectiveInterface ObjectivemainFrame = new ObjectiveInterface();
    @Override
    public String getSubMenu() {
        return "Automation";
    }

    @Override
    public void onPluginSelected() {
        ObjectivemainFrame.setupObjectiveInterface(studio);
        ObjectivemainFrame.setupLogger();
    }

    @Override
    public void setContext(Studio studio) {
        this.studio = studio;
    }

    @Override
    public String getName() {
        return "Objective Control";
    }

    @Override
    public String getHelpText() {
        return null;
    }

    @Override
    public String getVersion() {
        return null;
    }

    @Override
    public String getCopyright() {
        return null;
    }
}