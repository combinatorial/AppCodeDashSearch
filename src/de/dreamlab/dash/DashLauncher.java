/**
 * Created by gerard on 04.04.14.
 */

package de.dreamlab.dash;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.util.ExecUtil;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Collection;

public class DashLauncher {

    public DashLauncher() {
    }

    public void search(Collection<String> keywords, String query)
    {
        try {
            String request = "dash-plugin://";

            // keywords
            if (keywords.size() > 0) {
                request += "keys=";
                int i = 0;

                for (String keyword : keywords) {
                    if (i > 0) {
                        request += ',';
                    }

                    request += urlEncode(keyword);

                    i++;
                }

                request += "&";
            }

            // query
            request += "query=" + urlEncode(query);

            openUri(request);
        }
        catch ( UnsupportedEncodingException e ) {
            Notifications.Bus.notify(new Notification("Dash Plugin Error", "Dash Plugin Error", e.getMessage(), NotificationType.ERROR));
            return;
        }
    }

    private void openUri(String uri)
    {
        try {
            final GeneralCommandLine commandLine = new GeneralCommandLine(ExecUtil.getOpenCommandPath());
            commandLine.addParameter(uri);
            commandLine.createProcess();

        }
        catch ( ExecutionException e ) {
            Notifications.Bus.notify(new Notification("Dash Plugin Error", "Dash Plugin Error", e.getMessage(), NotificationType.ERROR));
            return;
        }
    }

    private String urlEncode(String s) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(s, "UTF-8").replace("+", "%20");
    }
}