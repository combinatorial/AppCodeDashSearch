package de.dreamlab.dash.launcher;

import de.dreamlab.dash.SystemUtil;

import java.util.List;

public abstract class AbstractLauncher {

    public static AbstractLauncher createInstance()
    {
        if ( SystemUtil.isIsOSMac() ) {
            return new DashLauncher();
        }
        else {
            return new DashPluginSchemeLauncher();
        }
    }

    public abstract void search(List<String> keywords, String query);
}
