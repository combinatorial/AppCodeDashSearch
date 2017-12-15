/**
 * Created by gerard on 04.04.14.
 */

package de.dreamlab.dash.launcher;

import javax.annotation.Nonnull;
import java.awt.*;
import java.net.URI;

public class DashPluginSchemeLauncher extends AbstractLauncher {

    protected void openUri(final @Nonnull String uriStr) throws Exception
    {
        Desktop desktop = Desktop.getDesktop();
        URI uri = new URI(uriStr);
        desktop.browse(uri);
    }

}