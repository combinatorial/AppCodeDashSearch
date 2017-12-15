/**
 * Created by gerard on 12.04.15.
 */

package de.dreamlab.dash.launcher;

import com.intellij.execution.configurations.GeneralCommandLine;

import javax.annotation.Nonnull;

public class DashLauncher extends AbstractLauncher {

    protected void openUri(final @Nonnull String uriStr) throws Exception
    {
        final GeneralCommandLine commandLine = new GeneralCommandLine("open");
        commandLine.addParameter("-g");
        commandLine.addParameter(uriStr);
        commandLine.createProcess();
    }

}
