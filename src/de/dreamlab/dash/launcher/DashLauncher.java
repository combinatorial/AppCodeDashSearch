/**
 * Created by gerard on 12.04.15.
 */

package de.dreamlab.dash.launcher;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import java.awt.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

public class DashLauncher extends AbstractLauncher {

    public void search(List<String> keywords, String query)
    {
        try {
            String request = "dash-plugin://";

            // keywords
            if ( keywords.size() > 0 ) {
                request += "keys=" + keywordString(keywords) + "&";
            }

            // query
            request += "query=" + urlEncode(query);

            openUri(request);
        }
        catch ( Throwable e ) {
            Notifications.Bus.notify(new Notification("Dash Plugin Error", "Dash Plugin Error", e.getMessage(), NotificationType.ERROR));
        }
    }

    private void openUri(String uriStr) throws ExecutionException
    {
        final GeneralCommandLine commandLine = new GeneralCommandLine("open");
        commandLine.addParameter("-g");
        commandLine.addParameter(uriStr);
        commandLine.createProcess();
    }

    private String urlEncode(String s) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(s, "UTF-8").replace("+", "%20");
    }

}
