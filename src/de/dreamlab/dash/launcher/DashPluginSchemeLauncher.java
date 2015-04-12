/**
 * Created by gerard on 04.04.14.
 */

package de.dreamlab.dash.launcher;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;

import java.awt.Desktop;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;

public class DashPluginSchemeLauncher extends AbstractLauncher {

    public DashPluginSchemeLauncher() {
    }

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

    private void openUri(String uriStr) throws URISyntaxException, IOException
    {
        Desktop desktop = Desktop.getDesktop();
        URI uri = new URI(uriStr);
        desktop.browse(uri);
    }

    private String urlEncode(String s) throws UnsupportedEncodingException
    {
        return URLEncoder.encode(s, "UTF-8").replace("+", "%20");
    }
}