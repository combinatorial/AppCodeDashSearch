/**
 * Created by gerard on 04.04.14.
 */

package de.dreamlab.dash;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import java.awt.Desktop;
import java.io.UnsupportedEncodingException;
import java.net.URI;
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

    private void openUri(String uriStr)
    {
        try {
            Desktop desktop = Desktop.getDesktop();
            URI uri = new URI(uriStr);
            desktop.browse(uri);
        }
        catch (Throwable e) {
            Notifications.Bus.notify(new Notification("Dash Plugin Error", "Dash Plugin Error", e.getMessage(), NotificationType.ERROR));
        }
    }

    private String urlEncode(String s) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(s, "UTF-8").replace("+", "%20");
    }
}