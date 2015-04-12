package de.dreamlab.dash.launcher;

import de.dreamlab.dash.SystemUtil;

import java.util.List;

public abstract class AbstractLauncher {

    public static AbstractLauncher createInstance()
    {
        if ( SystemUtil.isIsOSMac() ) {
            return new DashLauncher();
        }
        else if ( SystemUtil.isIsOSLinux() ) {
            return new ZealLauncher();
        }
        else {
            return new DashPluginSchemeLauncher();
        }
    }

    public abstract void search(List<String> keywords, String query);

    protected String keywordString(final List<String> keywords) throws Exception
    {
        if ( keywords.size() > 0 ) {
            String result = "";
            boolean first = true;

            for (String keyword : keywords) {
                if ( !first ) {
                    result += ',';
                }

                result += keyword;

                first = false;
            }

            return result;
        }
        else {
            throw new Exception("empty keyword list");
        }
    }
}
